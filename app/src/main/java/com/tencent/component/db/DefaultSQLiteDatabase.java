package com.tencent.component.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DefaultSQLiteDatabase implements ISQLiteDatabase {
    private SQLiteDatabase mDatabase;

    public DefaultSQLiteDatabase(SQLiteDatabase db) {
        if (db == null) {
            throw new IllegalArgumentException("SQLiteDatabase cannot be null!");
        }
        this.mDatabase = db;
    }

    public void beginTransaction() {
        this.mDatabase.beginTransaction();
    }

    public void endTransaction() {
        this.mDatabase.endTransaction();
    }

    public void setTransactionSuccessful() {
        this.mDatabase.setTransactionSuccessful();
    }

    public boolean inTransaction() {
        return this.mDatabase.inTransaction();
    }

    public int getVersion() {
        return this.mDatabase.getVersion();
    }

    public void setVersion(int version) {
        this.mDatabase.setVersion(version);
    }

    public Cursor query(boolean distinct, String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return this.mDatabase.query(distinct, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return this.mDatabase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return this.mDatabase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
    }

    public Cursor rawQuery(String sql, String[] selectionArgs) {
        return this.mDatabase.rawQuery(sql, selectionArgs);
    }

    public long insert(String table, String nullColumnHack, ContentValues values) {
        return this.mDatabase.insert(table, nullColumnHack, values);
    }

    public long insertOrThrow(String table, String nullColumnHack, ContentValues values) {
        return this.mDatabase.insertOrThrow(table, nullColumnHack, values);
    }

    public long replace(String table, String nullColumnHack, ContentValues initialValues) {
        return this.mDatabase.replace(table, nullColumnHack, initialValues);
    }

    public long replaceOrThrow(String table, String nullColumnHack, ContentValues initialValues) {
        return this.mDatabase.replaceOrThrow(table, nullColumnHack, initialValues);
    }

    public long insertWithOnConflict(String table, String nullColumnHack, ContentValues initialValues, int conflictAlgorithm) {
        return this.mDatabase.insertWithOnConflict(table, nullColumnHack, initialValues, conflictAlgorithm);
    }

    public int delete(String table, String whereClause, String[] whereArgs) {
        return this.mDatabase.delete(table, whereClause, whereArgs);
    }

    public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        return this.mDatabase.update(table, values, whereClause, whereArgs);
    }

    public int updateWithOnConflict(String table, ContentValues values, String whereClause, String[] whereArgs, int conflictAlgorithm) {
        return this.mDatabase.updateWithOnConflict(table, values, whereClause, whereArgs, conflictAlgorithm);
    }

    public void execSQL(String sql) {
        this.mDatabase.execSQL(sql);
    }

    public void execSQL(String sql, Object[] bindArgs) {
        this.mDatabase.execSQL(sql, bindArgs);
    }

    public boolean isReadOnly() {
        return this.mDatabase.isReadOnly();
    }

    public boolean isOpen() {
        return this.mDatabase.isOpen();
    }

    public boolean needUpgrade(int newVersion) {
        return this.mDatabase.needUpgrade(newVersion);
    }

    public String getPath() {
        return this.mDatabase.getPath();
    }

    public void close() {
        this.mDatabase.close();
    }
}
