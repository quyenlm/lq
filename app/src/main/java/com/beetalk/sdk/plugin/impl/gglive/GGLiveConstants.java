package com.beetalk.sdk.plugin.impl.gglive;

import android.content.Context;
import android.text.TextUtils;
import com.garena.android.gpns.utility.DeviceUtil;
import com.garena.pay.android.GGErrorCode;

public class GGLiveConstants {
    public static final String DEVICE_ID_PREFIX = "MSDK_";
    public static final int STREAMING_CLIENT_TYPE = 4355;

    public interface ERROR_CODE {
        public static final String ERROR_ACCESS_RESTRICTION = "error_access_restriction";
        public static final String ERROR_BAN = "error_ban";
        public static final String ERROR_CHANNEL_EXIST = "error_channel_exist";
        public static final String ERROR_CHANNEL_NOT_EXIST = "error_channel_not_exist";
        public static final String ERROR_FORBIDDEN = "error_forbidden";
        public static final String ERROR_INVALID_SESSION = "error_invalid_session";
        public static final String ERROR_INVALID_USER_PLATFORM = "error_invalid_user_platform";
        public static final String ERROR_NAME_RESERVED = "error_name_reserved";
        public static final String ERROR_NETWORK_EXCEPTION = "error_network_exception";
        public static final String ERROR_NOT_STREAMING = "error_not_streaming";
        public static final String ERROR_NO_MOBILE_BINDING = "error_no_mobile_binding";
        public static final String ERROR_PARAM = "error_params";
        public static final String ERROR_QUOTA_LIMIT = "error_quota_limit";
        public static final String ERROR_SERVER = "error_server";
        public static final String ERROR_SERVER_NOT_EXIST = "error_server_not_exist";
        public static final String ERROR_SESSION_EXPIRED = "error_session_expired";
        public static final String ERROR_STREAMING = "error_streaming";
        public static final String ERROR_TOKEN_EXPIRED = "error_token_expired";
        public static final String ERROR_TOKEN_INVALID = "error_token_invalid";
        public static final String ERROR_UNKNOWN = "error_unknown";
        public static final String SUCCESS = "success";
    }

    public interface EXPIRY {
        public static final int SESSION_KEY = 3000;
    }

    public interface PARAM {
        public static final String ACCOUNT_INFO = "account_info";
        public static final String CATEGORY_ID = "category_id";
        public static final String CHANNEL_DESCRIPTION = "description";
        public static final String CHANNEL_NAME = "name";
        public static final String CLIENT_TYPE = "client_type";
        public static final String EXPIRY_TIME = "expiry_time";
        public static final String MACHINE_ID = "machine_id";
        public static final String NUMBER_VIEWER = "number_viewer";
        public static final String REGION = "region";
        public static final String REPLY = "reply";
        public static final String RESULT = "result";
        public static final String SESSION_TOKEN = "session_token";
        public static final String STATUS = "status";
        public static final String STREAM_KEY = "stream_key";
        public static final String STREAM_SERVER_ADDRESS = "stream_server_address";
        public static final String TOKEN = "token";
        public static final String TOKEN_ID = "token_id";
        public static final String UID = "uid";
    }

    public interface STREAM_STATUS {
        public static final String BANNED = "STREAM_STATUS_BANNED";
        public static final String DISCONNECTED = "STREAM_STATUS_DISCONNECTED";
        public static final String EXPIRED = "STREAM_STATUS_EXPIRED";
        public static final String FINISHED = "STREAM_STATUS_FINISHED";
        public static final String INIT = "STREAM_STATUS_INIT";
        public static final String KICKED = "STREAM_STATUS_KICKED";
        public static final String STREAMING = "STREAM_STATUS_STREAMING";
        public static final String UNUPDATED = "STREAM_STATUS_UNUPDATED";
    }

    public static final String generateDeviceId(Context context) {
        return DEVICE_ID_PREFIX + String.valueOf(DeviceUtil.generateDeviceId(context));
    }

