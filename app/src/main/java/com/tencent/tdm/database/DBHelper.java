package com.tencent.tdm.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.tencent.tdm.system.TXLog;

public class DBHelper extends SQLiteOpenHelper {
    private static final String KEY_Data = "Data";
    private static final String KEY_EventID = "EventId";
    private static final String KEY_Len = "Len";
    private static final String KEY_SrcID = "SrcId";
    private static final String PKEY_ID = "Id";
    private static final String TAG = "DBHelper";
    private String mTable = null;

    public DBHelper(Context context, String dbName, String table, int dbVersion) {
        super(context, dbName, (SQLiteDatabase.CursorFactory) null, dbVersion);
        this.mTable = table;
    }

    public void onCreate(SQLiteDatabase db) {
        String TABLESQL = "create table if not exists " + this.mTable + " (" + "Id" + " integer primary key," + KEY_EventID + " integer," + KEY_SrcID + " integer," + KEY_Len + " integer," + KEY_Data + " blob)";
        TXLog.d(TAG, "Create Table:" + TABLESQL);
        try {
            db.execSQL(TABLESQL);
        } catch (Exception e) {
            TXLog.e(TAG, "onCreate, create table Exception");
            TXLog.i(TAG, "Exception Track: " + e);
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        TXLog.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        try {
            db.execSQL("DROP TABLE IF EXISTS titles");
        } catch (Exception e) {
            TXLog.e(TAG, "onUpgrade, Upgrading Exception");
            TXLog.i(TAG, "Exception Track: " + e);
        }
        onCreate(db);
    }
}
