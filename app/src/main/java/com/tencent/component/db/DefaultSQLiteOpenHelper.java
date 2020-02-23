package com.tencent.component.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.tencent.component.db.EntityManager;
import com.tencent.component.db.entity.TableEntity;
import com.tencent.component.db.exception.DbCacheExceptionHandler;
import com.tencent.component.utils.IOUtils;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSQLiteOpenHelper implements ISQLiteOpenHelper {
    private static ConcurrentHashMap<String, DefaultSQLiteOpenHelper> sInstanceMap = new ConcurrentHashMap<>();
    private DBHelper mOpenHelper;

    public void init(Context context, String name, int version) {
        init(context, name, version, (EntityManager.UpdateListener) null);
    }

    public void init(Context context, String name, int version, EntityManager.UpdateListener updateListener) {
        if (this.mOpenHelper == null) {
            this.mOpenHelper = new DBHelper(context, name, (SQLiteDatabase.CursorFactory) null, version, updateListener);
        }
    }

    public ISQLiteDatabase getWritableDatabase() {
        return new DefaultSQLiteDatabase(this.mOpenHelper.getWritableDatabase());
    }

    public ISQLiteDatabase getReadableDatabase() {
        return new DefaultSQLiteDatabase(this.mOpenHelper.getReadableDatabase());
    }

    public static DefaultSQLiteOpenHelper getInstance(String dbName) {
        DefaultSQLiteOpenHelper openHelper = sInstanceMap.get(dbName);
        if (openHelper == null) {
            synchronized (DefaultSQLiteOpenHelper.class) {
                openHelper = sInstanceMap.get(dbName);
                if (openHelper == null) {
                    openHelper = new DefaultSQLiteOpenHelper();
                }
            }
        }
        return openHelper;
    }

    private DefaultSQLiteOpenHelper() {
    }

    public static class DBHelper extends SQLiteOpenHelper {
        private final Context mContext;
        private final String mDataName;
        private EntityManager.UpdateListener mUpdateListener;

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, EntityManager.UpdateListener updateListener) {
            super(context, name, factory, version);
            this.mDataName = name;
            this.mContext = context;
            this.mUpdateListener = updateListener;
        }

        public void onCreate(SQLiteDatabase db) {
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (this.mUpdateListener != null) {
                this.mUpdateListener.onDatabaseUpgrade(new DefaultSQLiteDatabase(db), oldVersion, newVersion);
            } else {
                dropDatabase(db);
            }
        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (this.mUpdateListener != null) {
                this.mUpdateListener.onDatabaseDowngrade(new DefaultSQLiteDatabase(db), oldVersion, newVersion);
            } else {
                dropDatabase(db);
            }
        }

        private static void dropDatabase(SQLiteDatabase db) {
            if (db != null) {
                Cursor cursor = null;
                try {
                    cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type ='table'", (String[]) null);
                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            String tableName = cursor.getString(0);
                            db.execSQL("DROP TABLE IF EXISTS " + tableName);
                            TableEntity.remove(tableName);
                        }
                    }
                    IOUtils.closeQuietly(cursor);
                } catch (Throwable th) {
                    IOUtils.closeQuietly(cursor);
                    throw th;
                }
            }
        }

        public SQLiteDatabase getWritableDatabase() {
            SQLiteDatabase db;
            synchronized (this) {
                db = null;
                try {
                    db = super.getWritableDatabase();
                } catch (Throwable t) {
                    handleException(t);
                }
            }
            return db;
        }

        public void deleteDatabase() {
            this.mContext.deleteDatabase(this.mDataName);
        }

        private static void handleException(Throwable e) {
            try {
                DbCacheExceptionHandler.getInstance().handleException(e);
            } catch (Throwable th) {
            }
        }
    }
}
