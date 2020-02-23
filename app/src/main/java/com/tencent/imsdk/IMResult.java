package com.tencent.imsdk;

import com.tencent.imsdk.tool.json.JsonInt;
import com.tencent.imsdk.tool.json.JsonProp;
import com.tencent.imsdk.tool.json.JsonSerializable;
import com.tencent.imsdk.tool.json.JsonString;
import org.json.JSONException;
import org.json.JSONObject;

public class IMResult extends JsonSerializable {
    @JsonInt(def = -1)
    @JsonProp(name = "imsdkRetCode")
    public int imsdkRetCode;
    @JsonString(def = "")
    @JsonProp(name = "imsdkRetMsg")
    public String imsdkRetMsg;
    @JsonInt(def = 1)
    @JsonProp(name = "code")
    public int retCode;
    @JsonString(def = "{}")
    @JsonProp(name = "retExtraJson")
    public String retExtraJson;
    @JsonProp(name = "desc")
    public String retMsg;
    @JsonInt(def = -1)
    @JsonProp(name = "thirdRetCode")
    public int thirdRetCode;
    @JsonString(def = "")
    @JsonProp(name = "thirdRetMsg")
    public String thirdRetMsg;

    public IMResult() {
    }

    public IMResult(int errCode) {
        this.retCode = errCode;
        this.retMsg = IMErrorDef.getErrorString(errCode);
    }

    public IMResult(int errCode, int imsdkCode) {
        this.retCode = errCode;
        this.retMsg = IMErrorDef.getErrorString(errCode);
        this.imsdkRetCode = imsdkCode;
        this.imsdkRetMsg = IMErrorMsg.getMessageByCode(this.imsdkRetCode);
    }

    public IMResult(int errCode, int imsdkCode, int thirdCode) {
        this.retCode = errCode;
        this.retMsg = IMErrorDef.getErrorString(errCode);
        this.imsdkRetCode = imsdkCode;
        this.imsdkRetMsg = IMErrorMsg.getMessageByCode(this.imsdkRetCode);
        this.thirdRetCode = thirdCode;
    }

    public IMResult(int errCode, String message) {
        this.retCode = errCode;
        this.retMsg = message;
    }

    public IMResult(int errCode, String message, int imsdkCode, String imsdkMsg) {
        this.retCode = errCode;
        this.retMsg = message;
        this.imsdkRetCode = imsdkCode;
        this.imsdkRetMsg = imsdkMsg;
    }

    public IMResult(int errCode, String message, int imsdkCode, String imsdkMsg, int thirdCode, String thirdMsg) {
        this.retCode = errCode;
        this.retMsg = message;
        this.imsdkRetCode = imsdkCode;
        this.imsdkRetMsg = imsdkMsg;
        this.thirdRetCode = thirdCode;
        this.thirdRetMsg = thirdMsg;
    }

    public IMResult(JSONObject object) throws JSONException {
        super(object);
        this.imsdkRetCode = this.retCode;
    }

    public IMResult(String json) throws JSONException {
        super(json);
        this.imsdkRetCode = this.retCode;
    }
}
