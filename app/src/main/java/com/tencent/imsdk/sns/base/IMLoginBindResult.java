package com.tencent.imsdk.sns.base;

import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.tool.json.JsonList;
import com.tencent.imsdk.tool.json.JsonProp;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class IMLoginBindResult extends IMResult {
    @JsonList(type = "com.tencent.imsdk.sns.base.IMLoginBindInfo")
    @JsonProp(name = "ARelationInfo")
    public List<IMLoginBindInfo> bindInfoList;
    @JsonProp(name = "iChannel")
    public int channelId;
    @JsonProp(name = "sExtToken")
    public String channelToken;
    @JsonProp(name = "iExtTokenExpireTime")
    public long channelTokenExpire;
    @JsonProp(name = "channelUserId")
    public String channelUserId;
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
    @JsonProp(name = "iOpenid")
    public String openId;

    public IMLoginBindResult() {
    }

    public IMLoginBindResult(int errCode) {
        super(errCode);
    }

    public IMLoginBindResult(int errCode, String message) {
        super(errCode, message);
    }

    public IMLoginBindResult(int errCode, int imsdkRetCode) {
        super(errCode, imsdkRetCode);
    }

    public IMLoginBindResult(int errCode, String message, int imsdkCode, String imsdkMsg) {
        super(errCode, message, imsdkCode, imsdkMsg);
    }

    public IMLoginBindResult(int errCode, int imsdkCode, int thirdCode) {
        super(errCode, imsdkCode, thirdCode);
    }

    public IMLoginBindResult(int errCode, String message, int imsdkCode, String imsdkMsg, int thirdCode, String thirdMsg) {
        super(errCode, message, imsdkCode, imsdkMsg, thirdCode, thirdMsg);
    }

    public IMLoginBindResult(String json) throws JSONException {
        super(json);
    }

    public IMLoginBindResult(JSONObject json) throws JSONException {
        super(json);
    }
}
