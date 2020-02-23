package com.tencent.component.db.converter;

import android.os.Parcelable;
import com.qq.taf.jce.JceStruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ColumnConverterFactory {
    private static ReadWriteLock mLock = new ReentrantReadWriteLock();
    private static final LinkedHashMap<Class, ColumnConverter> sColumnConverterMap = new LinkedHashMap<>();

    private ColumnConverterFactory() {
    }

    static {
        BooleanColumnConverter booleanColumnConverter = new BooleanColumnConverter();
        sColumnConverterMap.put(Boolean.TYPE, booleanColumnConverter);
        sColumnConverterMap.put(Boolean.class, booleanColumnConverter);
        sColumnConverterMap.put(byte[].class, new ByteArrayColumnConverter());
        ByteColumnConverter byteColumnConverter = new ByteColumnConverter();
        sColumnConverterMap.put(Byte.TYPE, byteColumnConverter);
        sColumnConverterMap.put(Byte.class, byteColumnConverter);
        CharColumnConverter charColumnConverter = new CharColumnConverter();
        sColumnConverterMap.put(Character.TYPE, charColumnConverter);
        sColumnConverterMap.put(Character.class, charColumnConverter);
        sColumnConverterMap.put(Date.class, new DateColumnConverter());
        DoubleColumnConverter doubleColumnConverter = new DoubleColumnConverter();
        sColumnConverterMap.put(Double.TYPE, doubleColumnConverter);
        sColumnConverterMap.put(Double.class, doubleColumnConverter);
        FloatColumnConverter floatColumnConverter = new FloatColumnConverter();
        sColumnConverterMap.put(Float.TYPE, floatColumnConverter);
        sColumnConverterMap.put(Float.class, floatColumnConverter);
        IntegerColumnConverter integerColumnConverter = new IntegerColumnConverter();
        sColumnConverterMap.put(Integer.TYPE, integerColumnConverter);
        sColumnConverterMap.put(Integer.class, integerColumnConverter);
        LongColumnConverter longColumnConverter = new LongColumnConverter();
        sColumnConverterMap.put(Long.TYPE, longColumnConverter);
        sColumnConverterMap.put(Long.class, longColumnConverter);
        ShortColumnConverter shortColumnConverter = new ShortColumnConverter();
        sColumnConverterMap.put(Short.TYPE, shortColumnConverter);
        sColumnConverterMap.put(Short.class, shortColumnConverter);
        sColumnConverterMap.put(java.sql.Date.class, new SqlDateColumnConverter());
        sColumnConverterMap.put(String.class, new StringColumnConverter());
        sColumnConverterMap.put(JceStruct.class, new JceColumnConverter());
        ListColumnConverter listColumnConverter = new ListColumnConverter();
        sColumnConverterMap.put(List.class, listColumnConverter);
        sColumnConverterMap.put(ArrayList.class, listColumnConverter);
        sColumnConverterMap.put(Parcelable.class, new ParcelColumnConverter());
        sColumnConverterMap.put(Serializable.class, new SerializableColumnConverter());
    }

    /* JADX INFO: finally extract failed */
    public static ColumnConverter getColumnConverter(Class columnType) {
        try {
            mLock.readLock().lock();
            if (sColumnConverterMap.containsKey(columnType)) {
                return sColumnConverterMap.get(columnType);
            }
            Set<Map.Entry<Class, ColumnConverter>> entries = sColumnConverterMap.entrySet();
            mLock.readLock().unlock();
            if (entries != null) {
                for (Map.Entry<Class, ColumnConverter> entry : entries) {
                    if (entry.getKey().isAssignableFrom(columnType)) {
                        try {
                            mLock.writeLock().lock();
                            sColumnConverterMap.put(columnType, entry.getValue());
                            mLock.writeLock().unlock();
                            return entry.getValue();
                        } catch (Throwable th) {
                            mLock.writeLock().unlock();
                            throw th;
                        }
                    }
                }
            }
            return null;
        } finally {
            mLock.readLock().unlock();
        }
    }

    public static String getDbColumnType(Class columnType) {
        ColumnConverter converter = getColumnConverter(columnType);
        if (converter != null) {
            return converter.getColumnDbType();
        }
        return "TEXT";
    }

    public static void registerColumnConverter(Class columnType, ColumnConverter columnConverter) {
        try {
            mLock.writeLock().lock();
            sColumnConverterMap.put(columnType, columnConverter);
        } finally {
            mLock.writeLock().unlock();
        }
    }

    public static boolean isSupportColumnConverter(Class columnType) {
        try {
            mLock.readLock().lock();
            if (sColumnConverterMap.containsKey(columnType)) {
                return true;
            }
            Set<Map.Entry<Class, ColumnConverter>> entries = sColumnConverterMap.entrySet();
            mLock.readLock().unlock();
            if (entries != null) {
                for (Map.Entry<Class, ColumnConverter> entry : entries) {
                    if (entry.getKey().isAssignableFrom(columnType)) {
                        try {
                            mLock.writeLock().lock();
                            sColumnConverterMap.put(columnType, entry.getValue());
                            return true;
                        } finally {
                            mLock.writeLock().unlock();
                        }
                    }
                }
            }
            return false;
        } finally {
            mLock.readLock().unlock();
        }
    }
}
