package com.facebook.login;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginClient;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

class LoginLogger {
    static final String EVENT_EXTRAS_DEFAULT_AUDIENCE = "default_audience";
    static final String EVENT_EXTRAS_FACEBOOK_VERSION = "facebookVersion";
    static final String EVENT_EXTRAS_FAILURE = "failure";
    static final String EVENT_EXTRAS_IS_REAUTHORIZE = "isReauthorize";
    static final String EVENT_EXTRAS_LOGIN_BEHAVIOR = "login_behavior";
    static final String EVENT_EXTRAS_MISSING_INTERNET_PERMISSION = "no_internet_permission";
    static final String EVENT_EXTRAS_NEW_PERMISSIONS = "new_permissions";
    static final String EVENT_EXTRAS_NOT_TRIED = "not_tried";
    static final String EVENT_EXTRAS_PERMISSIONS = "permissions";
    static final String EVENT_EXTRAS_REQUEST_CODE = "request_code";
    static final String EVENT_EXTRAS_TRY_LOGIN_ACTIVITY = "try_login_activity";
    static final String EVENT_NAME_LOGIN_COMPLETE = "fb_mobile_login_complete";
    static final String EVENT_NAME_LOGIN_METHOD_COMPLETE = "fb_mobile_login_method_complete";
    static final String EVENT_NAME_LOGIN_METHOD_NOT_TRIED = "fb_mobile_login_method_not_tried";
    static final String EVENT_NAME_LOGIN_METHOD_START = "fb_mobile_login_method_start";
    static final String EVENT_NAME_LOGIN_START = "fb_mobile_login_start";
    static final String EVENT_NAME_LOGIN_STATUS_COMPLETE = "fb_mobile_login_status_complete";
    static final String EVENT_NAME_LOGIN_STATUS_START = "fb_mobile_login_status_start";
    static final String EVENT_PARAM_AUTH_LOGGER_ID = "0_auth_logger_id";
    static final String EVENT_PARAM_CHALLENGE = "7_challenge";
    static final String EVENT_PARAM_ERROR_CODE = "4_error_code";
    static final String EVENT_PARAM_ERROR_MESSAGE = "5_error_message";
    static final String EVENT_PARAM_EXTRAS = "6_extras";
    static final String EVENT_PARAM_LOGIN_RESULT = "2_result";
    static final String EVENT_PARAM_METHOD = "3_method";
    static final String EVENT_PARAM_METHOD_RESULT_SKIPPED = "skipped";
    static final String EVENT_PARAM_TIMESTAMP = "1_timestamp_ms";
    static final String FACEBOOK_PACKAGE_NAME = "com.facebook.katana";
    private final AppEventsLogger appEventsLogger;
    private String applicationId;
    private String facebookVersion;

    LoginLogger(Context context, String applicationId2) {
        PackageInfo facebookInfo;
        this.applicationId = applicationId2;
        this.appEventsLogger = AppEventsLogger.newLogger(context, applicationId2);
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null && (facebookInfo = packageManager.getPackageInfo("com.facebook.katana", 0)) != null) {
                this.facebookVersion = facebookInfo.versionName;
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
    }

    public String getApplicationId() {
        return this.applicationId;
    }

    static Bundle newAuthorizationLoggingBundle(String authLoggerId) {
        Bundle bundle = new Bundle();
        bundle.putLong(EVENT_PARAM_TIMESTAMP, System.currentTimeMillis());
        bundle.putString(EVENT_PARAM_AUTH_LOGGER_ID, authLoggerId);
        bundle.putString(EVENT_PARAM_METHOD, "");
        bundle.putString(EVENT_PARAM_LOGIN_RESULT, "");
        bundle.putString(EVENT_PARAM_ERROR_MESSAGE, "");
        bundle.putString(EVENT_PARAM_ERROR_CODE, "");
        bundle.putString(EVENT_PARAM_EXTRAS, "");
        return bundle;
    }

    public void logStartLogin(LoginClient.Request pendingLoginRequest) {
        Bundle bundle = newAuthorizationLoggingBundle(pendingLoginRequest.getAuthId());
        try {
            JSONObject extras = new JSONObject();
            extras.put(EVENT_EXTRAS_LOGIN_BEHAVIOR, pendingLoginRequest.getLoginBehavior().toString());
            extras.put(EVENT_EXTRAS_REQUEST_CODE, LoginClient.getLoginRequestCode());
            extras.put("permissions", TextUtils.join(",", pendingLoginRequest.getPermissions()));
            extras.put("default_audience", pendingLoginRequest.getDefaultAudience().toString());
            extras.put(EVENT_EXTRAS_IS_REAUTHORIZE, pendingLoginRequest.isRerequest());
            if (this.facebookVersion != null) {
                extras.put(EVENT_EXTRAS_FACEBOOK_VERSION, this.facebookVersion);
            }
            bundle.putString(EVENT_PARAM_EXTRAS, extras.toString());
        } catch (JSONException e) {
        }
        this.appEventsLogger.logSdkEvent(EVENT_NAME_LOGIN_START, (Double) null, bundle);
    }

