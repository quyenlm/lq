package com.tencent.component.db.converter;

import android.database.Cursor;

public class ByteArrayColumnConverter implements ColumnConverter<byte[], byte[]> {
    public byte[] convertString2FiledValue(String fieldStringValue) {
        return null;
    }

    public byte[] fieldValue2ColumnValue(byte[] fieldValue) {
        return fieldValue;
    }

    public String getColumnDbType() {
        return "BLOB";
    }

    public byte[] getColumnValue(Cursor cursor, int index) {
        return cursor.getBlob(index);
    }

    public byte[] columnValue2FiledValue(byte[] columnValue, ClassLoader classLoader) {
        return columnValue;
    }
}
