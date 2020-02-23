package com.tencent.component.db.entity;

import android.database.Cursor;
import com.tencent.component.db.ColumnValueProcessor;
import com.tencent.component.db.converter.ColumnConverter;
import com.tencent.component.db.converter.ColumnConverterFactory;
import com.tencent.component.db.util.ColumnUtils;
import com.tencent.component.utils.log.LogUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ColumnEntity<EntityType> {
    public static final String RESERVED_COLUMN_DYNAMIC_CLASS = "_reserved_dynamic_class";
    private static final String TAG = "ColumnEntity";
    protected final ColumnConverter columnConverter;
    protected final Field columnField;
    protected final String columnName;
    private final ColumnValueProcessor columnValueProcessor;
    private final Object defaultValue;
    private final boolean encrypt;
    private final Class<EntityType> entityClass;
    protected final Method getMethod;
    protected final Method setMethod;

    public ColumnEntity(Class<EntityType> entityType, Field field, ColumnValueProcessor processor) {
        this.columnField = field;
        this.columnConverter = ColumnConverterFactory.getColumnConverter(field.getType());
        this.columnName = ColumnUtils.getColumnNameByField(field);
        this.columnValueProcessor = processor;
        this.entityClass = entityType;
        if (this.columnConverter != null) {
            this.defaultValue = this.columnConverter.convertString2FiledValue(ColumnUtils.getColumnDefaultValue(field));
        } else {
            this.defaultValue = null;
        }
        this.encrypt = ColumnUtils.needEncryptColumn(entityType, field);
        this.getMethod = ColumnUtils.getColumnGetMethod(entityType, field);
        this.setMethod = ColumnUtils.getColumnSetMethod(entityType, field);
    }

    public boolean isNeedEncrypt() {
        return this.encrypt;
    }

    public void setValue2Entity(EntityType entity, Cursor cursor, int index, ClassLoader classLoader) {
        Object columnValue = this.columnConverter.getColumnValue(cursor, index);
        if (this.columnValueProcessor != null) {
            columnValue = this.columnValueProcessor.processGet(columnValue, this.entityClass, this.columnName);
        }
        Object value = this.columnConverter.columnValue2FiledValue(columnValue, classLoader);
        if (this.setMethod != null) {
            try {
                Method method = this.setMethod;
                Object[] objArr = new Object[1];
                if (value == null) {
                    value = this.defaultValue;
                }
                objArr[0] = value;
                method.invoke(entity, objArr);
            } catch (Throwable e) {
                LogUtil.e(TAG, e.getMessage(), e);
            }
        } else {
            try {
                this.columnField.setAccessible(true);
                Field field = this.columnField;
                if (value == null) {
                    value = this.defaultValue;
                }
                field.set(entity, value);
            } catch (Throwable e2) {
                LogUtil.e(TAG, e2.getMessage(), e2);
            }
        }
    }

    public Object getColumnValue(EntityType entity) {
        Object columnValue = this.columnConverter.fieldValue2ColumnValue(getFieldValue(entity));
        if (this.columnValueProcessor != null) {
            return this.columnValueProcessor.processSet(columnValue, this.entityClass, this.columnName);
        }
        return columnValue;
    }

    public Object getFieldValue(EntityType entity) {
        if (entity == null) {
            return null;
        }
        if (this.getMethod != null) {
            try {
                return this.getMethod.invoke(entity, new Object[0]);
            } catch (Throwable e) {
                LogUtil.e(TAG, e.getMessage(), e);
                return null;
            }
        } else {
            try {
                this.columnField.setAccessible(true);
                return this.columnField.get(entity);
            } catch (Throwable e2) {
                LogUtil.e(TAG, e2.getMessage(), e2);
                return null;
            }
        }
    }

    public String getColumnName() {
        return this.columnName;
    }

    public Object getDefaultValue() {
        return this.defaultValue;
    }

    public Field getColumnField() {
        return this.columnField;
    }

    public String getColumnDbType() {
        if (this.columnConverter != null) {
            return this.columnConverter.getColumnDbType();
        }
        return "TEXT";
    }
}