    public static int getErrorCode(String error) {
        if (TextUtils.isEmpty(error)) {
            return GGErrorCode.UNKNOWN_ERROR.getCode().intValue();
        }
        if ("success".equalsIgnoreCase(error)) {
            return GGErrorCode.SUCCESS.getCode().intValue();
        }
        if ("error_params".equalsIgnoreCase(error)) {
            return GGErrorCode.GGLIVE_ERROR_PARAM.getCode().intValue();
        }
        if (ERROR_CODE.ERROR_INVALID_SESSION.equalsIgnoreCase(error) || ERROR_CODE.ERROR_TOKEN_INVALID.equalsIgnoreCase(error)) {
            return GGErrorCode.GGLIVE_INVALID_SESSION.getCode().intValue();
        }
        if (ERROR_CODE.ERROR_SESSION_EXPIRED.equalsIgnoreCase(error) || ERROR_CODE.ERROR_TOKEN_EXPIRED.equalsIgnoreCase(error)) {
            return GGErrorCode.GGLIVE_SESSION_EXPIRED.getCode().intValue();
        }
        if (ERROR_CODE.ERROR_SERVER.equalsIgnoreCase(error)) {
            return GGErrorCode.GGLIVE_SERVER_ERROR.getCode().intValue();
        }
        if (ERROR_CODE.ERROR_FORBIDDEN.equalsIgnoreCase(error)) {
            return GGErrorCode.GGLIVE_FORBIDDEN.getCode().intValue();
        }
        if (ERROR_CODE.ERROR_QUOTA_LIMIT.equalsIgnoreCase(error)) {
            return GGErrorCode.GGLIVE_QUOTA_LIMIT.getCode().intValue();
        }
        if (ERROR_CODE.ERROR_CHANNEL_NOT_EXIST.equalsIgnoreCase(error)) {
            return GGErrorCode.GGLIVE_CHANNEL_NOT_EXIST.getCode().intValue();
        }
        if (ERROR_CODE.ERROR_CHANNEL_EXIST.equalsIgnoreCase(error)) {
            return GGErrorCode.GGLIVE_CHANNEL_EXIST.getCode().intValue();
        }
        if (ERROR_CODE.ERROR_STREAMING.equalsIgnoreCase(error)) {
            return GGErrorCode.GGLIVE_STREAMING.getCode().intValue();
        }
        if (ERROR_CODE.ERROR_NOT_STREAMING.equalsIgnoreCase(error)) {
            return GGErrorCode.GGLIVE_NOT_STREAMING.getCode().intValue();
        }
        if (ERROR_CODE.ERROR_NAME_RESERVED.equalsIgnoreCase(error)) {
            return GGErrorCode.GGLIVE_CHANNEL_NAME_RESERVED.getCode().intValue();
        }
        if (ERROR_CODE.ERROR_INVALID_USER_PLATFORM.equalsIgnoreCase(error)) {
            return GGErrorCode.GGLIVE_INVALID_USER_PLATFORM.getCode().intValue();
        }
        if (ERROR_CODE.ERROR_ACCESS_RESTRICTION.equalsIgnoreCase(error)) {
            return GGErrorCode.GGLIVE_ACCESS_RESTRICTION.getCode().intValue();
        }
        if (ERROR_CODE.ERROR_SERVER_NOT_EXIST.equalsIgnoreCase(error)) {
            return GGErrorCode.GGLIVE_SERVER_NOT_EXIST.getCode().intValue();
        }
        if (ERROR_CODE.ERROR_BAN.equalsIgnoreCase(error)) {
            return GGErrorCode.GGLIVE_BAN.getCode().intValue();
        }
        if (ERROR_CODE.ERROR_NO_MOBILE_BINDING.equalsIgnoreCase(error)) {
            return GGErrorCode.GGLIVE_NO_MOBILE_BINDING.getCode().intValue();
        }
        if (ERROR_CODE.ERROR_NETWORK_EXCEPTION.equals(error)) {
            return GGErrorCode.NETWORK_EXCEPTION.getCode().intValue();
        }
        return GGErrorCode.UNKNOWN_ERROR.getCode().intValue();
    }
}
