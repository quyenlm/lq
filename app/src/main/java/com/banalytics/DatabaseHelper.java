package com.banalytics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.amazonaws.services.s3.internal.Constants;
import com.banalytics.BAEvent;
import com.banalytics.BATrackerConst;
import com.btalk.helper.BBAppLogger;
import com.tencent.tp.a.h;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 4;
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE `ba_event` (`api_level` INTEGER , `app_key` VARCHAR , `app_version` INTEGER , `channel` VARCHAR , `cmd_type` VARCHAR , `country` VARCHAR , `description` VARCHAR , `device_id` VARCHAR , `device_info` VARCHAR , `event_id` INTEGER PRIMARY KEY AUTOINCREMENT , `manufacturer` VARCHAR , `referrer` VARCHAR , `status` INTEGER DEFAULT 0 , `time_stamp` INTEGER , `user_id` VARCHAR );";

    public DatabaseHelper(Context context) {
        super(context, BATrackerConst.DB_NAME, (SQLiteDatabase.CursorFactory) null, 4);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        new UpgradeHelper(db, oldVersion, newVersion).runAllUpgrades();
    }

    /* access modifiers changed from: protected */
    public void createNewEvent(String deviceId, String event, String remarks, String uid, String country, int appVersion) {
        BAEvent newEvent = new BAEvent();
        newEvent.setDeviceId(deviceId);
        newEvent.setCmdType(event);
        newEvent.setDescription(remarks);
        newEvent.setUserId(uid);
        newEvent.setStatus(0);
        newEvent.setTimestamp((int) (System.currentTimeMillis() / 1000));
        newEvent.setCountry(country);
        newEvent.setAppVersion(appVersion);
        BBAppLogger.i(newEvent.toJSON().toString(), new Object[0]);
        ContentValues values = new ContentValues();
        newEvent.dump(values);
        if (getWritableDatabase().insertOrThrow(BAEvent.EventEntry.TABLE_NAME, Constants.NULL_VERSION_ID, values) != -1) {
            BBAppLogger.i("event created", new Object[0]);
        } else {
            BBAppLogger.i("event creation failed", new Object[0]);
        }
    }

    /* access modifiers changed from: protected */
    public boolean createNewInstallEvent(String deviceId, String remarks, String channel, int apiLevel, String deviceInfo, int appVersion, String referrer, String manufacturer, String appKey) {
        BAEvent newEvent = new BAEvent();
        newEvent.setDeviceId(deviceId);
        newEvent.setCmdType(BATrackerConst.EVENT_TYPES.INSTALLATION);
        newEvent.setDescription(remarks);
        newEvent.setUserId(BATrackerConst.BA_DEFAULT.STR);
        newEvent.setStatus(0);
        newEvent.setTimestamp((int) (System.currentTimeMillis() / 1000));
        newEvent.setChannel(channel);
        newEvent.setApiLevel(apiLevel);
        newEvent.setDeviceInfo(deviceInfo);
        newEvent.setAppVersion(appVersion);
        newEvent.setReferrer(referrer);
        newEvent.setManufacturer(manufacturer);
        newEvent.setAppKey(appKey);
        newEvent.setCountry((String) null);
        BBAppLogger.i(newEvent.toJSON().toString(), new Object[0]);
        ContentValues values = new ContentValues();
        newEvent.dump(values);
        if (getWritableDatabase().insertOrThrow(BAEvent.EventEntry.TABLE_NAME, Constants.NULL_VERSION_ID, values) != -1) {
            BBAppLogger.i("event created", new Object[0]);
            return true;
        }
        BBAppLogger.i("event creation failed", new Object[0]);
        return false;
    }

    /* access modifiers changed from: protected */
    public long getPendingEventCount() {
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM ba_event WHERE status== " + String.valueOf(0) + " LIMIT " + String.valueOf(20), (String[]) null);
        int cnt = cursor.getCount();
        cursor.close();
        return (long) cnt;
    }

    /* access modifiers changed from: protected */
    public List<BAEvent> getPendingEvents() {
        Cursor c = getReadableDatabase().query(BAEvent.EventEntry.TABLE_NAME, new String[]{BAEvent.EventEntry.COLUMN_NAME_EVENT_ID, BAEvent.EventEntry.COLUMN_NAME_TIMESTAMP, "status", "description", BAEvent.EventEntry.COLUMN_NAME_CMD, "user_id", "device_id", "channel", "api_level", "device_info", "app_version", "referrer", "manufacturer", "app_key", "country"}, "status== ?", new String[]{String.valueOf(0)}, (String) null, (String) null, "time_stamp ASC", String.valueOf(20));
        ArrayList arrayList = new ArrayList();
        if (c.moveToFirst()) {
            do {
                int eventId = c.getInt(c.getColumnIndex(BAEvent.EventEntry.COLUMN_NAME_EVENT_ID));
                int timestamp = c.getInt(c.getColumnIndex(BAEvent.EventEntry.COLUMN_NAME_TIMESTAMP));
                int status = c.getInt(c.getColumnIndex("status"));
                String description = c.getString(c.getColumnIndex("description"));
                String cmdType = c.getString(c.getColumnIndex(BAEvent.EventEntry.COLUMN_NAME_CMD));
                String userId = c.getString(c.getColumnIndex("user_id"));
                String deviceId = c.getString(c.getColumnIndex("device_id"));
                String channel = c.getString(c.getColumnIndex("channel"));
                int apiLevel = c.getInt(c.getColumnIndex("api_level"));
                String deviceInfo = c.getString(c.getColumnIndex("device_info"));
                int appVersion = c.getInt(c.getColumnIndex("app_version"));
                String referrer = c.getString(c.getColumnIndex("referrer"));
                String manufacturer = c.getString(c.getColumnIndex("manufacturer"));
                String appKey = c.getString(c.getColumnIndex("app_key"));
                String country = c.getString(c.getColumnIndex("country"));
                BAEvent event = new BAEvent();
                event.setEventId(eventId);
                event.setTimestamp(timestamp);
                event.setStatus(status);
                event.setDescription(description);
                event.setCmdType(cmdType);
                event.setUserId(userId);
                event.setDeviceId(deviceId);
                event.setChannel(channel);
                event.setApiLevel(apiLevel);
                event.setDeviceInfo(deviceInfo);
                event.setAppVersion(appVersion);
                event.setReferrer(referrer);
                event.setManufacturer(manufacturer);
                event.setAppKey(appKey);
                event.setCountry(country);
                arrayList.add(event);
            } while (c.moveToNext());
        }
        c.close();
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public void removeSentEvents() {
        getWritableDatabase().delete(BAEvent.EventEntry.TABLE_NAME, "status== ?", new String[]{String.valueOf(1)});
    }

    /* access modifiers changed from: package-private */
    public void removeSentEvents(List<BAEvent> sentEvents) {
        SQLiteDatabase db = getWritableDatabase();
        StringBuilder builder = new StringBuilder(h.a);
        for (BAEvent event : sentEvents) {
            builder.append(event.getEventId());
            builder.append(',');
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(')');
        builder.append(';');
        db.execSQL("DELETE FROM ba_event WHERE event_id IN " + builder.toString());
    }
}
