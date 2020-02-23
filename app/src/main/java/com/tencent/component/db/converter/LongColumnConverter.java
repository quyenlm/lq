package com.tencent.component.db.converter;

import android.database.Cursor;
import android.text.TextUtils;

public class LongColumnConverter implements ColumnConverter<Long, Long> {
    public Long convertString2FiledValue(String fieldStringValue) {
        if (TextUtils.isEmpty(fieldStringValue)) {
            return null;
        }
        return Long.valueOf(fieldStringValue);
    }

    public Long fieldValue2ColumnValue(Long fieldValue) {
        return fieldValue;
    }

    public String getColumnDbType() {
        return "INTEGER";
    }

    public Long getColumnValue(Cursor cursor, int index) {
        return Long.valueOf(cursor.getLong(index));
    }

    public Long columnValue2FiledValue(Long columnValue, ClassLoader classLoader) {
        return columnValue;
    }
}
