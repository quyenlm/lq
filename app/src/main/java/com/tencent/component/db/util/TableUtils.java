package com.tencent.component.db.util;

import android.text.TextUtils;
import com.tencent.component.db.EntityContext;
import com.tencent.component.db.annotation.Id;
import com.tencent.component.db.annotation.Table;
import com.tencent.component.db.converter.ColumnConverterFactory;
import com.tencent.component.db.entity.ColumnEntity;
import com.tencent.component.db.entity.IdEntity;
import com.tencent.component.utils.log.LogUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class TableUtils {
    private static final String TAG = "TableUtils";
    private static ConcurrentHashMap<String, ConcurrentHashMap<String, ColumnEntity>> entityColumnsMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, ArrayList<IdEntity>> entityIdMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Class, String> mTableNameCache = new ConcurrentHashMap<>();

    private TableUtils() {
    }

    public static String getTableName(Class<?> clazz) {
        return getTableName(clazz, (String) null);
    }

    public static String getTableName(Class<?> clazz, String tableName) {
        if (TextUtils.isEmpty(tableName)) {
            tableName = mTableNameCache.get(clazz);
            if (TextUtils.isEmpty(tableName)) {
                Table table = (Table) clazz.getAnnotation(Table.class);
                if (table == null || TextUtils.isEmpty(table.name())) {
                    tableName = clazz.getName().toLowerCase().replace('.', '_');
                } else {
                    tableName = table.name();
                }
                mTableNameCache.put(clazz, tableName);
            }
        }
        return tableName;
    }

    public static int getVersion(Class<?> clazz) {
        Table table = (Table) clazz.getAnnotation(Table.class);
        if (table == null) {
            return 1;
        }
        return table.version();
    }

    public static boolean getDynamicClass(Class<?> clazz) {
        Table table = (Table) clazz.getAnnotation(Table.class);
        if (table == null) {
            return false;
        }
        return table.dynamicClass();
    }

    public static synchronized ConcurrentHashMap<String, ColumnEntity> getColumnMap(Class<?> entityType, EntityContext entityContext) {
        ConcurrentHashMap<String, ColumnEntity> concurrentHashMap;
        synchronized (TableUtils.class) {
            if (entityColumnsMap.containsKey(entityType.getCanonicalName())) {
                concurrentHashMap = entityColumnsMap.get(entityType.getCanonicalName());
            } else {
                ConcurrentHashMap<String, ColumnEntity> columnMap = new ConcurrentHashMap<>();
                addColumns2Map(entityType, columnMap, entityContext);
                entityColumnsMap.put(entityType.getCanonicalName(), columnMap);
                concurrentHashMap = columnMap;
            }
        }
        return concurrentHashMap;
    }

    private static void addColumns2Map(Class<?> entityType, ConcurrentHashMap<String, ColumnEntity> columnMap, EntityContext entityContext) {
        if (entityType != null && !Object.class.equals(entityType)) {
            try {
                for (Field field : entityType.getDeclaredFields()) {
                    if (!ColumnUtils.isTransient(field) && !Modifier.isStatic(field.getModifiers()) && ColumnUtils.isColumn(field) && ColumnConverterFactory.isSupportColumnConverter(field.getType())) {
                        ColumnEntity column = new ColumnEntity(entityType, field, entityContext.getColumnValueProcessor());
                        if (!columnMap.containsKey(column.getColumnName())) {
                            columnMap.put(column.getColumnName(), column);
                        }
                    }
                }
                addColumns2Map(entityType.getSuperclass(), columnMap, entityContext);
            } catch (Throwable e) {
                LogUtil.e(TAG, e.getMessage(), e);
            }
        }
    }

    public static synchronized ArrayList<IdEntity> getIdList(Class<?> entityType) {
        ArrayList<IdEntity> arrayList;
        Field field;
        synchronized (TableUtils.class) {
            if (Object.class.equals(entityType)) {
                arrayList = null;
            } else if (entityIdMap.containsKey(entityType.getCanonicalName())) {
                arrayList = entityIdMap.get(entityType.getCanonicalName());
            } else {
                ArrayList<IdEntity> idEntities = new ArrayList<>();
                Field[] fields = entityType.getDeclaredFields();
                if (fields != null) {
                    for (Field field2 : fields) {
                        if (field2.getAnnotation(Id.class) != null) {
                            idEntities.add(new IdEntity(entityType, field2));
                        }
                    }
                    if (idEntities.isEmpty()) {
                        Field[] arr$ = fields;
                        int len$ = arr$.length;
                        int i$ = 0;
                        while (true) {
                            if (i$ >= len$) {
                                break;
                            }
                            field = arr$[i$];
                            if ("id".equals(field.getName()) || "_id".equals(field.getName())) {
                                idEntities.add(new IdEntity(entityType, field));
                            } else {
                                i$++;
                            }
                        }
                        idEntities.add(new IdEntity(entityType, field));
                    }
                }
                ArrayList<IdEntity> superClassIds = getIdList(entityType.getSuperclass());
                if (superClassIds != null && !superClassIds.isEmpty()) {
                    idEntities.addAll(superClassIds);
                }
                int autoIncrementCount = 0;
                Iterator i$2 = idEntities.iterator();
                while (i$2.hasNext()) {
                    if (i$2.next().isAutoIncrement()) {
                        autoIncrementCount++;
                    }
                    if (autoIncrementCount > 1) {
                        throw new RuntimeException("There's more than one field declared to autoIncrement.");
                    }
                }
                entityIdMap.put(entityType.getCanonicalName(), idEntities);
                arrayList = idEntities;
            }
        }
        return arrayList;
    }
}