    public void logCompleteLogin(String loginRequestId, Map<String, String> loggingExtras, LoginClient.Result.Code result, Map<String, String> resultExtras, Exception exception) {
        Bundle bundle = newAuthorizationLoggingBundle(loginRequestId);
        if (result != null) {
            bundle.putString(EVENT_PARAM_LOGIN_RESULT, result.getLoggingValue());
        }
        if (!(exception == null || exception.getMessage() == null)) {
            bundle.putString(EVENT_PARAM_ERROR_MESSAGE, exception.getMessage());
        }
        JSONObject jsonObject = null;
        if (!loggingExtras.isEmpty()) {
            jsonObject = new JSONObject(loggingExtras);
        }
        if (resultExtras != null) {
            if (jsonObject == null) {
                jsonObject = new JSONObject();
            }
            try {
                for (Map.Entry<String, String> entry : resultExtras.entrySet()) {
                    jsonObject.put(entry.getKey(), entry.getValue());
                }
            } catch (JSONException e) {
            }
        }
        if (jsonObject != null) {
            bundle.putString(EVENT_PARAM_EXTRAS, jsonObject.toString());
        }
        this.appEventsLogger.logSdkEvent(EVENT_NAME_LOGIN_COMPLETE, (Double) null, bundle);
    }

    public void logAuthorizationMethodStart(String authId, String method) {
        Bundle bundle = newAuthorizationLoggingBundle(authId);
        bundle.putString(EVENT_PARAM_METHOD, method);
        this.appEventsLogger.logSdkEvent(EVENT_NAME_LOGIN_METHOD_START, (Double) null, bundle);
    }

    public void logAuthorizationMethodComplete(String authId, String method, String result, String errorMessage, String errorCode, Map<String, String> loggingExtras) {
        Bundle bundle = newAuthorizationLoggingBundle(authId);
        if (result != null) {
            bundle.putString(EVENT_PARAM_LOGIN_RESULT, result);
        }
        if (errorMessage != null) {
            bundle.putString(EVENT_PARAM_ERROR_MESSAGE, errorMessage);
        }
        if (errorCode != null) {
            bundle.putString(EVENT_PARAM_ERROR_CODE, errorCode);
        }
        if (loggingExtras != null && !loggingExtras.isEmpty()) {
            bundle.putString(EVENT_PARAM_EXTRAS, new JSONObject(loggingExtras).toString());
        }
        bundle.putString(EVENT_PARAM_METHOD, method);
        this.appEventsLogger.logSdkEvent(EVENT_NAME_LOGIN_METHOD_COMPLETE, (Double) null, bundle);
    }

    public void logAuthorizationMethodNotTried(String authId, String method) {
        Bundle bundle = newAuthorizationLoggingBundle(authId);
        bundle.putString(EVENT_PARAM_METHOD, method);
        this.appEventsLogger.logSdkEvent(EVENT_NAME_LOGIN_METHOD_NOT_TRIED, (Double) null, bundle);
    }

    public void logLoginStatusStart(String loggerRef) {
        this.appEventsLogger.logSdkEvent(EVENT_NAME_LOGIN_STATUS_START, (Double) null, newAuthorizationLoggingBundle(loggerRef));
    }

    public void logLoginStatusSuccess(String loggerRef) {
        Bundle bundle = newAuthorizationLoggingBundle(loggerRef);
        bundle.putString(EVENT_PARAM_LOGIN_RESULT, LoginClient.Result.Code.SUCCESS.getLoggingValue());
        this.appEventsLogger.logSdkEvent(EVENT_NAME_LOGIN_STATUS_COMPLETE, (Double) null, bundle);
    }

    public void logLoginStatusFailure(String loggerRef) {
        Bundle bundle = newAuthorizationLoggingBundle(loggerRef);
        bundle.putString(EVENT_PARAM_LOGIN_RESULT, EVENT_EXTRAS_FAILURE);
        this.appEventsLogger.logSdkEvent(EVENT_NAME_LOGIN_STATUS_COMPLETE, (Double) null, bundle);
    }

    public void logLoginStatusError(String loggerRef, Exception exception) {
        Bundle bundle = newAuthorizationLoggingBundle(loggerRef);
        bundle.putString(EVENT_PARAM_LOGIN_RESULT, LoginClient.Result.Code.ERROR.getLoggingValue());
        bundle.putString(EVENT_PARAM_ERROR_MESSAGE, exception.toString());
        this.appEventsLogger.logSdkEvent(EVENT_NAME_LOGIN_STATUS_COMPLETE, (Double) null, bundle);
    }

    public void logUnexpectedError(String eventName, String errorMessage) {
        logUnexpectedError(eventName, errorMessage, "");
    }

    public void logUnexpectedError(String eventName, String errorMessage, String method) {
        Bundle bundle = newAuthorizationLoggingBundle("");
        bundle.putString(EVENT_PARAM_LOGIN_RESULT, LoginClient.Result.Code.ERROR.getLoggingValue());
        bundle.putString(EVENT_PARAM_ERROR_MESSAGE, errorMessage);
        bundle.putString(EVENT_PARAM_METHOD, method);
        this.appEventsLogger.logSdkEvent(eventName, (Double) null, bundle);
    }
}
