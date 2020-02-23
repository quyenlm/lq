package com.tencent.component.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.text.TextUtils;
import com.tencent.component.annotation.PluginApi;
import com.tencent.component.cache.sp.PreferenceUtil;
import com.tencent.component.db.entity.IdEntity;
import com.tencent.component.db.entity.TableEntity;
import com.tencent.component.db.exception.DBException;
import com.tencent.component.db.sqlite.CursorUtils;
import com.tencent.component.db.sqlite.Selector;
import com.tencent.component.db.sqlite.SqlInfo;
import com.tencent.component.db.sqlite.SqlInfoBuilder;
import com.tencent.component.db.sqlite.WhereBuilder;
import com.tencent.component.db.util.TableUtils;
import com.tencent.component.utils.IOUtils;
import com.tencent.component.utils.KeyValue;
import com.tencent.component.utils.log.LogUtil;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.tp.a.h;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@PluginApi(since = 6)
public class EntityManager<T> {
    private static final String TABLE_VERSIONS_PREFENCE = "table_versions";
    private static final String TAG = "EntityManager";
    private Boolean isConnect = false;
    private OnCloseListener mCloseListener;
    private ISQLiteOpenHelper mDatabaseHelper = null;
    private Class<T> mEntityClass;
    private EntityContext mEntityContext;
    private final SharedPreferences mPreference;
    private final String mPreferenceKey;
    private ISQLiteDatabase mSQLiteDatabase = null;

    interface OnCloseListener {
        void onClosed(EntityManager<?> entityManager);
    }

    @PluginApi(since = 8)
    public interface UpdateListener {
        @PluginApi(since = 8)
        void onDatabaseDowngrade(ISQLiteDatabase iSQLiteDatabase, int i, int i2);

        @PluginApi(since = 8)
        void onDatabaseUpgrade(ISQLiteDatabase iSQLiteDatabase, int i, int i2);

        @PluginApi(since = 8)
        void onTableDowngrade(ISQLiteDatabase iSQLiteDatabase, String str, int i, int i2);

        @PluginApi(since = 8)
        void onTableUpgrade(ISQLiteDatabase iSQLiteDatabase, String str, int i, int i2);
    }

    protected EntityManager(Context context, Class<T> clazz, UpdateListener updateListener, String databaseName, String table, ClassLoader classLoader, ISQLiteOpenHelper sqliteOpenHelper) {
        this.mDatabaseHelper = sqliteOpenHelper;
        this.mSQLiteDatabase = openWritable();
        this.mEntityClass = clazz;
        String tableName = TableUtils.getTableName(clazz, !TextUtils.isEmpty(table) ? table.toLowerCase().replace('.', '_') : table);
        this.mEntityContext = new EntityContext(this, tableName, classLoader);
        this.mPreference = PreferenceUtil.getGlobalPreference(context, TABLE_VERSIONS_PREFENCE);
        this.mPreferenceKey = databaseName + "_" + tableName + "_version";
        checkTableVersion(updateListener);
        createTableIfNotExist(tableName);
    }

    public Class<T> getEntityClass() {
        return this.mEntityClass;
    }

    private static void fillContentValues(ContentValues contentValues, List<KeyValue> list) {
        if (list == null || contentValues == null) {
            LogUtil.w(TAG, "List<KeyValue> is empty or ContentValues is empty!");
            return;
        }
        for (KeyValue kv : list) {
            Object value = kv.getValue();
            if (value != null) {
                if ((value instanceof byte[]) || (value instanceof Byte[])) {
                    contentValues.put(kv.getKey(), (byte[]) value);
                } else if (value instanceof Byte) {
                    contentValues.put(kv.getKey(), (Byte) value);
                } else if (value instanceof Boolean) {
                    contentValues.put(kv.getKey(), (Boolean) value);
                } else {
                    contentValues.put(kv.getKey(), value.toString());
                }
            }
        }
    }

