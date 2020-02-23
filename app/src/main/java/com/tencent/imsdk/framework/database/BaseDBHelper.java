package com.tencent.imsdk.framework.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class BaseDBHelper extends SQLiteOpenHelper {
    public BaseDBHelper(Context ctx, String name, int version) {
        super(ctx, name, (SQLiteDatabase.CursorFactory) null, version);
    }

    public long insert(String table, String nullColumnHack, ContentValues values) {
        SQLiteDatabase db = getWritableDatabase();
        long rowid = db.insert(table, nullColumnHack, values);
        db.close();
        return rowid;
    }

    public int delete(String table, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.delete(table, whereClause, whereArgs);
        db.close();
        return rows;
    }

    public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = getWritableDatabase();
        int rows = db.update(table, values, whereClause, whereArgs);
        db.close();
        return rows;
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return getReadableDatabase().query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }
}
