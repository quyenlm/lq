package com.tencent.component.db.converter;

import android.database.Cursor;
import android.os.Parcelable;
import com.tencent.component.utils.ParcelUtil;

public class ParcelColumnConverter implements ColumnConverter<Parcelable, byte[]> {
    public Parcelable convertString2FiledValue(String fieldStringValue) {
        return null;
    }

    public byte[] fieldValue2ColumnValue(Parcelable fieldValue) {
        return ParcelUtil.writeParcelable(fieldValue);
    }

    public String getColumnDbType() {
        return "BLOB";
    }

    public byte[] getColumnValue(Cursor cursor, int index) {
        return cursor.getBlob(index);
    }

    public Parcelable columnValue2FiledValue(byte[] columnValue, ClassLoader classLoader) {
        return ParcelUtil.readParcelable(columnValue, classLoader);
    }
}
