package com.tencent.imsdk.sns.base;

import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.tool.json.JsonProp;
import org.json.JSONException;
import org.json.JSONObject;

public class IMLaunchResult extends IMResult {
    @JsonProp(name = "launchData")
    public String launchData;
    @JsonProp(name = "launchUri")
    public String launchUri;

    public IMLaunchResult() {
    }

    public IMLaunchResult(int errCode, int imsdkCode) {
        super(errCode, imsdkCode);
    }

    public IMLaunchResult(int errCode, String message, int imsdkCode, String imsdkMsg) {
        super(errCode, message, imsdkCode, imsdkMsg);
    }

    public IMLaunchResult(int errCode, int imsdkCode, int thirdCode) {
        super(errCode, imsdkCode, thirdCode);
    }

    public IMLaunchResult(int errCode, String message, int imsdkCode, String imsdkMsg, int thirdCode, String thirdMsg) {
        super(errCode, message, imsdkCode, imsdkMsg, thirdCode, thirdMsg);
    }

    public IMLaunchResult(JSONObject object) throws JSONException {
        super(object);
    }

    public IMLaunchResult(String json) throws JSONException {
        super(json);
    }
}
