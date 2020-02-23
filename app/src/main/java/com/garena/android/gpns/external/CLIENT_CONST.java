package com.garena.android.gpns.external;

public interface CLIENT_CONST {
    public static final String DATA_KEY = "DATA";
    public static final String MSDK_PUSH_SERVICE_VERSION_KEY = "msdk.pushservice.version";

    public interface PROTOCOL {
        public static final int CONNECT_ACK = 2;
        public static final String KEY_IS_RUNNING = "key_is_running";
        public static final String KEY_REGISTRATION_ID = "REGISTRATION_ID";
        public static final String KEY_SERVICE_VERSION = "key_service_version";
        public static final int REPLY_SERVICE_INFO = 1;
        public static final int REQUEST_SERVICE_INFO = 0;
        public static final int SELF_DESTRUCT_ACK = 3;
    }
}
