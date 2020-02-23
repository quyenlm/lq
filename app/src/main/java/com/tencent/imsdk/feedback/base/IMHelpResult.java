package com.tencent.imsdk.feedback.base;

import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.tool.json.JsonProp;
import org.json.JSONException;
import org.json.JSONObject;

public class IMHelpResult extends IMResult {
    @JsonProp(name = "state")
    public int state;

    public IMHelpResult() {
    }

    public IMHelpResult(int errCode) {
        super(errCode);
    }

    public IMHelpResult(int errCode, String message) {
        super(errCode, message);
    }

    public IMHelpResult(int errCode, int imsdkCode) {
        super(errCode, imsdkCode);
    }

    public IMHelpResult(int errCode, String message, int imsdkCode, String imsdkMsg) {
        super(errCode, message, imsdkCode, imsdkMsg);
    }

    public IMHelpResult(int errCode, int imsdkCode, int thirdCode) {
        super(errCode, imsdkCode, thirdCode);
    }

    public IMHelpResult(int errCode, String message, int imsdkCode, String imsdkMsg, int thirdCode, String thirdMsg) {
        super(errCode, message, imsdkCode, imsdkMsg, thirdCode, thirdMsg);
    }

    public IMHelpResult(String json) throws JSONException {
        super(json);
    }

    public IMHelpResult(JSONObject json) throws JSONException {
        super(json);
    }
}
