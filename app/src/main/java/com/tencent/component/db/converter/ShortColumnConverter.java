package com.tencent.component.db.converter;

import android.database.Cursor;
import android.text.TextUtils;

public class ShortColumnConverter implements ColumnConverter<Short, Short> {
    public Short convertString2FiledValue(String fieldStringValue) {
        if (TextUtils.isEmpty(fieldStringValue)) {
            return null;
        }
        return Short.valueOf(fieldStringValue);
    }

    public Short fieldValue2ColumnValue(Short fieldValue) {
        return fieldValue;
    }

    public String getColumnDbType() {
        return "INTEGER";
    }

    public Short getColumnValue(Cursor cursor, int index) {
        return Short.valueOf(cursor.getShort(index));
    }

    public Short columnValue2FiledValue(Short columnValue, ClassLoader classLoader) {
        return columnValue;
    }
}
