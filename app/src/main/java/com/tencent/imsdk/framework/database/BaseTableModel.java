package com.tencent.imsdk.framework.database;

import android.content.ContentValues;
import android.database.Cursor;
import com.tencent.imsdk.tool.etc.CommonUtil;

public abstract class BaseTableModel {
    public static int deleteAll(BaseDBHelper helper, String tableName) {
        int rows;
        synchronized (helper) {
            try {
                rows = helper.getWritableDatabase().delete(tableName, (String) null, (String[]) null);
                helper.close();
            } catch (Exception e) {
                e.printStackTrace();
                rows = 0;
                helper.close();
            } catch (Throwable th) {
                helper.close();
                throw th;
            }
        }
        return rows;
    }

    public static void putValues(ContentValues cv, String key, String value) {
        if (!CommonUtil.ckIsEmpty(value)) {
            cv.put(key, value);
        } else {
            cv.put(key, "");
        }
    }

    public static void putValues(ContentValues cv, String key, int value) {
        cv.put(key, Integer.valueOf(value));
    }

    public static String getStringByName(Cursor c, String columnName) {
        return c.getString(c.getColumnIndex(columnName));
    }

    protected static int getIntByName(Cursor c, String columnName) {
        return c.getInt(c.getColumnIndex(columnName));
    }

    protected static long getLongByName(Cursor c, String columnName) {
        return c.getLong(c.getColumnIndex(columnName));
    }
}
