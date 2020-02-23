package com.tencent.imsdk.sns.base;

import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.tool.json.JsonProp;
import org.json.JSONException;
import org.json.JSONObject;

public class IMUrlResult extends IMResult {
    @JsonProp(name = "shortUrl")
    public String shortUrl;

    public IMUrlResult() {
    }

    public IMUrlResult(int errCode) {
        super(errCode);
    }

    public IMUrlResult(int errCode, String message) {
        super(errCode, message);
    }

    public IMUrlResult(int errCode, int imsdkCode) {
        super(errCode, imsdkCode);
    }

    public IMUrlResult(int errCode, String message, int imsdkCode, String imsdkMsg) {
        super(errCode, message, imsdkCode, imsdkMsg);
    }

    public IMUrlResult(int errCode, int imsdkCode, int thirdCode) {
        super(errCode, imsdkCode, thirdCode);
    }

    public IMUrlResult(int errCode, String message, int imsdkCode, String imsdkMsg, int thirdCode, String thirdMsg) {
        super(errCode, message, imsdkCode, imsdkMsg, thirdCode, thirdMsg);
    }

    public IMUrlResult(String json) throws JSONException {
        super(json);
    }

    public IMUrlResult(JSONObject json) throws JSONException {
        super(json);
    }
}