    private void checkTableVersion(UpdateListener tableUpdateListener) {
        ISQLiteDatabase db = this.mSQLiteDatabase;
        TableEntity tableEntity = TableEntity.get(this.mEntityClass, this.mEntityContext);
        int version = tableEntity.getVersion();
        int preVersion = this.mPreference.getInt(this.mPreferenceKey, -1);
        if (preVersion <= 0 || preVersion == version) {
            this.mPreference.edit().putInt(this.mPreferenceKey, version).commit();
            return;
        }
        String tableName = tableEntity.getTableName();
        LogUtil.i(TAG, "table version changed(table:" + tableName + "| oldVersion:" + preVersion + " |version:" + version + h.b);
        if (tableUpdateListener != null) {
            LogUtil.i(TAG, "tableUpdateListener is not empty , dispatch version change event to listener.");
            if (version > preVersion) {
                tableUpdateListener.onTableUpgrade(db, tableName, preVersion, version);
            } else {
                tableUpdateListener.onTableDowngrade(db, tableName, preVersion, version);
            }
            this.mPreference.edit().putInt(this.mPreferenceKey, version).commit();
        } else if (db != null) {
            LogUtil.i(TAG, "tableUpdateListener is empty , try to drop the table " + tableName);
            try {
                if (dropTableInner()) {
                    this.mPreference.edit().putInt(this.mPreferenceKey, version).commit();
                } else {
                    LogUtil.e(TAG, "drop table " + tableName + " failed .");
                }
            } catch (SQLException e) {
                LogUtil.e(TAG, "It occurs some exception when drop table -->" + e.getMessage(), e);
            } catch (Exception e2) {
                LogUtil.e(TAG, "It occurs some exception when drop table -->" + e2.getMessage(), e2);
            }
        } else {
            LogUtil.e(TAG, "db is empty when table version changed [ tableName:" + tableName + "]");
        }
    }

    private ISQLiteDatabase getSQLiteDatabase() {
        ISQLiteDatabase db = this.mSQLiteDatabase;
        if (db == null || !db.isOpen()) {
            return openWritable();
        }
        return db;
    }

    public Boolean testSQLiteDatabase() {
        if (!this.isConnect.booleanValue()) {
            return false;
        }
        if (this.mSQLiteDatabase.isOpen()) {
            return true;
        }
        return false;
    }

    private ISQLiteDatabase openWritable() {
        ISQLiteDatabase db = null;
        try {
            db = this.mDatabaseHelper.getWritableDatabase();
            this.isConnect = true;
            return db;
        } catch (Exception e) {
            this.isConnect = false;
            return db;
        }
    }

