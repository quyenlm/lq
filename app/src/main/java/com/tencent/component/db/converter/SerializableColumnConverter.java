package com.tencent.component.db.converter;

import android.database.Cursor;
import com.tencent.component.utils.ParcelUtil;
import java.io.Serializable;

public class SerializableColumnConverter implements ColumnConverter<Serializable, byte[]> {
    public Serializable convertString2FiledValue(String fieldStringValue) {
        return null;
    }

    public byte[] fieldValue2ColumnValue(Serializable fieldValue) {
        return ParcelUtil.writeSerializable(fieldValue);
    }

    public String getColumnDbType() {
        return "BLOB";
    }

    public byte[] getColumnValue(Cursor cursor, int index) {
        return cursor.getBlob(index);
    }

    public Serializable columnValue2FiledValue(byte[] columnValue, ClassLoader classLoader) {
        return ParcelUtil.readSerializable(columnValue);
    }
}
