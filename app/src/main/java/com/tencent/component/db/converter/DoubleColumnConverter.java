package com.tencent.component.db.converter;

import android.database.Cursor;
import android.text.TextUtils;

public class DoubleColumnConverter implements ColumnConverter<Double, Double> {
    public Double convertString2FiledValue(String fieldStringValue) {
        if (TextUtils.isEmpty(fieldStringValue)) {
            return null;
        }
        return Double.valueOf(fieldStringValue);
    }

    public Double fieldValue2ColumnValue(Double fieldValue) {
        return fieldValue;
    }

    public String getColumnDbType() {
        return "REAL";
    }

    public Double getColumnValue(Cursor cursor, int index) {
        return Double.valueOf(cursor.getDouble(index));
    }

    public Double columnValue2FiledValue(Double columnValue, ClassLoader classLoader) {
        return columnValue;
    }
}
