package com.amazonaws.mobileconnectors.s3.transferutility;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class TransferDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "awss3transfertable.db";
    private static final int DATABASE_VERSION = 4;
    private int version;

    public TransferDatabaseHelper(Context context) {
        this(context, 4);
    }

    public TransferDatabaseHelper(Context context, int version2) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, version2);
        this.version = version2;
    }

    public void onCreate(SQLiteDatabase database) {
        TransferTable.onCreate(database, this.version);
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        TransferTable.onUpgrade(database, oldVersion, newVersion);
    }
}
