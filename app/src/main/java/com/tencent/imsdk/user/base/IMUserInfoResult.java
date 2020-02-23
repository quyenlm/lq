package com.tencent.imsdk.user.base;

import com.tencent.imsdk.IMErrorDef;
import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.tool.json.JsonProp;
import org.json.JSONException;
import org.json.JSONObject;

public class IMUserInfoResult extends IMResult {
    @JsonProp(name = "iChannel")
    public int channelId;
    @JsonProp(name = "iGameId")
    public int gameId;
    @JsonProp(name = "iGuid")
    public String guid;
    @JsonProp(name = "sInnerToken")
    public String guidToken;
    @JsonProp(name = "iExpireTime")
    public long guidTokenExpire;
    @JsonProp(name = "sBirthdate")
    public String guidUserBirthday;
    @JsonProp(name = "sUserName")
    public String guidUserNick;
    @JsonProp(name = "sPictureUrl")
    public String guidUserPortrait;
    @JsonProp(name = "iGender")
    public int guidUserSex;
    @JsonProp(name = "APersonalInfo")
    public IMUserInfo info;
    @JsonProp(name = "iOpenid")
    public String openId;

    public IMUserInfoResult() {
    }

    public IMUserInfoResult(int errCode) {
        this.retCode = errCode;
        this.retMsg = IMErrorDef.getErrorString(errCode);
    }

    public IMUserInfoResult(int errCode, int imsdkCode) {
        this.retCode = errCode;
        this.retMsg = IMErrorDef.getErrorString(errCode);
        this.imsdkRetCode = imsdkCode;
    }

    public IMUserInfoResult(int errCode, int imsdkCode, int thirdCode) {
        this.retCode = errCode;
        this.retMsg = IMErrorDef.getErrorString(errCode);
        this.imsdkRetCode = imsdkCode;
        this.thirdRetCode = thirdCode;
    }

    public IMUserInfoResult(int errCode, String message) {
        this.retCode = errCode;
        this.retMsg = IMErrorDef.getErrorString(errCode) + ":" + message;
    }

    public IMUserInfoResult(int errCode, String message, int imsdkCode, String imsdkMsg) {
        this.retCode = errCode;
        this.retMsg = IMErrorDef.getErrorString(errCode) + ":" + message;
        this.imsdkRetCode = imsdkCode;
        this.imsdkRetMsg = imsdkMsg;
    }

    public IMUserInfoResult(int errCode, String message, int imsdkCode, String imsdkMsg, int thirdCode, String thirdMsg) {
        this.retCode = errCode;
        this.retMsg = IMErrorDef.getErrorString(errCode) + ":" + message;
        this.imsdkRetCode = imsdkCode;
        this.imsdkRetMsg = imsdkMsg;
        this.thirdRetCode = thirdCode;
        this.thirdRetMsg = thirdMsg;
    }

    public IMUserInfoResult(JSONObject object) throws JSONException {
        super(object);
        this.imsdkRetCode = this.retCode;
        this.imsdkRetMsg = this.retMsg;
    }

    public IMUserInfoResult(String json) throws JSONException {
        super(json);
        this.imsdkRetCode = this.retCode;
        this.imsdkRetMsg = this.retMsg;
    }
}
