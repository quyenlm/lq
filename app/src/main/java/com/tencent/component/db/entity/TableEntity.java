package com.tencent.component.db.entity;

import android.text.TextUtils;
import com.tencent.component.db.EntityContext;
import com.tencent.component.db.util.TableUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TableEntity {
    private static final ConcurrentHashMap<String, TableEntity> tableMap = new ConcurrentHashMap<>();
    public final ConcurrentHashMap<String, ColumnEntity> columnMap;
    private boolean dynamicClass;
    private Class<?> entityType;
    private ArrayList<IdEntity> idList;
    private String tableName;
    private int version;

    private TableEntity(Class<?> entityType2, EntityContext entityContext) {
        this.tableName = TableUtils.getTableName(entityType2, entityContext.getTableName());
        this.idList = TableUtils.getIdList(entityType2);
        this.version = TableUtils.getVersion(entityType2);
        this.dynamicClass = TableUtils.getDynamicClass(entityType2);
        this.columnMap = TableUtils.getColumnMap(entityType2, entityContext);
        this.entityType = entityType2;
    }

    public static synchronized TableEntity get(Class<?> entityType2, EntityContext entityContext) {
        TableEntity table;
        synchronized (TableEntity.class) {
            String key = entityType2.getCanonicalName() + "_" + TableUtils.getTableName(entityType2, entityContext.getTableName());
            table = tableMap.get(key);
            if (!(table == null || table.entityType == entityType2)) {
                table = null;
            }
            if (table == null) {
                table = new TableEntity(entityType2, entityContext);
                tableMap.put(key, table);
            }
        }
        return table;
    }

    public static synchronized void remove(Class<?> entityType2) {
        synchronized (TableEntity.class) {
            if (entityType2 != null) {
                tableMap.remove(entityType2.getCanonicalName());
            }
        }
    }

    public static synchronized void remove(String tableName2) {
        synchronized (TableEntity.class) {
            if (tableMap.size() > 0) {
                String key = null;
                Iterator i$ = tableMap.entrySet().iterator();
                while (true) {
                    if (i$.hasNext()) {
                        Map.Entry<String, TableEntity> entry = i$.next();
                        TableEntity table = entry.getValue();
                        if (table != null && table.getTableName().equals(tableName2)) {
                            key = entry.getKey();
                            break;
                        }
                    } else {
                        break;
                    }
                }
                if (!TextUtils.isEmpty(key)) {
                    tableMap.remove(key);
                }
            }
        }
    }

    public ColumnEntity getColumnEntity(String columnName) {
        return this.columnMap.get(columnName);
    }

    public String getTableName() {
        return this.tableName;
    }

    public ArrayList<IdEntity> getIdList() {
        return this.idList;
    }

    public int getVersion() {
        return this.version;
    }

    public boolean isDynamicClass() {
        return this.dynamicClass;
    }
}
