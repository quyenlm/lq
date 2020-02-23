package com.banalytics;

public class BATrackerConst {
    public static final String DB_NAME = "ba_events.db";
    public static final String DEFAULT_REFERRER = "default referrer";
    public static final String MANIFEST_CHANNEL_TAG = "com.beetalklib.ganalytics.channel";
    public static final String MANIFEST_META_KEY_TAG = "com.beetalklib.ganalytics.appkey";
    public static final String MANIFEST_PREINSTALL_TAG = "com.beetalklib.ganalytics.preinstall";
    public static final String MANIFEST_REPORT_URL = "com.beetalklib.ganalytics.report_url";
    public static final long MAX_NUM_EVENTS = 20;
    public static final int PENDING_INTENT_ID = 787;
    public static final long TRACKER_WAKE_UP_INTERVAL = 600000;
    public static String URL = null;

    public interface BA_DEFAULT {
        public static final int NUM = 0;
        public static final long NUM_LONG = 0;
        public static final String STR = "NA";
    }

    public interface CONTENT_VALUES_KEYS {
        public static final String KEY_KEY = "key";
        public static final String KEY_VALUE = "value";
    }

    public interface EVENT_TYPES {
        public static final String INSTALLATION = "installation";
        public static final String NOT_AVAILABLE = "na";
    }

    public interface INTENT_KEY {
        public static final String COMMAND_KEY = "command";
    }

    public interface JSON_KEYS {
        public static final String API_LEVEL = "api_level";
        public static final String APP_KEY = "app_key";
        public static final String APP_VERSION = "app_version";
        public static final String CHANNEL = "channel";
        public static final String CMD_TYPE = "cmd";
        public static final String COUNTRY = "country";
        public static final String DESCRIPTION = "description";
        public static final String DEVICE_ID = "device_id";
        public static final String DEVICE_INFO = "device_info";
        public static final String MANUFACTURER = "manufacturer";
        public static final String REFERRER = "referrer";
        public static final String SIGNATURE = "sign";
        public static final String TIME_STAMP = "timestamp";
        public static final String USER_ID = "userid";
        public static final String USER_ID_STR = "userIdStr";
    }

    public interface LOCAL_STORAGE {
        public static final String BA_LOCAL_STORE_V1 = "bee_analytics_local_storage_v1";
        public static final String DEVICE_ID_KEY = "device_id";
        public static final String IS_INSTALLATION_RECORDED = "has_installation_recorded";
        public static final String IS_INSTALLATION_SENT_KEY = "has_installation_sent";
    }

    public interface TRACKER_CMDS {
        public static final int NOT_AVAILABLE = 0;
        public static final int RECORD_EVENT = 2;
        public static final int RECORD_INSTALL = 4;
        public static final int SEND_EVENTS = 3;
        public static final int START_TRACKER = 1;
    }
}
