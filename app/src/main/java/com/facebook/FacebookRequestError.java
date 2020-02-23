package com.facebook;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.facebook.internal.FetchedAppSettings;
import com.facebook.internal.FetchedAppSettingsManager;
import com.facebook.internal.Utility;
import java.net.HttpURLConnection;
import org.json.JSONException;
import org.json.JSONObject;

public final class FacebookRequestError implements Parcelable {
    private static final String BODY_KEY = "body";
    private static final String CODE_KEY = "code";
    public static final Parcelable.Creator<FacebookRequestError> CREATOR = new Parcelable.Creator<FacebookRequestError>() {
        public FacebookRequestError createFromParcel(Parcel in) {
            return new FacebookRequestError(in);
        }

        public FacebookRequestError[] newArray(int size) {
            return new FacebookRequestError[size];
        }
    };
    private static final String ERROR_CODE_FIELD_KEY = "code";
    private static final String ERROR_CODE_KEY = "error_code";
    private static final String ERROR_IS_TRANSIENT_KEY = "is_transient";
    private static final String ERROR_KEY = "error";
    private static final String ERROR_MESSAGE_FIELD_KEY = "message";
    private static final String ERROR_MSG_KEY = "error_msg";
    private static final String ERROR_REASON_KEY = "error_reason";
    private static final String ERROR_SUB_CODE_KEY = "error_subcode";
    private static final String ERROR_TYPE_FIELD_KEY = "type";
    private static final String ERROR_USER_MSG_KEY = "error_user_msg";
    private static final String ERROR_USER_TITLE_KEY = "error_user_title";
    static final Range HTTP_RANGE_SUCCESS = new Range(200, 299);
    public static final int INVALID_ERROR_CODE = -1;
    public static final int INVALID_HTTP_STATUS_CODE = -1;
    private final Object batchRequestResult;
    private final Category category;
    private final HttpURLConnection connection;
    private final int errorCode;
    private final String errorMessage;
    private final String errorRecoveryMessage;
    private final String errorType;
    private final String errorUserMessage;
    private final String errorUserTitle;
    private final FacebookException exception;
    private final JSONObject requestResult;
    private final JSONObject requestResultBody;
    private final int requestStatusCode;
    private final int subErrorCode;

    public enum Category {
        LOGIN_RECOVERABLE,
        OTHER,
        TRANSIENT
    }

    private static class Range {
        private final int end;
        private final int start;

        private Range(int start2, int end2) {
            this.start = start2;
            this.end = end2;
        }

        /* access modifiers changed from: package-private */
        public boolean contains(int value) {
            return this.start <= value && value <= this.end;
        }
    }

