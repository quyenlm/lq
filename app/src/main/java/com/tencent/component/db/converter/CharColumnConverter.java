package com.tencent.component.db.converter;

import android.database.Cursor;
import android.text.TextUtils;

public class CharColumnConverter implements ColumnConverter<Character, Integer> {
    public Character convertString2FiledValue(String fieldStringValue) {
        if (TextUtils.isEmpty(fieldStringValue)) {
            return null;
        }
        return Character.valueOf(fieldStringValue.charAt(0));
    }

    public Integer fieldValue2ColumnValue(Character fieldValue) {
        if (fieldValue == null) {
            return null;
        }
        return Integer.valueOf(fieldValue.charValue());
    }

    public String getColumnDbType() {
        return "INTEGER";
    }

    public Integer getColumnValue(Cursor cursor, int index) {
        return Integer.valueOf(cursor.getInt(index));
    }

    public Character columnValue2FiledValue(Integer columnValue, ClassLoader classLoader) {
        return Character.valueOf((char) columnValue.intValue());
    }
}
