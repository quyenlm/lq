package com.banalytics;

import android.content.ContentValues;
import android.provider.BaseColumns;
import com.banalytics.BATrackerConst;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class BAEvent {
    public static final String STATE_PENDING = "0";
    public static final String STATE_SENT = "1";
    public static final int VALUE_PENDING = 0;
    public static final int VALUE_SENT = 1;
    private int apiLevel;
    private String appKey;
    private int appVersion;
    private String channel;
    protected String cmdType;
    protected String country;
    protected String description;
    private String deviceId;
    private String deviceInfo;
    private int eventId;
    private String manufacturer;
    private String referrer;
    protected int status;
    private int timestamp;
    protected String userId;

    public static abstract class EventEntry implements BaseColumns {
        public static final String COLUMN_NAME_API_LEVEL = "api_level";
        public static final String COLUMN_NAME_APP_KEY = "app_key";
        public static final String COLUMN_NAME_APP_VERSION = "app_version";
        public static final String COLUMN_NAME_CHANNEL = "channel";
        public static final String COLUMN_NAME_CMD = "cmd_type";
        public static final String COLUMN_NAME_COUNTRY = "country";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_DEVICE_ID = "device_id";
        public static final String COLUMN_NAME_DEVICE_INFO = "device_info";
        public static final String COLUMN_NAME_EVENT_ID = "event_id";
        public static final String COLUMN_NAME_MANUFACTURER = "manufacturer";
        public static final String COLUMN_NAME_REFERRER = "referrer";
        public static final String COLUMN_NAME_STATUS = "status";
        public static final String COLUMN_NAME_TIMESTAMP = "time_stamp";
        public static final String COLUMN_NAME_UID = "user_id";
        public static final String TABLE_NAME = "ba_event";
    }

    public int getEventId() {
        return this.eventId;
    }

    public void setEventId(int eventId2) {
        this.eventId = eventId2;
    }

    public void setTimestamp(int timestamp2) {
        this.timestamp = timestamp2;
    }

    public void setStatus(int status2) {
        this.status = status2;
    }

    public void setDescription(String description2) {
        this.description = description2;
    }

    public void setCmdType(String cmdType2) {
        this.cmdType = cmdType2;
    }

    public void setUserId(String userId2) {
        this.userId = userId2;
    }

    public void setDeviceId(String deviceId2) {
        this.deviceId = deviceId2;
    }

    public void setChannel(String channel2) {
        this.channel = channel2;
    }

    public void setApiLevel(int apiLevel2) {
        this.apiLevel = apiLevel2;
    }

    public void setDeviceInfo(String deviceInfo2) {
        this.deviceInfo = deviceInfo2;
    }

    public void setAppVersion(int appVersion2) {
        this.appVersion = appVersion2;
    }

    public void setReferrer(String referrer2) {
        this.referrer = referrer2;
    }

    public void setManufacturer(String manufacturer2) {
        this.manufacturer = manufacturer2;
    }

    public void setAppKey(String appKey2) {
        this.appKey = appKey2;
    }

    public void setCountry(String country2) {
        this.country = country2;
    }

    public JSONObject toJSON() {
        JSONObject eventJSON = new JSONObject();
        try {
            eventJSON.put("device_id", this.deviceId);
            eventJSON.put(BATrackerConst.JSON_KEYS.CMD_TYPE, this.cmdType);
            eventJSON.put("description", this.description);
            eventJSON.put("timestamp", Integer.toString(this.timestamp));
            eventJSON.put(BATrackerConst.JSON_KEYS.USER_ID, this.userId);
            eventJSON.put(BATrackerConst.JSON_KEYS.SIGNATURE, generateHash());
            if (BATrackerConst.EVENT_TYPES.INSTALLATION.equals(this.cmdType)) {
                eventJSON.put("channel", this.channel);
                eventJSON.put("api_level", Integer.toString(this.apiLevel));
                eventJSON.put("device_info", this.deviceInfo);
                eventJSON.put("app_version", Integer.toString(this.appVersion));
                eventJSON.put("referrer", this.referrer);
                eventJSON.put("manufacturer", this.manufacturer);
                eventJSON.put("app_key", this.appKey);
                return eventJSON;
            }
            eventJSON.put("country", this.country);
            eventJSON.put("app_version", Integer.toString(this.appVersion));
            return eventJSON;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void dump(ContentValues values) {
        values.put(EventEntry.COLUMN_NAME_TIMESTAMP, Integer.valueOf(this.timestamp));
        values.put("status", Integer.valueOf(this.status));
        values.put("description", this.description);
        values.put(EventEntry.COLUMN_NAME_CMD, this.cmdType);
        values.put("user_id", this.userId);
        values.put("device_id", this.deviceId);
        values.put("channel", this.channel);
        values.put("api_level", Integer.valueOf(this.apiLevel));
        values.put("device_info", this.deviceInfo);
        values.put("app_version", Integer.valueOf(this.appVersion));
        values.put("referrer", this.referrer);
        values.put("manufacturer", this.manufacturer);
        values.put("app_key", this.appKey);
        values.put("country", this.country);
    }

    private ContentValues craeteContentValues(String key, String value) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("key", key);
        contentValues.put("value", value);
        return contentValues;
    }

    private String generateHash() {
        List<ContentValues> values = new ArrayList<>();
        values.add(craeteContentValues("device_id", this.deviceId));
        values.add(craeteContentValues(BATrackerConst.JSON_KEYS.CMD_TYPE, this.cmdType));
        values.add(craeteContentValues("timestamp", Integer.toString(this.timestamp)));
        values.add(craeteContentValues(BATrackerConst.JSON_KEYS.USER_ID, this.userId));
        if (BATrackerConst.EVENT_TYPES.INSTALLATION.equals(this.cmdType)) {
            values.add(craeteContentValues("channel", this.channel));
            values.add(craeteContentValues("api_level", Integer.toString(this.apiLevel)));
            values.add(craeteContentValues("device_info", this.deviceInfo));
            values.add(craeteContentValues("app_version", Integer.toString(this.appVersion)));
            values.add(craeteContentValues("referrer", this.referrer));
            values.add(craeteContentValues("manufacturer", this.manufacturer));
            values.add(craeteContentValues("app_key", this.appKey));
        } else {
            values.add(craeteContentValues("country", this.country));
            values.add(craeteContentValues("app_version", Integer.toString(this.appVersion)));
        }
        Collections.sort(values, new Comparator<ContentValues>() {
            public int compare(ContentValues lhs, ContentValues rhs) {
                String leftKey = lhs.getAsString("key");
                String rightKey = rhs.getAsString("key");
                if (leftKey == null) {
                    return -1;
                }
                if (rightKey == null) {
                    return 1;
                }
                return leftKey.compareTo(rightKey);
            }
        });
        StringBuilder builder = new StringBuilder();
        for (ContentValues valuePair : values) {
            String value = valuePair.getAsString("value");
            if (value != null) {
                builder.append(value);
                builder.append(":");
            }
        }
        builder.append("frveiucrwu]i245bgr");
        return BASecurity.sha256(builder.toString().getBytes());
    }
}
