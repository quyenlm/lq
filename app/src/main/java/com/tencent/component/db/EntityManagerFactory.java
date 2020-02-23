package com.tencent.component.db;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.component.db.EntityManager;
import com.tencent.component.db.exception.DBException;
import com.tencent.component.db.sqlite.WhereBuilder;
import com.tencent.component.db.util.TableUtils;
import com.tencent.component.utils.AssertUtil;
import com.tencent.component.utils.DebugUtil;
import com.tencent.component.utils.SecurityUtil;
import com.tencent.component.utils.log.LogUtil;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class EntityManagerFactory {
    private static final String EMPTY_USER_ACCOUNT = String.valueOf(Integer.MIN_VALUE);
    private static final String TAG = "EntityManagerFactory";
    private static volatile HashMap<String, EntityManagerFactory> sFactoryMap = new HashMap<>();
    /* access modifiers changed from: private */
    public final HashMap<String, EntityManagerRecord> mActiveRecords = new HashMap<>();
    private EntityManager.OnCloseListener mCacheCloseListener = new EntityManager.OnCloseListener() {
        public void onClosed(EntityManager cacheManager) {
            synchronized (EntityManagerFactory.this.mActiveRecords) {
                EntityManagerFactory.this.mActiveRecords.remove((String) EntityManagerFactory.this.mKeysMap.remove(cacheManager));
            }
        }
    };
    private final Context mContext;
    private int mDBVersion;
    private String mDatabaseName;
    private final DefaultUpdateListener mDefaultUpdateListener = new DefaultUpdateListener();
    /* access modifiers changed from: private */
    public final HashMap<EntityManager<?>, String> mKeysMap = new HashMap<>();
    private ISQLiteOpenHelper mOpenHelper;
    private EntityManager.UpdateListener mUpdateListenerProxy = new EntityManager.UpdateListener() {
        public void onDatabaseUpgrade(ISQLiteDatabase db, int oldVersion, int newVersion) {
            Iterator i$ = EntityManagerFactory.this.mUpdateListenerSet.iterator();
            while (i$.hasNext()) {
                EntityManager.UpdateListener listener = (EntityManager.UpdateListener) i$.next();
                if (listener != null) {
                    listener.onDatabaseUpgrade(db, oldVersion, newVersion);
                }
            }
        }

        public void onDatabaseDowngrade(ISQLiteDatabase db, int oldVersion, int newVersion) {
            Iterator i$ = EntityManagerFactory.this.mUpdateListenerSet.iterator();
            while (i$.hasNext()) {
                EntityManager.UpdateListener listener = (EntityManager.UpdateListener) i$.next();
                if (listener != null) {
                    listener.onDatabaseDowngrade(db, oldVersion, newVersion);
                }
            }
        }

        public void onTableUpgrade(ISQLiteDatabase db, String tableName, int oldVersion, int newVersion) {
            Iterator i$ = EntityManagerFactory.this.mUpdateListenerSet.iterator();
            while (i$.hasNext()) {
                EntityManager.UpdateListener listener = (EntityManager.UpdateListener) i$.next();
                if (listener != null) {
                    listener.onTableUpgrade(db, tableName, oldVersion, newVersion);
                }
            }
        }

        public void onTableDowngrade(ISQLiteDatabase db, String tableName, int oldVersion, int newVersion) {
            Iterator i$ = EntityManagerFactory.this.mUpdateListenerSet.iterator();
            while (i$.hasNext()) {
                EntityManager.UpdateListener listener = (EntityManager.UpdateListener) i$.next();
                if (listener != null) {
                    listener.onTableDowngrade(db, tableName, oldVersion, newVersion);
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public final HashSet<EntityManager.UpdateListener> mUpdateListenerSet = new HashSet<>();
    private String mUserAccount;

    private EntityManagerFactory(Context context, int dbVersion, String userAccount, ISQLiteOpenHelper openHelper) {
        this.mContext = context.getApplicationContext();
        this.mUserAccount = userAccount;
        if (DebugUtil.isDebuggable()) {
            this.mDatabaseName = "db_" + userAccount;
        } else {
            this.mDatabaseName = SecurityUtil.encrypt(userAccount);
        }
        this.mDBVersion = dbVersion;
        openHelper = openHelper == null ? DefaultSQLiteOpenHelper.getInstance(userAccount) : openHelper;
        openHelper.init(this.mContext, this.mDatabaseName, this.mDBVersion, this.mUpdateListenerProxy);
        this.mOpenHelper = openHelper;
    }

    public void addUpdateListener(EntityManager.UpdateListener updateListener) {
        if (updateListener != null) {
            this.mUpdateListenerSet.add(updateListener);
        } else {
            this.mUpdateListenerSet.add(this.mDefaultUpdateListener);
        }
    }

    public <T> EntityManager<T> getEntityManager(Class<T> clazz, String table) {
        return getEntityManager(clazz, table, (ClassLoader) null, false);
    }

    public <T> EntityManager<T> getEntityManager(Class<T> clazz, String table, ClassLoader classLoader, boolean persist) {
        EntityManager<T> entityManager;
        if (clazz == null) {
            throw new RuntimeException("invalid Entity class: null");
        }
        String table2 = TableUtils.getTableName(clazz, table);
        if (TextUtils.isEmpty(table2)) {
            throw new RuntimeException("invalid table name: " + table2);
        }
        synchronized (this.mActiveRecords) {
            String key = uniqueKey(this.mUserAccount, table2, persist);
            EntityManagerRecord record = this.mActiveRecords.get(key);
            if (!(record == null || record.db == null || record.db.getEntityClass() == clazz)) {
                record = null;
            }
            if (record == null || record.db == null || record.db.isClosed()) {
                if (classLoader == null) {
                    classLoader = getClass().getClassLoader();
                }
                EntityManager<T> cacheManager = new EntityManager<>(this.mContext, clazz, this.mUpdateListenerProxy, this.mDatabaseName, table2, classLoader, this.mOpenHelper);
                cacheManager.setOnCloseListener(this.mCacheCloseListener);
                record = new EntityManagerRecord(cacheManager, this.mUserAccount, persist);
                this.mActiveRecords.put(key, record);
                this.mKeysMap.put(cacheManager, key);
            }
            entityManager = record.db;
        }
        return entityManager;
    }

    public void close() {
        close(EMPTY_USER_ACCOUNT);
    }

    public void close(String userAccount) {
        synchronized (this.mActiveRecords) {
            Iterator<EntityManagerRecord> iterator = this.mActiveRecords.values().iterator();
            while (iterator.hasNext()) {
                EntityManagerRecord record = iterator.next();
                if (record == null) {
                    iterator.remove();
                } else if (EMPTY_USER_ACCOUNT.equals(userAccount) || record.userAccount == userAccount) {
                    record.db.setOnCloseListener((EntityManager.OnCloseListener) null);
                    record.db.close();
                    iterator.remove();
                    this.mKeysMap.remove(record.db);
                }
            }
        }
    }

    public void clear() {
        clear(EMPTY_USER_ACCOUNT);
    }

    public void clear(String userAccount) {
        synchronized (this.mActiveRecords) {
            for (EntityManagerRecord record : this.mActiveRecords.values()) {
                if (record != null && !record.persist) {
                    if (EMPTY_USER_ACCOUNT.equals(userAccount) || record.userAccount == userAccount) {
                        try {
                            record.db.delete((WhereBuilder) null);
                        } catch (DBException e) {
                            LogUtil.e(TAG, e.getMessage(), e);
                        }
                    }
                }
            }
        }
    }

    private static String uniqueKey(String userAccount, String table, boolean persist) {
        return userAccount + "_" + table + "_" + persist;
    }

    static final class EntityManagerRecord {
        public final EntityManager db;
        public final boolean persist;
        public final String userAccount;

        public EntityManagerRecord(EntityManager db2, String userAccount2, boolean persist2) {
            AssertUtil.assertTrue(db2 != null);
            this.db = db2;
            this.userAccount = userAccount2;
            this.persist = persist2;
        }
    }

    public static EntityManagerFactory getInstance(Context context, int dbVersion, String userAccount, ISQLiteOpenHelper openHelper, EntityManager.UpdateListener updateListener) {
        if (TextUtils.isEmpty(userAccount)) {
            userAccount = EMPTY_USER_ACCOUNT;
        }
        EntityManagerFactory entityManagerFactory = sFactoryMap.get(userAccount);
        if (entityManagerFactory == null) {
            synchronized (sFactoryMap) {
                entityManagerFactory = sFactoryMap.get(userAccount);
                if (entityManagerFactory == null) {
                    EntityManagerFactory entityManagerFactory2 = new EntityManagerFactory(context, dbVersion, userAccount, openHelper);
                    try {
                        sFactoryMap.put(userAccount, entityManagerFactory2);
                        entityManagerFactory = entityManagerFactory2;
                    } catch (Throwable th) {
                        th = th;
                        EntityManagerFactory entityManagerFactory3 = entityManagerFactory2;
                        throw th;
                    }
                }
                try {
                } catch (Throwable th2) {
                    th = th2;
                    throw th;
                }
            }
        }
        entityManagerFactory.addUpdateListener(updateListener);
        return entityManagerFactory;
    }
}
