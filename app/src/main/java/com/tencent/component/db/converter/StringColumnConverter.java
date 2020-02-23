package com.tencent.component.db.converter;

import android.database.Cursor;

public class StringColumnConverter implements ColumnConverter<String, String> {
    public String convertString2FiledValue(String fieldStringValue) {
        return fieldStringValue;
    }

    public String fieldValue2ColumnValue(String fieldValue) {
        return fieldValue;
    }

    public String getColumnDbType() {
        return "TEXT";
    }

    public String getColumnValue(Cursor cursor, int index) {
        return cursor.getString(index);
    }

    public String columnValue2FiledValue(String columnValue, ClassLoader classLoader) {
        return columnValue;
    }
}