    private FacebookRequestError(int requestStatusCode2, int errorCode2, int subErrorCode2, String errorType2, String errorMessage2, String errorUserTitle2, String errorUserMessage2, boolean errorIsTransient, JSONObject requestResultBody2, JSONObject requestResult2, Object batchRequestResult2, HttpURLConnection connection2, FacebookException exception2) {
        Category classify;
        this.requestStatusCode = requestStatusCode2;
        this.errorCode = errorCode2;
        this.subErrorCode = subErrorCode2;
        this.errorType = errorType2;
        this.errorMessage = errorMessage2;
        this.requestResultBody = requestResultBody2;
        this.requestResult = requestResult2;
        this.batchRequestResult = batchRequestResult2;
        this.connection = connection2;
        this.errorUserTitle = errorUserTitle2;
        this.errorUserMessage = errorUserMessage2;
        boolean isLocalException = false;
        if (exception2 != null) {
            this.exception = exception2;
            isLocalException = true;
        } else {
            this.exception = new FacebookServiceException(this, errorMessage2);
        }
        FacebookRequestErrorClassification errorClassification = getErrorClassification();
        if (isLocalException) {
            classify = Category.OTHER;
        } else {
            classify = errorClassification.classify(errorCode2, subErrorCode2, errorIsTransient);
        }
        this.category = classify;
        this.errorRecoveryMessage = errorClassification.getRecoveryMessage(this.category);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    FacebookRequestError(HttpURLConnection connection2, Exception exception2) {
        this(-1, -1, -1, (String) null, (String) null, (String) null, (String) null, false, (JSONObject) null, (JSONObject) null, (Object) null, connection2, exception2 instanceof FacebookException ? (FacebookException) exception2 : new FacebookException((Throwable) exception2));
    }

    public FacebookRequestError(int errorCode2, String errorType2, String errorMessage2) {
        this(-1, errorCode2, -1, errorType2, errorMessage2, (String) null, (String) null, false, (JSONObject) null, (JSONObject) null, (Object) null, (HttpURLConnection) null, (FacebookException) null);
    }

    public Category getCategory() {
        return this.category;
    }

    public int getRequestStatusCode() {
        return this.requestStatusCode;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public int getSubErrorCode() {
        return this.subErrorCode;
    }

    public String getErrorType() {
        return this.errorType;
    }

    public String getErrorMessage() {
        if (this.errorMessage != null) {
            return this.errorMessage;
        }
        return this.exception.getLocalizedMessage();
    }

    public String getErrorRecoveryMessage() {
        return this.errorRecoveryMessage;
    }

    public String getErrorUserMessage() {
        return this.errorUserMessage;
    }

    public String getErrorUserTitle() {
        return this.errorUserTitle;
    }

    public JSONObject getRequestResultBody() {
        return this.requestResultBody;
    }

    public JSONObject getRequestResult() {
        return this.requestResult;
    }

    public Object getBatchRequestResult() {
        return this.batchRequestResult;
    }

    public HttpURLConnection getConnection() {
        return this.connection;
    }

    public FacebookException getException() {
        return this.exception;
    }

    public String toString() {
        return "{HttpStatus: " + this.requestStatusCode + ", errorCode: " + this.errorCode + ", subErrorCode: " + this.subErrorCode + ", errorType: " + this.errorType + ", errorMessage: " + getErrorMessage() + "}";
    }

    static FacebookRequestError checkResponseAndCreateError(JSONObject singleResult, Object batchResult, HttpURLConnection connection2) {
        JSONObject jSONObject;
        try {
            if (singleResult.has("code")) {
                int responseCode = singleResult.getInt("code");
                Object body = Utility.getStringPropertyAsJSON(singleResult, BODY_KEY, GraphResponse.NON_JSON_RESPONSE_PROPERTY);
                if (body != null && (body instanceof JSONObject)) {
                    JSONObject jsonBody = (JSONObject) body;
                    String errorType2 = null;
                    String errorMessage2 = null;
                    String errorUserMessage2 = null;
                    String errorUserTitle2 = null;
                    boolean errorIsTransient = false;
                    int errorCode2 = -1;
                    int errorSubCode = -1;
                    boolean hasError = false;
                    if (jsonBody.has("error")) {
                        JSONObject error = (JSONObject) Utility.getStringPropertyAsJSON(jsonBody, "error", (String) null);
                        errorType2 = error.optString("type", (String) null);
                        errorMessage2 = error.optString("message", (String) null);
                        errorCode2 = error.optInt("code", -1);
                        errorSubCode = error.optInt("error_subcode", -1);
                        errorUserMessage2 = error.optString(ERROR_USER_MSG_KEY, (String) null);
                        errorUserTitle2 = error.optString(ERROR_USER_TITLE_KEY, (String) null);
                        errorIsTransient = error.optBoolean(ERROR_IS_TRANSIENT_KEY, false);
                        hasError = true;
                    } else if (jsonBody.has("error_code") || jsonBody.has("error_msg") || jsonBody.has(ERROR_REASON_KEY)) {
                        errorType2 = jsonBody.optString(ERROR_REASON_KEY, (String) null);
                        errorMessage2 = jsonBody.optString("error_msg", (String) null);
                        errorCode2 = jsonBody.optInt("error_code", -1);
                        errorSubCode = jsonBody.optInt("error_subcode", -1);
                        hasError = true;
                    }
                    if (hasError) {
                        return new FacebookRequestError(responseCode, errorCode2, errorSubCode, errorType2, errorMessage2, errorUserTitle2, errorUserMessage2, errorIsTransient, jsonBody, singleResult, batchResult, connection2, (FacebookException) null);
                    }
                }
                if (!HTTP_RANGE_SUCCESS.contains(responseCode)) {
                    if (singleResult.has(BODY_KEY)) {
                        jSONObject = (JSONObject) Utility.getStringPropertyAsJSON(singleResult, BODY_KEY, GraphResponse.NON_JSON_RESPONSE_PROPERTY);
                    } else {
                        jSONObject = null;
                    }
                    return new FacebookRequestError(responseCode, -1, -1, (String) null, (String) null, (String) null, (String) null, false, jSONObject, singleResult, batchResult, connection2, (FacebookException) null);
                }
            }
        } catch (JSONException e) {
        }
        return null;
    }

    static synchronized FacebookRequestErrorClassification getErrorClassification() {
        FacebookRequestErrorClassification errorClassification;
        synchronized (FacebookRequestError.class) {
            FetchedAppSettings appSettings = FetchedAppSettingsManager.getAppSettingsWithoutQuery(FacebookSdk.getApplicationId());
            if (appSettings == null) {
                errorClassification = FacebookRequestErrorClassification.getDefaultErrorClassification();
            } else {
                errorClassification = appSettings.getErrorClassification();
            }
        }
        return errorClassification;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.requestStatusCode);
        out.writeInt(this.errorCode);
        out.writeInt(this.subErrorCode);
        out.writeString(this.errorType);
        out.writeString(this.errorMessage);
        out.writeString(this.errorUserTitle);
        out.writeString(this.errorUserMessage);
    }

    private FacebookRequestError(Parcel in) {
        this(in.readInt(), in.readInt(), in.readInt(), in.readString(), in.readString(), in.readString(), in.readString(), false, (JSONObject) null, (JSONObject) null, (Object) null, (HttpURLConnection) null, (FacebookException) null);
    }

    public int describeContents() {
        return 0;
    }
}
