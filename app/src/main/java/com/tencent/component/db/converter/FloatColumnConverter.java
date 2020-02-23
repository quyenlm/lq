package com.tencent.component.db.converter;

import android.database.Cursor;
import android.text.TextUtils;

public class FloatColumnConverter implements ColumnConverter<Float, Float> {
    public Float convertString2FiledValue(String fieldStringValue) {
        if (TextUtils.isEmpty(fieldStringValue)) {
            return null;
        }
        return Float.valueOf(fieldStringValue);
    }

    public Float fieldValue2ColumnValue(Float fieldValue) {
        return fieldValue;
    }

    public String getColumnDbType() {
        return "REAL";
    }

    public Float getColumnValue(Cursor cursor, int index) {
        return Float.valueOf(cursor.getFloat(index));
    }

    public Float columnValue2FiledValue(Float columnValue, ClassLoader classLoader) {
        return columnValue;
    }
}
