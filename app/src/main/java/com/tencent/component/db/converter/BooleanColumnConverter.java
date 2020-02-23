package com.tencent.component.db.converter;

import android.database.Cursor;
import android.text.TextUtils;

public class BooleanColumnConverter implements ColumnConverter<Boolean, Integer> {
    public Integer getColumnValue(Cursor cursor, int index) {
        return Integer.valueOf(cursor.getInt(index));
    }

    public Boolean columnValue2FiledValue(Integer columnValue, ClassLoader classLoader) {
        boolean z = true;
        if (columnValue.intValue() != 1) {
            z = false;
        }
        return Boolean.valueOf(z);
    }

    public Boolean convertString2FiledValue(String fieldStringValue) {
        if (TextUtils.isEmpty(fieldStringValue)) {
            return null;
        }
        return Boolean.valueOf(fieldStringValue.length() == 1 ? "1".equals(fieldStringValue) : Boolean.valueOf(fieldStringValue).booleanValue());
    }

    public Integer fieldValue2ColumnValue(Boolean fieldValue) {
        if (fieldValue == null) {
            return null;
        }
        return Integer.valueOf(fieldValue.booleanValue() ? 1 : 0);
    }

    public String getColumnDbType() {
        return "INTEGER";
    }
}