    public void replace(List<T> entities) {
        if (entities != null) {
            ISQLiteDatabase db = getSQLiteDatabase();
            if (db != null) {
                try {
                    beginTransaction(db);
                    deleteAllWithoutTransaction(db);
                    for (T entity : entities) {
                        saveWithoutTransaction(entity, db);
                    }
                    setTransactionSuccessful(db);
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage(), e);
                } finally {
                    endTransaction(db);
                }
            } else {
                LogUtil.e(TAG, "replace entities failed(cannot get sqlitedatabase)!");
            }
        }
    }

    @PluginApi(since = 6)
    public void saveOrUpdate(T entity) throws DBException {
        if (entity != null) {
            ISQLiteDatabase db = getSQLiteDatabase();
            if (db != null) {
                try {
                    beginTransaction(db);
                    saveOrUpdateWithoutTransaction(entity, db);
                    setTransactionSuccessful(db);
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage(), e);
                } finally {
                    endTransaction(db);
                }
            } else {
                LogUtil.e(TAG, "saveOrUpdate entity failed(cannot get sqlitedatabase)!");
            }
        }
    }

    @PluginApi(since = 6)
    public void saveOrUpdateAll(List<T> entities) throws DBException {
        if (entities != null && entities.size() != 0) {
            ISQLiteDatabase db = getSQLiteDatabase();
            if (db != null) {
                try {
                    beginTransaction(db);
                    for (T entity : entities) {
                        saveOrUpdateWithoutTransaction(entity, db);
                    }
                    setTransactionSuccessful(db);
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage(), e);
                } finally {
                    endTransaction(db);
                }
            } else {
                LogUtil.e(TAG, "saveOrUpdateAll failed(cannot get sqlitedatabase)!");
            }
        }
    }

    @PluginApi(since = 6)
    public boolean save(T entity) throws DBException {
        if (entity == null) {
            return false;
        }
        boolean result = false;
        ISQLiteDatabase db = getSQLiteDatabase();
        if (db != null) {
            try {
                beginTransaction(db);
                result = saveWithoutTransaction(entity, db);
                setTransactionSuccessful(db);
                return result;
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage(), e);
                return result;
            } finally {
                endTransaction(db);
            }
        } else {
            LogUtil.e(TAG, "save entity failed(cannot get sqlitedatabase)!");
            return false;
        }
    }

    @PluginApi(since = 6)
    public void saveAll(List<T> entities) throws DBException {
        if (entities != null && entities.size() != 0) {
            ISQLiteDatabase db = getSQLiteDatabase();
            if (db != null) {
                try {
                    beginTransaction(db);
                    for (T entity : entities) {
                        if (!saveWithoutTransaction(entity, db)) {
                            throw new DBException("saveBindingId error, transaction will not commit!");
                        }
                    }
                    setTransactionSuccessful(db);
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage(), e);
                } finally {
                    endTransaction(db);
                }
            } else {
                LogUtil.e(TAG, "saveAll entities failed(cannot get sqlitedatabase)!");
            }
        }
    }

    @PluginApi(since = 6)
    public void delete(T entity) throws DBException {
        if (entity != null) {
            ISQLiteDatabase db = getSQLiteDatabase();
            if (db != null) {
                try {
                    beginTransaction(db);
                    deleteWithoutTransaction(entity, db);
                    setTransactionSuccessful(db);
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage(), e);
                } finally {
                    endTransaction(db);
                }
            } else {
                LogUtil.e(TAG, "delete entity failed(cannot get sqlitedatabase)!");
            }
        }
    }

    @PluginApi(since = 6)
    public void deleteAll(List<T> entities) throws DBException {
        if (entities != null && entities.size() >= 1) {
            ISQLiteDatabase db = getSQLiteDatabase();
            if (db != null) {
                try {
                    beginTransaction(db);
                    for (T entity : entities) {
                        deleteWithoutTransaction(entity, db);
                    }
                    setTransactionSuccessful(db);
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage(), e);
                } finally {
                    endTransaction(db);
                }
            } else {
                LogUtil.e(TAG, "delete entities failed(cannot get sqlitedatabase)!");
            }
        }
    }

    @PluginApi(since = 6)
    public void deleteAll() throws DBException {
        ISQLiteDatabase db = getSQLiteDatabase();
        if (db != null) {
            try {
                beginTransaction(db);
                deleteAllWithoutTransaction(db);
                setTransactionSuccessful(db);
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage(), e);
            } finally {
                endTransaction(db);
            }
        } else {
            LogUtil.e(TAG, "deleteAll failed(cannot get sqlitedatabase)!");
        }
    }

    private void deleteAllWithoutTransaction(ISQLiteDatabase db) {
        execSQL(SqlInfoBuilder.buildDeleteSqlInfo((Class<?>) this.mEntityClass, (WhereBuilder) null, this.mEntityContext), db);
    }

    @PluginApi(since = 6)
    public void deleteById(Object idValue) throws DBException {
        if (idValue != null) {
            ISQLiteDatabase db = getSQLiteDatabase();
            if (db != null) {
                try {
                    beginTransaction(db);
                    execSQL(SqlInfoBuilder.buildDeleteSqlInfo((Class<?>) this.mEntityClass, idValue, this.mEntityContext), db);
                    setTransactionSuccessful(db);
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage(), e);
                } finally {
                    endTransaction(db);
                }
            } else {
                LogUtil.e(TAG, "deleteById failed(cannot get sqlitedatabase)!");
            }
        }
    }

    @PluginApi(since = 6)
    public void delete(WhereBuilder whereBuilder) throws DBException {
        if (whereBuilder != null) {
            ISQLiteDatabase db = getSQLiteDatabase();
            if (db != null) {
                try {
                    beginTransaction(db);
                    execSQL(SqlInfoBuilder.buildDeleteSqlInfo((Class<?>) this.mEntityClass, whereBuilder, this.mEntityContext), db);
                    setTransactionSuccessful(db);
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage(), e);
                } finally {
                    endTransaction(db);
                }
            } else {
                LogUtil.e(TAG, "delete failed(cannot get sqlitedatabase)!");
            }
        }
    }

    @PluginApi(since = 6)
    public void update(T entity, String... updateColumnNames) throws DBException {
        if (entity != null) {
            ISQLiteDatabase db = getSQLiteDatabase();
            if (db != null) {
                try {
                    beginTransaction(db);
                    updateWithoutTransaction(entity, db, updateColumnNames);
                    setTransactionSuccessful(db);
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage(), e);
                } finally {
                    endTransaction(db);
                }
            } else {
                LogUtil.e(TAG, "update entity failed(cannot get sqlitedatabase)!");
            }
        }
    }

    @PluginApi(since = 6)
    public void updateAll(List<T> entities, String... updateColumnNames) throws DBException {
        if (entities != null && entities.size() >= 1) {
            ISQLiteDatabase db = getSQLiteDatabase();
            if (db != null) {
                try {
                    beginTransaction(db);
                    for (T entity : entities) {
                        updateWithoutTransaction(entity, db, updateColumnNames);
                    }
                    setTransactionSuccessful(db);
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage(), e);
                } finally {
                    endTransaction(db);
                }
            } else {
                LogUtil.e(TAG, "updateAll entities failed(cannot get sqlitedatabase)!");
            }
        }
    }

    @PluginApi(since = 6)
    public void update(T entity, WhereBuilder whereBuilder, String... updateColumnNames) throws DBException {
        if (entity != null) {
            ISQLiteDatabase db = getSQLiteDatabase();
            if (db != null) {
                try {
                    beginTransaction(db);
                    execSQL(SqlInfoBuilder.buildUpdateSqlInfo(this.mEntityContext, this.mEntityClass, entity, whereBuilder, updateColumnNames), db);
                    setTransactionSuccessful(db);
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage(), e);
                } finally {
                    endTransaction(db);
                }
            } else {
                LogUtil.e(TAG, "update entity failed(cannot get sqlitedatabase)!");
            }
        }
    }

    @PluginApi(since = 7)
    public void update(ContentValues contentValues, WhereBuilder whereBuilder) {
        ISQLiteDatabase db = getSQLiteDatabase();
        if (db != null) {
            try {
                beginTransaction(db);
                db.update(TableEntity.get(this.mEntityClass, this.mEntityContext).getTableName(), contentValues, whereBuilder.toString(), (String[]) null);
                setTransactionSuccessful(db);
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage(), e);
            } finally {
                endTransaction(db);
            }
        } else {
            LogUtil.e(TAG, "update entity failed(cannot get sqlitedatabase)!");
        }
    }

    @PluginApi(since = 100)
    public T createEntityByCursor(Cursor cursor) {
        if (cursor != null) {
            return CursorUtils.getEntity(cursor, this.mEntityClass, this.mEntityContext);
        }
        return null;
    }

    @PluginApi(since = 6)
    public T findById(Object idValue) throws DBException {
        T t = null;
        if (idValue != null) {
            ArrayList<IdEntity> idEntities = TableEntity.get(this.mEntityClass, this.mEntityContext).getIdList();
            if (idEntities == null || idEntities.size() > 1) {
                LogUtil.e(TAG, "There's more than one id, cannot use findById method!!");
            } else {
                IdEntity id = idEntities.get(0);
                if (id == null) {
                    LogUtil.e(TAG, "findById failed[id is empty]");
                } else {
                    Cursor cursor = query(Selector.create().where(id.getColumnName(), HttpRequest.HTTP_REQ_ENTITY_MERGE, idValue).limit(1).buildSql(this.mEntityClass, this.mEntityContext));
                    if (cursor != null) {
                        try {
                            if (cursor.moveToNext()) {
                                t = CursorUtils.getEntity(cursor, this.mEntityClass, this.mEntityContext);
                            }
                        } catch (Exception e) {
                            LogUtil.e(TAG, e.getMessage(), e);
                        } finally {
                            IOUtils.closeQuietly(cursor);
                        }
                    }
                    IOUtils.closeQuietly(cursor);
                }
            }
        }
        return t;
    }

    @PluginApi(since = 6)
    public T findFirst(Selector selector) throws DBException {
        T t = null;
        if (selector != null) {
            Cursor cursor = query(selector.limit(1).buildSql(this.mEntityClass, this.mEntityContext));
            if (cursor != null) {
                try {
                    if (cursor.moveToNext()) {
                        t = CursorUtils.getEntity(cursor, this.mEntityClass, this.mEntityContext);
                    }
                } catch (Exception e) {
                    LogUtil.e(TAG, e.getMessage(), e);
                } finally {
                    IOUtils.closeQuietly(cursor);
                }
            }
            IOUtils.closeQuietly(cursor);
        }
        return t;
    }

    @PluginApi(since = 6)
    public List<T> findAll() throws DBException {
        return findAll(Selector.create());
    }

    @PluginApi(since = 6)
    public List<T> findAll(Selector selector) throws DBException {
        if (selector == null) {
            return null;
        }
        Cursor cursor = query(selector.buildSql(this.mEntityClass, this.mEntityContext));
        List<T> result = new ArrayList<>();
        if (cursor == null) {
            return result;
        }
        while (cursor.moveToNext()) {
            try {
                result.add(CursorUtils.getEntity(cursor, this.mEntityClass, this.mEntityContext));
            } catch (Exception e) {
                LogUtil.e(TAG, e.getMessage(), e);
                return result;
            } finally {
                IOUtils.closeQuietly(cursor);
            }
        }
        return result;
    }

    public void execSQL(SqlInfo sqlInfo) throws DBException {
        execSQL(sqlInfo, (ISQLiteDatabase) null);
    }

    private void execSQL(SqlInfo sqlInfo, ISQLiteDatabase db) throws DBException {
        if (sqlInfo != null) {
            debugSql(sqlInfo.getSql());
            if (db == null) {
                try {
                    db = getSQLiteDatabase();
                } catch (Throwable e) {
                    throw new DBException(e);
                }
            }
            if (db == null) {
                LogUtil.e(TAG, "cannot get sqlitedatabase!");
            } else if (sqlInfo.getBindArgs() != null) {
                db.execSQL(sqlInfo.getSql(), sqlInfo.getBindArgsAsArray());
            } else {
                db.execSQL(sqlInfo.getSql());
            }
        }
    }

    @PluginApi(since = 7)
    public void execSQL(String sql) throws DBException {
        execSQL(sql, (ISQLiteDatabase) null);
    }

    private void execSQL(String sql, ISQLiteDatabase db) throws DBException {
        if (sql != null) {
            debugSql(sql);
            if (db == null) {
                try {
                    db = getSQLiteDatabase();
                } catch (Throwable e) {
                    throw new DBException(e);
                }
            }
            if (db != null) {
                db.execSQL(sql);
            } else {
                LogUtil.e(TAG, "cannot get sqlitedatabase!");
            }
        }
    }

    @PluginApi(since = 6)
    public Cursor query(Selector selector) throws DBException {
        if (selector == null) {
            return null;
        }
        return query(selector.buildSql(this.mEntityClass, this.mEntityContext));
    }

    public Cursor query(String sql) throws DBException {
        return rawQuery(sql, (String[]) null);
    }

    private void saveOrUpdateWithoutTransaction(T entity, ISQLiteDatabase db) throws DBException {
        if (!hasEmptyIdEntity(entity)) {
            replaceWithoutTransaction(entity, db);
        } else {
            saveWithoutTransaction(entity, db);
        }
    }

    private boolean hasEmptyIdEntity(T entity) {
        ArrayList<IdEntity> idEntities = TableUtils.getIdList(this.mEntityClass);
        if (idEntities != null) {
            Iterator i$ = idEntities.iterator();
            while (i$.hasNext()) {
                if (i$.next().getColumnValue(entity) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    private void replaceWithoutTransaction(T entity, ISQLiteDatabase db) throws DBException {
        execSQL(SqlInfoBuilder.buildReplaceSqlInfo(this.mEntityClass, entity, this.mEntityContext), db);
    }

    private boolean saveWithoutTransaction(T entity, ISQLiteDatabase db) throws DBException {
        TableEntity table = TableEntity.get(this.mEntityClass, this.mEntityContext);
        ArrayList<IdEntity> idEntities = table.getIdList();
        List<KeyValue> entityKvList = SqlInfoBuilder.collectInsertKeyValues(this.mEntityClass, entity, this.mEntityContext);
        if (entityKvList != null && entityKvList.size() > 0) {
            ContentValues cv = new ContentValues();
            fillContentValues(cv, entityKvList);
            if (db == null) {
                db = getSQLiteDatabase();
            }
            if (db != null) {
                Long id = Long.valueOf(db.insert(table.getTableName(), (String) null, cv));
                if (idEntities != null) {
                    Iterator i$ = idEntities.iterator();
                    while (i$.hasNext()) {
                        IdEntity idColumn = i$.next();
                        if (idColumn != null) {
                            if (idColumn.isAutoIncrement()) {
                                if (id.longValue() == -1) {
                                    return false;
                                }
                                idColumn.setAutoIncrementId(entity, id.longValue());
                            } else if (idColumn.isUUIDGenerationType()) {
                                idColumn.setIdValue(entity, cv.get(idColumn.getColumnName()));
                            }
                        }
                    }
                }
                return true;
            }
            LogUtil.e(TAG, "saveWithoutTransaction failed(cannot get sqlitedatabase)!");
        }
        return false;
    }

    private void deleteWithoutTransaction(T entity, ISQLiteDatabase db) throws DBException {
        execSQL(SqlInfoBuilder.buildDeleteByObjectSqlInfo(this.mEntityClass, entity, this.mEntityContext), db);
    }

    private void updateWithoutTransaction(T entity, ISQLiteDatabase db, String... updateColumnNames) throws DBException {
        execSQL(SqlInfoBuilder.buildUpdateSqlInfo(this.mEntityContext, this.mEntityClass, entity, updateColumnNames), db);
    }

    private void createTableIfNotExist(String table) throws DBException {
        try {
            execSQL(SqlInfoBuilder.buildCreateTableSqlInfo(this.mEntityClass, this.mEntityContext));
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
        }
    }

    @PluginApi(since = 6)
    public int getCount() {
        int i = 0;
        Cursor cursor = null;
        try {
            cursor = query("SELECT COUNT(*) FROM " + TableEntity.get(this.mEntityClass, this.mEntityContext).getTableName());
            if (cursor == null || !cursor.moveToNext()) {
                IOUtils.closeQuietly(cursor);
            } else {
                i = cursor.getInt(0);
            }
            return i;
        } finally {
            IOUtils.closeQuietly(cursor);
        }
    }

    public boolean tableIsExist() throws DBException {
        Cursor cursor = null;
        try {
            cursor = query("SELECT COUNT(*) AS c FROM sqlite_master WHERE type ='table' AND name ='" + TableEntity.get(this.mEntityClass, this.mEntityContext).getTableName() + "'");
            if (cursor == null || !cursor.moveToNext() || cursor.getInt(0) <= 0) {
                IOUtils.closeQuietly(cursor);
                return false;
            }
            return true;
        } finally {
            IOUtils.closeQuietly(cursor);
        }
    }

    @PluginApi(since = 6)
    public void dropDb() throws DBException {
        Cursor cursor = null;
        try {
            cursor = query("SELECT name FROM sqlite_master WHERE type ='table'");
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String tableName = cursor.getString(0);
                    execSQL("DROP TABLE " + tableName);
                    TableEntity.remove(tableName);
                }
            }
            IOUtils.closeQuietly(cursor);
        } catch (Throwable th) {
            IOUtils.closeQuietly(cursor);
            throw th;
        }
    }

    @PluginApi(since = 6)
    public void dropTable() throws DBException {
        dropTableInner();
    }

    private boolean dropTableInner() {
        TableEntity table = TableEntity.get(this.mEntityClass, this.mEntityContext);
        if (table == null) {
            return false;
        }
        try {
            execSQL("DROP TABLE IF EXISTS " + table.getTableName());
            if (tableIsExist()) {
                return false;
            }
            TableEntity.remove((Class<?>) this.mEntityClass);
            return true;
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
            return false;
        }
    }

    private void debugSql(String sql) {
    }

    private void beginTransaction(ISQLiteDatabase db) {
        try {
            db.beginTransaction();
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
        }
    }

    private void setTransactionSuccessful(ISQLiteDatabase db) {
        try {
            db.setTransactionSuccessful();
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
        }
    }

    private void endTransaction(ISQLiteDatabase db) {
        try {
            db.endTransaction();
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
        }
    }

    private Cursor rawQuery(String sql, String[] selectionArgs) {
        debugSql(sql);
        try {
            ISQLiteDatabase db = getSQLiteDatabase();
            if (db != null) {
                Cursor cursor = db.rawQuery(sql, selectionArgs);
                if (cursor != null) {
                    return SafeCursorWrapper.create(cursor);
                }
            } else {
                LogUtil.e(TAG, "rawQuery failed[cannot get sqlitedatabase]!");
            }
        } catch (Exception e) {
            LogUtil.e(TAG, e.getMessage(), e);
        }
        return null;
    }

    @PluginApi(since = 6)
    public void close() {
        ISQLiteDatabase db = getSQLiteDatabase();
        if (db != null) {
            db.close();
        }
        notifyClosed();
    }

    private void notifyClosed() {
        OnCloseListener listener = this.mCloseListener;
        if (listener != null) {
            listener.onClosed(this);
        }
    }

    /* access modifiers changed from: package-private */
    public void setOnCloseListener(OnCloseListener listener) {
        this.mCloseListener = listener;
    }

    public boolean isClosed() {
        return false;
    }
}
