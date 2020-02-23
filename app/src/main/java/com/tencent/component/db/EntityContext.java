package com.tencent.component.db;

public class EntityContext {
    private ClassLoader mClassLoader;
    private EntityManager mEntityManager;
    private String mTableName;

    public EntityContext(EntityManager entityManager, String tableName, ClassLoader classLoader) {
        this.mEntityManager = entityManager;
        this.mTableName = tableName;
        this.mClassLoader = classLoader;
    }

    public String getTableName() {
        return this.mTableName;
    }

    public EntityManager getEntityManager() {
        return this.mEntityManager;
    }

    public ClassLoader getClassLoader() {
        return this.mClassLoader;
    }

    public synchronized ColumnValueProcessor getColumnValueProcessor() {
        return null;
    }
}
