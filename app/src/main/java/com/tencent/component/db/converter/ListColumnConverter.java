package com.tencent.component.db.converter;

import android.database.Cursor;
import com.tencent.component.utils.ParcelUtil;
import java.util.List;

public class ListColumnConverter implements ColumnConverter<List, byte[]> {
    public byte[] getColumnValue(Cursor cursor, int index) {
        return cursor.getBlob(index);
    }

    public List convertString2FiledValue(String fieldStringValue) {
        return null;
    }

    public List columnValue2FiledValue(byte[] columnValue, ClassLoader classLoader) {
        return ParcelUtil.readList(columnValue, classLoader);
    }

    public byte[] fieldValue2ColumnValue(List fieldValue) {
        return ParcelUtil.writeList(fieldValue);
    }

    public String getColumnDbType() {
        return "BLOB";
    }
}
