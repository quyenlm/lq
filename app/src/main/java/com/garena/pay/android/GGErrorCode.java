package com.garena.pay.android;

import MTT.EFvrECode;
import com.google.android.gms.cast.CastStatusCodes;
import com.google.android.gms.games.GamesActivityResultCodes;
import com.google.android.gms.games.GamesStatusCodes;
import com.tencent.midas.oversea.comm.APGlobalInfo;

public enum GGErrorCode {
    SUCCESS(0, "No Error"),
    APP_NOT_INSTALLED(2000, "Application is not installed"),
    UNSUPPORTED_API(2001, "Unsupported API used"),
    USER_CANCELLED(2002, "User cancelled the request"),
    USER_DENIED_REQUEST(2003, "User denied the request"),
    LOGIN_FAILED(2004, "Login failed"),
    REFRESH_TOKEN_SUCCESS(2005, "Token refreshed successfully"),
    REFRESH_TOKEN_FAILED(2006, "Token refreshed failed"),
    ACCESS_TOKEN_EXPIRED(2007, "Access token expired"),
    NETWORK_EXCEPTION(2008, "Network error."),
    ACCESS_TOKEN_EXCHANGE_FAILED(2009, "Access token exchanged failed"),
    NETWORK_CONNECTION_EXCEPTION(2010, "Server cannot be reached. Check your network connection"),
    CANNOT_GET_RESULT(2011, "Unable to retrieve result"),
    ERROR_IN_PARAMS(2012, "Error in parameters"),
    CANNOT_START_ACTIVITY(Integer.valueOf(EFvrECode._ERR_FVR_NONSUPPORT), "Cannot start activity"),
    REQUEST_ID_MISMATCH(Integer.valueOf(EFvrECode._ERR_FVR_IMGCVT_EXCEPTION), "Request ID mismatch"),
    ACCESS_TOKEN_INSPECTION_FAILED(Integer.valueOf(EFvrECode._ERR_FVR_IMGCVT_ERR), "Token inspection failed"),
    SESSION_NOT_INITIALIZED(Integer.valueOf(EFvrECode._ERR_FVR_UNIID_EXCEPTION), "Session not initialized"),
    BATCH_SIZE_EXCEEDED(2102, "Batch size exceeded for the request"),
    BIND_FAILED(Integer.valueOf(CastStatusCodes.ERROR_SERVICE_DISCONNECTED), "Bind failed but login success"),
    GUEST_ACCOUNT_INVALID(2202, "Fail to grant guest token"),
    REDEEM_NOT_AVAILABLE(2304, "Redeem not available"),
    ALREADY_REDEEMED(2305, "Already redeemed"),
    REDEEM_LIMIT_REACHED(2306, "Redeem limit reached"),
    NETWORK_RESPONSE_PARSE_FAIL(2308, "Network response parse failed"),
    NETWORK_REQUEST_TIME_OUT(2309, "Network request timeout"),
    GOP_ERROR_SERVER(2300, "Server error"),
    GOP_ERROR_TOKEN(2302, "Invalid token"),
    GOP_ERROR_SCOPE(2303, "Invalid scope"),
    ERROR(2999, "General error"),
    PAYMENT_NO_ERROR(Integer.valueOf(GamesStatusCodes.STATUS_REAL_TIME_CONNECTION_FAILED), "No payment error"),
    PAYMENT_CANNOT_START_ACTIVITY(Integer.valueOf(GamesStatusCodes.STATUS_REAL_TIME_MESSAGE_SEND_FAILED), "Cannot start payment activity"),
    PAYMENT_REQUEST_ID_MISMATCH(Integer.valueOf(GamesStatusCodes.STATUS_INVALID_REAL_TIME_ROOM_ID), "Request ID mismatch"),
    PAYMENT_USER_CANCELLED(Integer.valueOf(GamesStatusCodes.STATUS_PARTICIPANT_NOT_CONNECTED), "User cancelled payment"),
    PAYMENT_INVALID_SERVER_RESPONSE(Integer.valueOf(GamesStatusCodes.STATUS_REAL_TIME_ROOM_NOT_JOINED), "Payment recd. Invalid response from server"),
    PAYMENT_NETWORK_CONNECTION_EXCEPTION(Integer.valueOf(GamesStatusCodes.STATUS_REAL_TIME_INACTIVE_ROOM), "Network connection exception with the server"),
    PAYMENT_BUNDLE_RESULT_INVALID(7006, "Bundle result cannot be read, did you call onActivityResult?"),
    PAYMENT_ERROR_IN_PARAMS(Integer.valueOf(GamesStatusCodes.STATUS_OPERATION_IN_FLIGHT), "Invalid parameters"),
    PAYMENT_ERROR_IN_SCOPE(7008, "Scope does not authorize payment"),
    PAYMENT_ERROR_IN_TOKEN(7009, "Error in access token used for payment"),
    PAYMENT_ERROR_SUBSCRIPTION_BINDING(7010, "Error when server binding subscription"),
    PAYMENT_GENERAL_ERROR(7999, "General payment error"),
    UNKNOWN_ERROR(-1, "Unknown error occurred"),
    ACCESS_TOKEN_INVALID_GRANT(Integer.valueOf(EFvrECode._ERR_FVR_URLINFO_EXCEPTION), "Access token invalid grant"),
    ERROR_USER_BANNED(Integer.valueOf(EFvrECode._ERR_FVR_BOOKMARK_EXCEPTION), "User banned"),
    ERROR_GUEST_LOGIN(Integer.valueOf(EFvrECode._ERR_FVR_BOOKMARK_ERR), "Error guest login"),
    GGLIVE_ERROR_PARAM(10001, "GGLive error param"),
    GGLIVE_INVALID_SESSION(10002, "GGLive invalid session"),
    GGLIVE_SESSION_EXPIRED(10003, "GGLive session expired"),
    GGLIVE_SERVER_ERROR(10004, "GGLive server error"),
    GGLIVE_FORBIDDEN(10005, "GGLive forbidden"),
    GGLIVE_QUOTA_LIMIT(10006, "GGLive quota limit"),
    GGLIVE_CHANNEL_NOT_EXIST(Integer.valueOf(GamesActivityResultCodes.RESULT_SEND_REQUEST_FAILED), "GGLive channel not exist"),
    GGLIVE_CHANNEL_EXIST(10008, "GGLive channel exist"),
    GGLIVE_STREAMING(Integer.valueOf(APGlobalInfo.RET_PAYSESSIONVALID), "GGLive is already streaming"),
    GGLIVE_NOT_STREAMING(10010, "GGLive not streaming"),
    GGLIVE_CHANNEL_NAME_RESERVED(10011, "GGLive channel name reserved"),
    GGLIVE_INVALID_USER_PLATFORM(10012, "GGLive invalid user platform"),
    GGLIVE_ACCESS_RESTRICTION(10013, "GGLive access restriction"),
    GGLIVE_SERVER_NOT_EXIST(10014, "GGLive server not exist"),
    GGLIVE_BAN(10015, "GGLive ban"),
    GGLIVE_INVALID_STREAM_KEY(10016, "GGLive stream key invalid"),
    GGLIVE_STREAM_KEY_EXPIRED(10017, "GGLive stream key expired"),
    GGLIVE_NO_MOBILE_BINDING(10018, "GGLive no mobile binding"),
    DECODE_IMAGE_FAILED(20000, "Failed to decode image");
    
    private final Integer code;
    private final String value;

    private GGErrorCode(Integer code2, String value2) {
        this.value = value2;
        this.code = code2;
    }

    public String getStringValue() {
        return this.value;
    }

    public Integer getCode() {
        return this.code;
    }

    public static String getErrorStringFromCode(int value2) {
        for (GGErrorCode errorCode : values()) {
            if (value2 == errorCode.getCode().intValue()) {
                return errorCode.getStringValue();
            }
        }
        return UNKNOWN_ERROR.getStringValue();
    }

    public static GGErrorCode getErrorFromCode(int value2) {
        for (GGErrorCode errorCode : values()) {
            if (value2 == errorCode.getCode().intValue()) {
                return errorCode;
            }
        }
        return UNKNOWN_ERROR;
    }

    public static boolean isFatal(int value2) {
        return value2 == ACCESS_TOKEN_EXCHANGE_FAILED.getCode().intValue() || value2 == ACCESS_TOKEN_EXPIRED.getCode().intValue() || value2 == REFRESH_TOKEN_FAILED.getCode().intValue();
    }
}
