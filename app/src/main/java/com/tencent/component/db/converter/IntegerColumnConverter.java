package com.tencent.component.db.converter;

import android.database.Cursor;
import android.text.TextUtils;

public class IntegerColumnConverter implements ColumnConverter<Integer, Integer> {
    public Integer convertString2FiledValue(String fieldStringValue) {
        if (TextUtils.isEmpty(fieldStringValue)) {
            return null;
        }
        return Integer.valueOf(fieldStringValue);
    }

    public Integer fieldValue2ColumnValue(Integer fieldValue) {
        return fieldValue;
    }

    public String getColumnDbType() {
        return "INTEGER";
    }

    public Integer getColumnValue(Cursor cursor, int index) {
        return Integer.valueOf(cursor.getInt(index));
    }

    public Integer columnValue2FiledValue(Integer columnValue, ClassLoader classLoader) {
        return columnValue;
    }
}
