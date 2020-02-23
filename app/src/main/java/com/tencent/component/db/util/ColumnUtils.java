package com.tencent.component.db.util;

import android.text.TextUtils;
import com.tencent.component.db.annotation.Column;
import com.tencent.component.db.annotation.Encrypt;
import com.tencent.component.db.annotation.Id;
import com.tencent.component.db.annotation.Transient;
import com.tencent.component.db.converter.ColumnConverter;
import com.tencent.component.db.converter.ColumnConverterFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ColumnUtils {
    private static final String FIXED_ID_NAME = "_id";
    private static final String RESERVED_ID_NAME = "id";
    private static final String TAG = "ColumnUtils";

    private ColumnUtils() {
    }

    public static Method getColumnGetMethod(Class<?> entityType, Field field) {
        String fieldName = field.getName();
        Method getMethod = null;
        if (field.getType() == Boolean.TYPE) {
            getMethod = getBooleanColumnGetMethod(entityType, fieldName);
        }
        if (getMethod == null) {
            try {
                getMethod = entityType.getDeclaredMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), new Class[0]);
            } catch (NoSuchMethodException e) {
            }
        }
        if (getMethod != null || Object.class.equals(entityType.getSuperclass())) {
            return getMethod;
        }
        return getColumnGetMethod(entityType.getSuperclass(), field);
    }

    public static Method getColumnSetMethod(Class<?> entityType, Field field) {
        String fieldName = field.getName();
        Method setMethod = null;
        if (field.getType() == Boolean.TYPE) {
            setMethod = getBooleanColumnSetMethod(entityType, field);
        }
        if (setMethod == null) {
            try {
                setMethod = entityType.getDeclaredMethod("set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), new Class[]{field.getType()});
            } catch (NoSuchMethodException e) {
            }
        }
        if (setMethod != null || Object.class.equals(entityType.getSuperclass())) {
            return setMethod;
        }
        return getColumnSetMethod(entityType.getSuperclass(), field);
    }

    public static boolean needEncryptColumn(Class<?> cls, Field field) {
        Encrypt encrypt = (Encrypt) field.getAnnotation(Encrypt.class);
        if (encrypt == null) {
            encrypt = (Encrypt) field.getAnnotation(Encrypt.class);
        }
        return encrypt != null;
    }

    public static String getColumnNameByField(Field field) {
        Column column = (Column) field.getAnnotation(Column.class);
        if (column != null && !TextUtils.isEmpty(column.name())) {
            return fixedColumnName(column.name());
        }
        Id id = (Id) field.getAnnotation(Id.class);
        if (id == null || TextUtils.isEmpty(id.name())) {
            return fixedColumnName(field.getName());
        }
        return fixedColumnName(id.name());
    }

    private static String fixedColumnName(String columnName) {
        if (TextUtils.isEmpty(columnName) || !"id".equalsIgnoreCase(columnName)) {
            return columnName;
        }
        return "_id";
    }

    public static String getColumnDefaultValue(Field field) {
        Column column = (Column) field.getAnnotation(Column.class);
        if (column == null || TextUtils.isEmpty(column.defaultValue())) {
            return null;
        }
        return column.defaultValue();
    }

    public static boolean isTransient(Field field) {
        return field.getAnnotation(Transient.class) != null;
    }

    public static boolean isColumn(Field field) {
        return field.getAnnotation(Column.class) != null;
    }

    public static boolean isUnique(Field field) {
        Column column = (Column) field.getAnnotation(Column.class);
        if (column != null) {
            return column.unique();
        }
        return false;
    }

    public static boolean isNullable(Field field) {
        Column column = (Column) field.getAnnotation(Column.class);
        if (column != null) {
            return column.nullable();
        }
        return true;
    }

    public static Object convert2DbColumnValueIfNeeded(Object value) {
        Object result = value;
        if (value == null) {
            return result;
        }
        ColumnConverter converter = ColumnConverterFactory.getColumnConverter(value.getClass());
        if (converter != null) {
            return converter.fieldValue2ColumnValue(value);
        }
        return value;
    }

    private static boolean isStartWithIs(String fieldName) {
        return fieldName != null && fieldName.startsWith("is");
    }

    private static Method getBooleanColumnGetMethod(Class<?> entityType, String fieldName) {
        String methodName = "is" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        if (isStartWithIs(fieldName)) {
            methodName = fieldName;
        }
        try {
            return entityType.getDeclaredMethod(methodName, new Class[0]);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    private static Method getBooleanColumnSetMethod(Class<?> entityType, Field field) {
        String methodName;
        String fieldName = field.getName();
        if (isStartWithIs(field.getName())) {
            methodName = "set" + fieldName.substring(2, 3).toUpperCase() + fieldName.substring(3);
        } else {
            methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        }
        try {
            return entityType.getDeclaredMethod(methodName, new Class[]{field.getType()});
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
}
