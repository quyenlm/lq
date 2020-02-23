package com.tencent.imsdk.sns.base;

import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.tool.json.JsonList;
import com.tencent.imsdk.tool.json.JsonProp;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class IMFriendResult extends IMResult {
    @JsonProp(name = "iChannel")
    public int channelId;
    @JsonProp(name = "sChannelId")
    public String channelUserId;
    @JsonProp(name = "iCount")
    public int count;
    @JsonList(type = "com.tencent.imsdk.sns.base.IMFriendInfo")
    @JsonProp(name = "data")
    public List<IMFriendInfo> friendInfoList;
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

    public IMFriendResult() {
    }

    public IMFriendResult(int errCode) {
        super(errCode);
    }

    public IMFriendResult(int errCode, String message) {
        super(errCode, message);
    }

    public IMFriendResult(int errCode, int imsdkCode) {
        super(errCode, imsdkCode);
    }

    public IMFriendResult(int errCode, String message, int imsdkCode, String imsdkMsg) {
        super(errCode, message, imsdkCode, imsdkMsg);
    }

    public IMFriendResult(int errCode, int imsdkCode, int thirdCode) {
        super(errCode, imsdkCode, thirdCode);
    }

    public IMFriendResult(int errCode, String message, int imsdkCode, String imsdkMsg, int thirdCode, String thirdMsg) {
        super(errCode, message, imsdkCode, imsdkMsg, thirdCode, thirdMsg);
    }

    public IMFriendResult(JSONObject object) throws JSONException {
        super(object);
    }

    public IMFriendResult(String json) throws JSONException {
        super(json);
    }
}
