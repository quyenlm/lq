package com.vk.sdk.api;

import android.net.Uri;
import com.tencent.tp.a.h;
import com.vk.sdk.VKObject;
import com.vk.sdk.util.VKJsonHelper;
import java.util.ArrayList;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class VKError extends VKObject {
    private static final String ERROR_DESCRIPTION = "error_description";
    private static final String ERROR_REASON = "error_reason";
    private static final String FAIL = "fail";
    public static final int VK_API_ERROR = -101;
    public static final int VK_CANCELED = -102;
    public static final int VK_JSON_FAILED = -104;
    public static final int VK_REQUEST_HTTP_FAILED = -105;
    public static final int VK_REQUEST_NOT_PREPARED = -103;
    public VKError apiError;
    public String captchaImg;
    public String captchaSid;
    public int errorCode;
    public String errorMessage;
    public String errorReason;
    public Exception httpError;
    public String redirectUri;
    public VKRequest request;
    public ArrayList<Map<String, String>> requestParams;

    public VKError(int errorCode2) {
        this.errorCode = errorCode2;
    }

    public VKError(JSONObject json) throws JSONException {
        VKError internalError = new VKError(json.getInt("error_code"));
        internalError.errorMessage = json.getString(VKApiConst.ERROR_MSG);
        internalError.requestParams = (ArrayList) VKJsonHelper.toList(json.getJSONArray(VKApiConst.REQUEST_PARAMS));
        if (internalError.errorCode == 14) {
            internalError.captchaImg = json.getString(VKApiConst.CAPTCHA_IMG);
            internalError.captchaSid = json.getString(VKApiConst.CAPTCHA_SID);
        }
        if (internalError.errorCode == 17) {
            internalError.redirectUri = json.getString("redirect_uri");
        }
        this.errorCode = -101;
        this.apiError = internalError;
    }

    public VKError(Map<String, String> queryParams) {
        this.errorCode = -101;
        this.errorReason = queryParams.get(ERROR_REASON);
        this.errorMessage = Uri.decode(queryParams.get("error_description"));
        if (queryParams.containsKey(FAIL)) {
            this.errorReason = "Action failed";
        }
        if (queryParams.containsKey("cancel")) {
            this.errorCode = -102;
            this.errorReason = "User canceled request";
        }
    }

    public void answerCaptcha(String userEnteredCode) {
        VKParameters params = new VKParameters();
        params.put(VKApiConst.CAPTCHA_SID, this.captchaSid);
        params.put(VKApiConst.CAPTCHA_KEY, userEnteredCode);
        this.request.addExtraParameters(params);
        this.request.repeat();
    }

    public static VKError getRegisteredError(long requestId) {
        return (VKError) getRegisteredObject(requestId);
    }

    private void appendFields(StringBuilder builder) {
        if (this.errorReason != null) {
            builder.append(String.format("; %s", new Object[]{this.errorReason}));
        }
        if (this.errorMessage != null) {
            builder.append(String.format("; %s", new Object[]{this.errorMessage}));
        }
    }

    public String toString() {
        StringBuilder errorString = new StringBuilder("VKError (");
        switch (this.errorCode) {
            case -105:
                errorString.append("HTTP failed");
                break;
            case -104:
                errorString.append("JSON failed");
                break;
            case -103:
                errorString.append("Request wasn't prepared");
                break;
            case -102:
                errorString.append("Canceled");
                break;
            case -101:
                errorString.append("API error");
                if (this.apiError != null) {
                    errorString.append(this.apiError.toString());
                    break;
                }
                break;
            default:
                errorString.append(String.format("code: %d; ", new Object[]{Integer.valueOf(this.errorCode)}));
                break;
        }
        appendFields(errorString);
        errorString.append(h.b);
        return errorString.toString();
    }
}
