package com.tencent.imsdk.feedback.base;

import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.tool.json.JsonProp;
import org.json.JSONException;
import org.json.JSONObject;

public class IMFeedbackResult extends IMResult {
    @JsonProp(name = "count")
    public int count;

    public IMFeedbackResult() {
    }

    public IMFeedbackResult(int errCode) {
        super(errCode);
    }

    public IMFeedbackResult(int errCode, String message) {
        super(errCode, message);
    }

    public IMFeedbackResult(int errCode, int imsdkCode) {
        super(errCode, imsdkCode);
    }

    public IMFeedbackResult(int errCode, String message, int imsdkCode, String imsdkMsg) {
        super(errCode, message, imsdkCode, imsdkMsg);
    }

    public IMFeedbackResult(int errCode, int imsdkCode, int thirdCode) {
        super(errCode, imsdkCode, thirdCode);
    }

    public IMFeedbackResult(int errCode, String message, int imsdkCode, String imsdkMsg, int thirdCode, String thirdMsg) {
        super(errCode, message, imsdkCode, imsdkMsg, thirdCode, thirdMsg);
    }

    public IMFeedbackResult(String json) throws JSONException {
        super(json);
    }

    public IMFeedbackResult(JSONObject json) throws JSONException {
        super(json);
    }

    public String toString() {
        return "IMFeedbackResult{count=" + this.count + '}';
    }
}
