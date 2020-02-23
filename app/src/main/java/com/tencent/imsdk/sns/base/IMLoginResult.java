package com.tencent.imsdk.sns.base;

import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.tool.json.JsonList;
import com.tencent.imsdk.tool.json.JsonProp;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class IMLoginResult extends IMResult {
    @JsonProp(name = "channel")
    public String channel;
    @JsonProp(name = "iChannel")
    public int channelId;
    @JsonList(type = "java.lang.String")
    @JsonProp(name = "channelPermissions")
    public List<String> channelPermissions;
    @JsonProp(name = "sExtToken")
    public String channelToken;
    @JsonProp(name = "iExtTokenExpireTime")
    public long channelTokenExpire;
    @JsonProp(name = "sChannelId")
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

    public IMLoginResult() {
    }

    public IMLoginResult(int errCode) {
        super(errCode);
    }

    public IMLoginResult(int errCode, String message) {
        super(errCode, message);
    }

    public IMLoginResult(int errCode, int imsdkCode) {
        super(errCode, imsdkCode);
    }

    public IMLoginResult(int errCode, String message, int imsdkCode, String imsdkMsg) {
        super(errCode, message, imsdkCode, imsdkMsg);
    }

    public IMLoginResult(int errCode, int imsdkCode, int thirdCode) {
        super(errCode, imsdkCode, thirdCode);
    }

    public IMLoginResult(int errCode, String message, int imsdkCode, String imsdkMsg, int thirdCode, String thirdMsg) {
        super(errCode, message, imsdkCode, imsdkMsg, thirdCode, thirdMsg);
    }

    public IMLoginResult(String json) throws JSONException {
        super(json);
    }

    public IMLoginResult(JSONObject json) throws JSONException {
        super(json);
    }

    public boolean isValid() {
        if (this.channelTokenExpire <= System.currentTimeMillis() / 1000) {
            return false;
        }
        if (this.guid.length() <= 0 || this.guidTokenExpire > System.currentTimeMillis() / 1000) {
            return true;
        }
        return false;
    }
}
