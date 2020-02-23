package com.tencent.component.net.download.multiplex.download.extension;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.tencent.component.net.download.multiplex.FileDownload;

public class DBHelper {
    static DBHelper instance;
    SQLiteDatabase db;

    public static DBHelper getPublicInstance() {
        if (instance == null) {
            instance = new DBHelper();
        }
        if (instance.db == null) {
            instance.db = FileDownload.context.openOrCreateDatabase("download.db", 0, (SQLiteDatabase.CursorFactory) null);
        }
        return instance;
    }

    public SQLiteDatabase getSQLiteDatabase() {
        return this.db;
    }

    public boolean exist(String tableName) {
        Cursor cursor = this.db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", (String[]) null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public void execSQL(String cmd) {
        this.db.execSQL(cmd);
    }

    public int insert(String table, ContentValues cv) {
        return (int) this.db.insert(table, (String) null, cv);
    }

    public int update(String tableName, ContentValues cv, String where, String[] values) {
        return this.db.update(tableName, cv, where, values);
    }

    public int delete(String tableName, String where, String[] values) {
        return this.db.delete(tableName, where, values);
    }

    public Cursor query(String tableName, String selection) {
        return this.db.query(tableName, (String[]) null, selection, (String[]) null, (String) null, (String) null, (String) null);
    }

    public Cursor query(String tableName, String selection, String oderby) {
        return this.db.query(tableName, (String[]) null, selection, (String[]) null, (String) null, (String) null, oderby);
    }

    public Cursor query(String tableName, String[] columns, String selection, String[] selectionArgs, String orderBy) {
        return this.db.query(tableName, columns, selection, selectionArgs, (String) null, (String) null, orderBy);
    }

    public void closeDB() {
        if (this.db != null) {
            this.db.close();
        }
    }
}
