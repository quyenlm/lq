package com.tencent.component.db;

public interface ColumnValueProcessor {
    <T> T processGet(T t, Class<?> cls, String str);

    <T> T processSet(T t, Class<?> cls, String str);
}
