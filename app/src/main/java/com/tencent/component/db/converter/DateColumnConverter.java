package com.tencent.component.db.converter;

import android.database.Cursor;
import android.text.TextUtils;
import java.util.Date;

public class DateColumnConverter implements ColumnConverter<Date, Long> {
    public Date convertString2FiledValue(String fieldStringValue) {
        if (TextUtils.isEmpty(fieldStringValue)) {
            return null;
        }
        return new Date(Long.valueOf(fieldStringValue).longValue());
    }

    public Long fieldValue2ColumnValue(Date fieldValue) {
        if (fieldValue == null) {
            return null;
        }
        return Long.valueOf(fieldValue.getTime());
    }

    public String getColumnDbType() {
        return "INTEGER";
    }

    public Long getColumnValue(Cursor cursor, int index) {
        return Long.valueOf(cursor.getLong(index));
    }

    public Date columnValue2FiledValue(Long columnValue, ClassLoader classLoader) {
        return new Date(columnValue.longValue());
    }
}
