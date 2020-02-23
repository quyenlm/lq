package com.tencent.imsdk.ads.entity;

import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.tool.json.JsonProp;
import org.json.JSONException;
import org.json.JSONObject;

public class IMAdsResult extends IMResult {
    @JsonProp(name = "stateCode")
    public String stateCode = "";

    public IMAdsResult() {
    }

    public IMAdsResult(int errCode) {
        super(errCode);
    }

    public IMAdsResult(int errCode, String message) {
        super(errCode, message);
    }

    public IMAdsResult(String json) throws JSONException {
        super(json);
    }

    public IMAdsResult(JSONObject json) throws JSONException {
        super(json);
    }

    public String toString() {
        return "IMAdsResult{stateCode='" + this.stateCode + '\'' + '}';
    }
}
