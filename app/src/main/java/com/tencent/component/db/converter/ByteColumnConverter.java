package com.tencent.component.db.converter;

import android.database.Cursor;
import android.text.TextUtils;

public class ByteColumnConverter implements ColumnConverter<Byte, Byte> {
    public Byte convertString2FiledValue(String fieldStringValue) {
        if (TextUtils.isEmpty(fieldStringValue)) {
            return null;
        }
        return Byte.valueOf(fieldStringValue);
    }

    public Byte fieldValue2ColumnValue(Byte fieldValue) {
        return fieldValue;
    }

    public String getColumnDbType() {
        return "INTEGER";
    }

    public Byte getColumnValue(Cursor cursor, int index) {
        return Byte.valueOf((byte) cursor.getInt(index));
    }

    public Byte columnValue2FiledValue(Byte columnValue, ClassLoader classLoader) {
        return columnValue;
    }
}
