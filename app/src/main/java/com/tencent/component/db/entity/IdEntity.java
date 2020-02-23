package com.tencent.component.db.entity;

import com.tencent.component.db.ColumnValueProcessor;
import com.tencent.component.db.annotation.Id;
import com.tencent.component.utils.log.LogUtil;
import java.lang.reflect.Field;

public class IdEntity<T> extends ColumnEntity {
    private static final String TAG = "IdEntity";

    public IdEntity(Class<T> entityType, Field field) {
        super(entityType, field, (ColumnValueProcessor) null);
    }

    public boolean isAutoIncrement() {
        Id id = (Id) getColumnField().getAnnotation(Id.class);
        if (id == null || id.strategy() != 3) {
            return false;
        }
        Class<?> idType = getColumnField().getType();
        if (idType.equals(Integer.TYPE) || idType.equals(Integer.class) || idType.equals(Long.TYPE) || idType.equals(Long.class)) {
            return true;
        }
        return false;
    }

    public boolean isUUIDGenerationType() {
        Id id = (Id) getColumnField().getAnnotation(Id.class);
        if (id == null || id.strategy() != 2) {
            return false;
        }
        return getColumnField().getType().equals(String.class);
    }

    public void setAutoIncrementId(Object entity, long value) {
        Object valueOf = Long.valueOf(value);
        Class<?> columnFieldType = this.columnField.getType();
        if (columnFieldType.equals(Integer.TYPE) || columnFieldType.equals(Integer.class)) {
            valueOf = Integer.valueOf((int) value);
        }
        setIdValue(entity, valueOf);
    }

    public void setIdValue(Object entity, Object idValue) {
        if (this.setMethod != null) {
            try {
                this.setMethod.invoke(entity, new Object[]{idValue});
            } catch (Throwable e) {
                LogUtil.e(TAG, e.getMessage(), e);
            }
        } else {
            try {
                this.columnField.setAccessible(true);
                this.columnField.set(entity, idValue);
            } catch (Throwable e2) {
                LogUtil.e(TAG, e2.getMessage(), e2);
            }
        }
    }

    public Object getColumnValue(Object entity) {
        Object idValue = super.getColumnValue(entity);
        if (idValue == null) {
            return null;
        }
        if (isAutoIncrement() && (idValue.equals(0) || idValue.equals(0L))) {
            return null;
        }
        if (!isUUIDGenerationType() || !idValue.equals("")) {
            return idValue;
        }
        return null;
    }
}
