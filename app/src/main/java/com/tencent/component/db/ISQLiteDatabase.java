package com.tencent.component.db;

import android.content.ContentValues;
import android.database.Cursor;
import com.tencent.component.annotation.PluginApi;

@PluginApi(since = 8)
public interface ISQLiteDatabase {
    @PluginApi(since = 8)
    void beginTransaction();

    @PluginApi(since = 8)
    void close();

    @PluginApi(since = 8)
    int delete(String str, String str2, String[] strArr);

    @PluginApi(since = 8)
    void endTransaction();

    @PluginApi(since = 8)
    void execSQL(String str);

    @PluginApi(since = 8)
    void execSQL(String str, Object[] objArr);

    @PluginApi(since = 8)
    String getPath();

    @PluginApi(since = 8)
    int getVersion();

    @PluginApi(since = 8)
    boolean inTransaction();

    @PluginApi(since = 8)
    long insert(String str, String str2, ContentValues contentValues);

    @PluginApi(since = 8)
    long insertOrThrow(String str, String str2, ContentValues contentValues);

    @PluginApi(since = 8)
    long insertWithOnConflict(String str, String str2, ContentValues contentValues, int i);

    @PluginApi(since = 8)
    boolean isOpen();

    @PluginApi(since = 8)
    boolean isReadOnly();

    @PluginApi(since = 8)
    boolean needUpgrade(int i);

    @PluginApi(since = 8)
    Cursor query(String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5);

    @PluginApi(since = 8)
    Cursor query(String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6);

    @PluginApi(since = 8)
    Cursor query(boolean z, String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6);

    @PluginApi(since = 8)
    Cursor rawQuery(String str, String[] strArr);

    @PluginApi(since = 8)
    long replace(String str, String str2, ContentValues contentValues);

    @PluginApi(since = 8)
    long replaceOrThrow(String str, String str2, ContentValues contentValues);

    @PluginApi(since = 8)
    void setTransactionSuccessful();

    @PluginApi(since = 8)
    void setVersion(int i);

    @PluginApi(since = 8)
    int update(String str, ContentValues contentValues, String str2, String[] strArr);

    @PluginApi(since = 8)
    int updateWithOnConflict(String str, ContentValues contentValues, String str2, String[] strArr, int i);
}
