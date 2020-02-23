package com.tencent.imsdk.config;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import com.tencent.imsdk.tool.etc.IMLogger;
import com.tencent.imsdk.tool.etc.KVPair;
import java.util.ArrayList;
import java.util.List;

public class ConfigDBManager {
    private SQLiteDatabase db = this.helper.getWritableDatabase();
    private ConfigDBHelper helper;

    public ConfigDBManager(Context context) {
        this.helper = new ConfigDBHelper(context);
    }

    public long add(KVPair kv) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("key", kv.key);
            contentValues.put("value", kv.value);
            return this.db.insertOrThrow(ConfigDBHelper.DATABASE_TABLE_NAME, (String) null, contentValues);
        } catch (SQLiteConstraintException ex) {
            IMLogger.w(ex.getMessage());
            return -1;
        }
    }

    public void add(List<KVPair> kvList) {
        this.db.beginTransaction();
        try {
            for (KVPair kv : kvList) {
                this.db.execSQL("INSERT INTO config VALUES(null, ?, ?)", new Object[]{kv.key, kv.value});
            }
            this.db.setTransactionSuccessful();
        } catch (SQLiteException ex) {
            if (ex != null) {
                IMLogger.w(ex.getMessage());
            }
        } finally {
            this.db.endTransaction();
        }
    }

    public int update(KVPair kv) {
        ContentValues cv = new ContentValues();
        cv.put("value", kv.value);
        return this.db.update(ConfigDBHelper.DATABASE_TABLE_NAME, cv, "key = ?", new String[]{kv.key});
    }

    public void update(List<KVPair> kvList) {
        this.db.beginTransaction();
        try {
            for (KVPair kv : kvList) {
                update(kv);
            }
            this.db.setTransactionSuccessful();
        } catch (SQLiteException ex) {
            if (ex != null) {
                IMLogger.w(ex.getMessage());
            }
        } finally {
            this.db.endTransaction();
        }
    }

    public void addOrUpdate(List<KVPair> kvList) {
        this.db.beginTransaction();
        try {
            for (KVPair kv : kvList) {
                if (add(kv) == -1) {
                    update(kv);
                }
            }
            this.db.setTransactionSuccessful();
        } catch (SQLiteException ex) {
            if (ex != null) {
                IMLogger.w(ex.getMessage());
            }
        } finally {
            this.db.endTransaction();
        }
    }

    public int delete(KVPair kv) {
        return delete(kv.key);
    }

    public int delete(String key) {
        return this.db.delete(ConfigDBHelper.DATABASE_TABLE_NAME, "key = ?", new String[]{key});
    }

    public KVPair getByKey(KVPair kv) {
        return get("key", kv.key);
    }

    public KVPair getByValue(KVPair kv) {
        return get("value", kv.value);
    }

    public KVPair get(String keyItem, String keyValue) {
        KVPair kv = new KVPair();
        if (!TextUtils.isEmpty(keyItem) && !TextUtils.isEmpty(keyValue)) {
            Cursor cursor = this.db.rawQuery("SELECT * FROM config WHERE " + keyItem + " = ?", new String[]{keyValue});
            if (cursor.moveToFirst()) {
                kv.key = cursor.getString(cursor.getColumnIndex("key"));
                kv.value = cursor.getString(cursor.getColumnIndex("value"));
            }
            cursor.close();
        }
        return kv;
    }

    public List<KVPair> query() {
        ArrayList<KVPair> kvsList = new ArrayList<>();
        Cursor cursor = this.db.rawQuery("SELECT * FROM config", (String[]) null);
        while (cursor.moveToNext()) {
            KVPair kv = new KVPair();
            kv.key = cursor.getString(cursor.getColumnIndex("key"));
            kv.value = cursor.getString(cursor.getColumnIndex("value"));
            kvsList.add(kv);
        }
        cursor.close();
        return kvsList;
    }

    public void close() {
        this.db.close();
    }
}
