package com.tencent.imsdk.sns.base;

import com.tencent.imsdk.tool.json.JsonProp;
import com.tencent.imsdk.tool.json.JsonSerializable;
import org.json.JSONException;
import org.json.JSONObject;

public class IMFriendInfo extends JsonSerializable {
    @JsonProp(name = "sChannelId")
    public String channelUserId;
    @JsonProp(name = "sUserName")
    public String guidUserNick;
    @JsonProp(name = "sPictureUrl")
    public String guidUserPortrait;
    @JsonProp(name = "iGender")
    public int guidUserSex;
    @JsonProp(name = "iOpenid")
    public String openId;

    public IMFriendInfo() {
    }

    public IMFriendInfo(JSONObject object) throws JSONException {
        super(object);
    }

    public IMFriendInfo(String json) throws JSONException {
        super(json);
    }

    public String toString() {
        return "guidUserNick=" + this.guidUserNick + ", channelUserId='" + this.channelUserId + '\'' + ", guidUserPortrait=" + this.guidUserPortrait + '\'';
    }
}
