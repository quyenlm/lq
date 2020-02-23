package com.tencent.component.db;

import android.database.Cursor;
import android.text.TextUtils;
import com.tencent.component.db.EntityManager;
import com.tencent.component.db.entity.TableEntity;
import com.tencent.component.utils.IOUtils;
import com.tencent.component.utils.log.LogUtil;
import com.tencent.tp.a.h;

public class DefaultUpdateListener implements EntityManager.UpdateListener {
    private static final String TAG = "DefaultUpdateListener";

    public void onDatabaseUpgrade(ISQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtil.i(TAG, "onDatabaseUpgrade(" + oldVersion + " --> " + newVersion + h.b);
        dropDatabase(db);
    }

    public void onDatabaseDowngrade(ISQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtil.i(TAG, "onDatabaseDowngrade(" + oldVersion + " --> " + newVersion + h.b);
        dropDatabase(db);
    }

    public void onTableUpgrade(ISQLiteDatabase db, String tableName, int oldVersion, int newVersion) {
        LogUtil.i(TAG, "onTableUpgrade(" + oldVersion + " --> " + newVersion + ",tableName:" + tableName + h.b);
        dropTable(db, tableName);
    }

    public void onTableDowngrade(ISQLiteDatabase db, String tableName, int oldVersion, int newVersion) {
        LogUtil.i(TAG, "onTableDowngrade(" + oldVersion + " --> " + newVersion + ",tableName:" + tableName + h.b);
        dropTable(db, tableName);
    }

    private static void dropTable(ISQLiteDatabase db, String tableName) {
        if (db != null && !TextUtils.isEmpty(tableName)) {
            db.execSQL("DROP TABLE IF EXISTS " + tableName);
        }
    }

    private static void dropDatabase(ISQLiteDatabase db) {
        if (db != null) {
            Cursor cursor = null;
            try {
                cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type ='table'", (String[]) null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String tableName = cursor.getString(0);
                        db.execSQL("DROP TABLE IF EXISTS " + tableName);
                        TableEntity.remove(tableName);
                    }
                }
                IOUtils.closeQuietly(cursor);
            } catch (Throwable th) {
                IOUtils.closeQuietly(cursor);
                throw th;
            }
        }
    }
}
