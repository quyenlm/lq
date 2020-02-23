package com.tencent.imsdk.config;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConfigDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "config.db";
    public static final String DATABASE_TABLE_NAME = "config";
    private static final int DATABASE_VERSION = 1;

    public ConfigDBHelper(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS config(_id INTEGER PRIMARY KEY AUTOINCREMENT, key VARCHAR NOT NULL UNIQUE, value VARCHAR)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
