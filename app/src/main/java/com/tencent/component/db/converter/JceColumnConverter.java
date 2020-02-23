package com.tencent.component.db.converter;

import android.database.Cursor;
import com.qq.taf.jce.JceStruct;
import com.tencent.component.utils.ParcelUtil;

public class JceColumnConverter implements ColumnConverter<JceStruct, byte[]> {
    public JceStruct convertString2FiledValue(String fieldStringValue) {
        return null;
    }

    public byte[] fieldValue2ColumnValue(JceStruct fieldValue) {
        return ParcelUtil.writeJceStruct(fieldValue);
    }

    public String getColumnDbType() {
        return "BLOB";
    }

    public byte[] getColumnValue(Cursor cursor, int index) {
        return cursor.getBlob(index);
    }

    public JceStruct columnValue2FiledValue(byte[] columnValue, ClassLoader classLoader) {
        if (columnValue != null) {
            return ParcelUtil.readJceStruct(columnValue, classLoader);
        }
        return null;
    }
}
