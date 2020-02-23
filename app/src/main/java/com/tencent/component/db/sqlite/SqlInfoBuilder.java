package com.tencent.component.db.sqlite;

import com.tencent.component.db.EntityContext;
import com.tencent.component.db.entity.ColumnEntity;
import com.tencent.component.db.entity.IdEntity;
import com.tencent.component.db.entity.TableEntity;
import com.tencent.component.db.exception.DBException;
import com.tencent.component.db.util.ColumnUtils;
import com.tencent.component.utils.KeyValue;
import com.tencent.imsdk.framework.request.HttpRequest;
import com.tencent.tp.a.h;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class SqlInfoBuilder {
    private SqlInfoBuilder() {
    }

    public static SqlInfo buildInsertSqlInfo(Class<?> entityType, Object entity, EntityContext entityContext) throws DBException {
        List<KeyValue> keyValueList = collectInsertKeyValues(entityType, entity, entityContext);
        if (keyValueList.size() == 0) {
            return null;
        }
        SqlInfo result = new SqlInfo();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("INSERT INTO ");
        sqlBuffer.append(TableEntity.get(entityType, entityContext).getTableName());
        sqlBuffer.append(" (");
        for (KeyValue kv : keyValueList) {
            sqlBuffer.append(kv.getKey()).append(",");
            result.addBindArgWithoutConverter(kv.getValue());
        }
        sqlBuffer.deleteCharAt(sqlBuffer.length() - 1);
        sqlBuffer.append(") VALUES (");
        int length = keyValueList.size();
        for (int i = 0; i < length; i++) {
            sqlBuffer.append("?,");
        }
        sqlBuffer.deleteCharAt(sqlBuffer.length() - 1);
        sqlBuffer.append(h.b);
        result.setSql(sqlBuffer.toString());
        return result;
    }

    public static SqlInfo buildReplaceSqlInfo(Class<?> entityType, Object entity, EntityContext entityContext) throws DBException {
        List<KeyValue> keyValueList = collectReplaceKeyValues(entityType, entity, entityContext);
        if (keyValueList.size() == 0) {
            return null;
        }
        SqlInfo result = new SqlInfo();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("REPLACE INTO ");
        sqlBuffer.append(TableEntity.get(entityType, entityContext).getTableName());
        sqlBuffer.append(" (");
        for (KeyValue kv : keyValueList) {
            sqlBuffer.append(kv.getKey()).append(",");
            result.addBindArgWithoutConverter(kv.getValue());
        }
        sqlBuffer.deleteCharAt(sqlBuffer.length() - 1);
        sqlBuffer.append(") VALUES (");
        int length = keyValueList.size();
        for (int i = 0; i < length; i++) {
            sqlBuffer.append("?,");
        }
        sqlBuffer.deleteCharAt(sqlBuffer.length() - 1);
        sqlBuffer.append(h.b);
        result.setSql(sqlBuffer.toString());
        return result;
    }

    private static String buildDeleteSqlByTableName(String tableName) {
        return "DELETE FROM " + tableName;
    }

    public static SqlInfo buildDeleteByObjectSqlInfo(Class<?> entityType, Object entity, EntityContext entityContext) throws DBException {
        SqlInfo result = new SqlInfo();
        TableEntity table = TableEntity.get(entityType, entityContext);
        StringBuilder sb = new StringBuilder(buildDeleteSqlByTableName(table.getTableName()));
        ArrayList<IdEntity> idEntities = table.getIdList();
        if (idEntities == null || idEntities.size() == 0) {
            throw new DBException("this entity[" + entity.getClass() + "] can't find id field");
        }
        WhereBuilder whereBuilder = null;
        Iterator i$ = idEntities.iterator();
        while (i$.hasNext()) {
            IdEntity id = i$.next();
            Object idValue = id.getColumnValue(entity);
            if (idValue == null) {
                throw new DBException("this entity[" + entity.getClass() + "]'s id value is null");
            } else if (whereBuilder == null) {
                whereBuilder = WhereBuilder.create(id.getColumnName(), HttpRequest.HTTP_REQ_ENTITY_MERGE, idValue);
            } else {
                whereBuilder.and(id.getColumnName(), HttpRequest.HTTP_REQ_ENTITY_MERGE, idValue);
            }
        }
        if (whereBuilder != null) {
            sb.append(" WHERE ");
            sb.append(whereBuilder);
        }
        result.setSql(sb.toString());
        return result;
    }

    public static SqlInfo buildDeleteSqlInfo(Class<?> entityType, Object idValue, EntityContext entityContext) throws DBException {
        SqlInfo result = new SqlInfo();
        TableEntity table = TableEntity.get(entityType, entityContext);
        ArrayList<IdEntity> idEntities = table.getIdList();
        if (idEntities == null || idEntities.isEmpty() || idEntities.size() > 1) {
            throw new DBException("BuildDelteSqlInfo failed(there's more than one idEntity)");
        }
        IdEntity id = idEntities.get(0);
        StringBuilder sb = new StringBuilder(buildDeleteSqlByTableName(table.getTableName()));
        if (idValue != null) {
            sb.append(" WHERE ").append(WhereBuilder.create(id.getColumnName(), HttpRequest.HTTP_REQ_ENTITY_MERGE, idValue));
        }
        result.setSql(sb.toString());
        return result;
    }

    public static SqlInfo buildDeleteSqlInfo(Class<?> entityType, WhereBuilder whereBuilder, EntityContext entityContext) throws DBException {
        StringBuilder sb = new StringBuilder(buildDeleteSqlByTableName(TableEntity.get(entityType, entityContext).getTableName()));
        if (whereBuilder != null && whereBuilder.getWhereItemSize() > 0) {
            sb.append(" WHERE ").append(whereBuilder.toString());
        }
        return new SqlInfo(sb.toString());
    }

    public static SqlInfo buildUpdateSqlInfo(EntityContext entityContext, Class<?> entityType, Object entity, String... updateColumnNames) throws DBException {
        List<KeyValue> keyValueList = collectUpdateKeyValues(entityType, entity, entityContext);
        if (keyValueList.size() == 0) {
            return null;
        }
        HashSet<String> updateColumnNameSet = null;
        if (updateColumnNames != null && updateColumnNames.length > 0) {
            updateColumnNameSet = new HashSet<>(updateColumnNames.length);
            Collections.addAll(updateColumnNameSet, updateColumnNames);
        }
        TableEntity table = TableEntity.get(entityType, entityContext);
        SqlInfo result = new SqlInfo();
        StringBuffer sqlBuffer = new StringBuffer("UPDATE ");
        sqlBuffer.append(table.getTableName());
        sqlBuffer.append(" SET ");
        for (KeyValue kv : keyValueList) {
            if (updateColumnNameSet == null || updateColumnNameSet.contains(kv.getKey())) {
                sqlBuffer.append(kv.getKey()).append("=?,");
                result.addBindArgWithoutConverter(kv.getValue());
            }
        }
        sqlBuffer.deleteCharAt(sqlBuffer.length() - 1);
        ArrayList<IdEntity> idEntities = table.getIdList();
        if (idEntities == null || idEntities.size() == 0) {
            throw new DBException("this entity[" + entity.getClass() + "] can't find id field");
        }
        WhereBuilder whereBuilder = null;
        Iterator i$ = idEntities.iterator();
        while (i$.hasNext()) {
            IdEntity id = i$.next();
            Object idValue = id.getColumnValue(entity);
            if (idValue == null) {
                throw new DBException("this entity[" + entity.getClass() + "]'s id value is null");
            } else if (whereBuilder == null) {
                whereBuilder = WhereBuilder.create(id.getColumnName(), HttpRequest.HTTP_REQ_ENTITY_MERGE, idValue);
            } else {
                whereBuilder.and(id.getColumnName(), HttpRequest.HTTP_REQ_ENTITY_MERGE, idValue);
            }
        }
        if (whereBuilder != null) {
            sqlBuffer.append(" WHERE ");
            sqlBuffer.append(whereBuilder);
        }
        result.setSql(sqlBuffer.toString());
        return result;
    }

    public static SqlInfo buildUpdateSqlInfo(EntityContext entityContext, Class<?> entityType, Object entity, WhereBuilder whereBuilder, String... updateColumnNames) throws DBException {
        List<KeyValue> keyValueList = collectUpdateKeyValues(entityType, entity, entityContext);
        if (keyValueList.size() == 0) {
            return null;
        }
        HashSet<String> updateColumnNameSet = null;
        if (updateColumnNames != null && updateColumnNames.length > 0) {
            updateColumnNameSet = new HashSet<>(updateColumnNames.length);
            Collections.addAll(updateColumnNameSet, updateColumnNames);
        }
        TableEntity table = TableEntity.get(entityType, entityContext);
        SqlInfo result = new SqlInfo();
        StringBuffer sqlBuffer = new StringBuffer("UPDATE ");
        sqlBuffer.append(table.getTableName());
        sqlBuffer.append(" SET ");
        for (KeyValue kv : keyValueList) {
            if (updateColumnNameSet == null || updateColumnNameSet.contains(kv.getKey())) {
                sqlBuffer.append(kv.getKey()).append("=?,");
                result.addBindArgWithoutConverter(kv.getValue());
            }
        }
        sqlBuffer.deleteCharAt(sqlBuffer.length() - 1);
        if (whereBuilder != null && whereBuilder.getWhereItemSize() > 0) {
            sqlBuffer.append(" WHERE ").append(whereBuilder.toString());
        }
        result.setSql(sqlBuffer.toString());
        return result;
    }

    public static SqlInfo buildCreateTableSqlInfo(Class<?> entityType, EntityContext entityContext) throws DBException {
        boolean isMultiId = true;
        TableEntity table = TableEntity.get(entityType, entityContext);
        ArrayList<IdEntity> idEntities = table.getIdList();
        if (idEntities == null || idEntities.isEmpty()) {
            return null;
        }
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("CREATE TABLE IF NOT EXISTS ");
        sqlBuffer.append(entityContext.getTableName());
        sqlBuffer.append(" ( ");
        if (idEntities.size() <= 1) {
            isMultiId = false;
        }
        Iterator i$ = idEntities.iterator();
        while (i$.hasNext()) {
            IdEntity id = i$.next();
            if (isMultiId && id.isAutoIncrement()) {
                throw new DBException("Not support auto increment column when declared composite primary key!");
            } else if (isMultiId || !id.isAutoIncrement()) {
                sqlBuffer.append("\"").append(id.getColumnName()).append("\"  ").append(id.getColumnDbType());
                if (!isMultiId) {
                    sqlBuffer.append(" PRIMARY KEY");
                }
                sqlBuffer.append(",");
            } else {
                sqlBuffer.append("\"").append(id.getColumnName()).append("\"  ").append("INTEGER PRIMARY KEY AUTOINCREMENT,");
            }
        }
        for (ColumnEntity column : table.columnMap.values()) {
            sqlBuffer.append("\"").append(column.getColumnName()).append("\"  ");
            sqlBuffer.append(column.getColumnDbType());
            if (ColumnUtils.isUnique(column.getColumnField())) {
                sqlBuffer.append(" UNIQUE");
            }
            if (!ColumnUtils.isNullable(column.getColumnField())) {
                sqlBuffer.append(" NOT NULL");
            }
            sqlBuffer.append(",");
        }
        if (table.isDynamicClass()) {
            sqlBuffer.append("\"").append(ColumnEntity.RESERVED_COLUMN_DYNAMIC_CLASS).append("\"  ");
            sqlBuffer.append(" TEXT,");
        }
        if (isMultiId) {
            sqlBuffer.append(" PRIMARY KEY(");
            Iterator i$2 = idEntities.iterator();
            while (i$2.hasNext()) {
                sqlBuffer.append(i$2.next().getColumnName()).append(",");
            }
        }
        sqlBuffer.deleteCharAt(sqlBuffer.length() - 1);
        if (isMultiId) {
            sqlBuffer.append(" )");
        }
        sqlBuffer.append(" )");
        return new SqlInfo(sqlBuffer.toString());
    }

    private static KeyValue column2KeyValue(Object entity, ColumnEntity column) {
        String key = column.getColumnName();
        Object value = column.getColumnValue(entity);
        if (value == null) {
            value = column.getDefaultValue();
        }
        if (key != null) {
            return new KeyValue(key, value);
        }
        return null;
    }

    public static List<KeyValue> collectReplaceKeyValues(Class<?> entityType, Object entity, EntityContext entityContext) {
        Object columnValue;
        List<KeyValue> keyValueList = new ArrayList<>();
        TableEntity table = TableEntity.get(entityType, entityContext);
        ArrayList<IdEntity> idEntities = table.getIdList();
        if (idEntities == null) {
            return null;
        }
        Iterator i$ = idEntities.iterator();
        while (i$.hasNext()) {
            IdEntity id = i$.next();
            if (!id.isAutoIncrement()) {
                if (id.isUUIDGenerationType()) {
                    columnValue = UUID.randomUUID().toString();
                } else {
                    columnValue = id.getColumnValue(entity);
                }
                keyValueList.add(new KeyValue(id.getColumnName(), columnValue));
            } else {
                long idValue = 0;
                try {
                    idValue = ((Number) id.getColumnValue(entity)).longValue();
                } catch (Exception e) {
                }
                if (idValue > 0) {
                    keyValueList.add(new KeyValue(id.getColumnName(), Long.valueOf(idValue)));
                }
            }
        }
        if (table.isDynamicClass()) {
            keyValueList.add(new KeyValue(ColumnEntity.RESERVED_COLUMN_DYNAMIC_CLASS, entity.getClass().getName()));
        }
        for (ColumnEntity column : table.columnMap.values()) {
            KeyValue kv = column2KeyValue(entity, column);
            if (kv != null) {
                keyValueList.add(kv);
            }
        }
        return keyValueList;
    }

    public static List<KeyValue> collectInsertKeyValues(Class<?> entityType, Object entity, EntityContext entityContext) {
        Object columnValue;
        List<KeyValue> keyValueList = new ArrayList<>();
        TableEntity table = TableEntity.get(entityType, entityContext);
        ArrayList<IdEntity> idEntities = table.getIdList();
        if (idEntities == null) {
            return null;
        }
        Iterator i$ = idEntities.iterator();
        while (i$.hasNext()) {
            IdEntity id = i$.next();
            if (!id.isAutoIncrement()) {
                if (id.isUUIDGenerationType()) {
                    columnValue = UUID.randomUUID().toString();
                } else {
                    columnValue = id.getColumnValue(entity);
                }
                keyValueList.add(new KeyValue(id.getColumnName(), columnValue));
            }
        }
        if (table.isDynamicClass()) {
            keyValueList.add(new KeyValue(ColumnEntity.RESERVED_COLUMN_DYNAMIC_CLASS, entity.getClass().getName()));
        }
        for (ColumnEntity column : table.columnMap.values()) {
            KeyValue kv = column2KeyValue(entity, column);
            if (kv != null) {
                keyValueList.add(kv);
            }
        }
        return keyValueList;
    }

    public static List<KeyValue> collectUpdateKeyValues(Class<?> entityType, Object entity, EntityContext entityContext) {
        List<KeyValue> keyValueList = new ArrayList<>();
        for (ColumnEntity column : TableEntity.get(entityType, entityContext).columnMap.values()) {
            KeyValue kv = column2KeyValue(entity, column);
            if (kv != null) {
                keyValueList.add(kv);
            }
        }
        return keyValueList;
    }
}
