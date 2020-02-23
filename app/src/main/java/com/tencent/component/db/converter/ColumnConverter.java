package com.tencent.component.db.converter;

import android.database.Cursor;

public interface ColumnConverter<FieldType, ColumnType> {
    FieldType columnValue2FiledValue(ColumnType columntype, ClassLoader classLoader);

    FieldType convertString2FiledValue(String str);

    ColumnType fieldValue2ColumnValue(FieldType fieldtype);

    String getColumnDbType();

    ColumnType getColumnValue(Cursor cursor, int i);
}
