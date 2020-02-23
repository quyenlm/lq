package com.beetalk.sdk;

public class ShareConstants {

    interface ERROR_CODE {
        public static final int GG_RESULT_AUTH_ERROR = 179;
        public static final int GG_RESULT_AUTH_EXCHANGE_ERROR = 182;
        public static final int GG_RESULT_CANCELLED = 184;
        public static final int GG_RESULT_INSPECTION_ERROR = 185;
        public static final int GG_RESULT_NETWORK_ERROR = 178;
        public static final int GG_RESULT_NOT_SUPPORTED = 187;
        public static final int GG_RESULT_NO_AUTH_HANDLER = 181;
        public static final int GG_RESULT_NO_SESSION_INITIALISED = 188;
        public static final int GG_RESULT_REFRESH_FAIL_ERROR = 186;
        public static final int GG_RESULT_REFRESH_TOKEN_EMPTY = 183;
        public static final int GG_RESULT_TCP_FAILED = 189;
        public static final int GG_RESULT_UNKNOWN_ERROR = 180;
    }

    public interface ErrorInfo {
        public static final String GG_ERROR_USER_CANCEL = "user cancel the event";
    }

    public interface IntentFlag {
        public static final String GG_FIELD_APPLICATION_ID = "gg_application_id";
        public static final String GG_FIELD_APPLICATION_KEY = "gg_application_key";
        public static final String GG_FIELD_AUTH_ID = "gg_auth_id";
        public static final String GG_FIELD_ERROR_CODE = "gg_error_code";
        public static final String GG_FIELD_KEY_HASH = "gg_key_hash";
        public static final String GG_FIELD_REDIRECT_URL = "gg_app_redirect_url";
        public static final String GG_FIELD_REQUEST_CODE = "gg_request_code";
        public static final String GG_FIELD_SDK_ENV = "gg_sdk_env";
        public static final String GG_FIELD_SDK_VERSION = "gg_sdk_version";
        public static final String GG_FIELD_TOKEN_VALUE = "gg_token_value";
    }
}
