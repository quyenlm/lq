package com.tencent.imsdk.sns.base;

import com.tencent.imsdk.tool.json.JsonProp;
import com.tencent.imsdk.tool.json.JsonSerializable;
import org.json.JSONException;
import org.json.JSONObject;

public class IMLoginBindInfo extends JsonSerializable {
    @JsonProp(name = "iChannel")
    public int channelId;
    @JsonProp(name = "sUserName")
    public String guidUserNick;
    @JsonProp(name = "sPictureUrl")
    public String guidUserPortrait;
    @JsonProp(name = "iGender")
    public int guidUserSex;

    public IMLoginBindInfo() {
    }

    public IMLoginBindInfo(JSONObject object) throws JSONException {
        super(object);
    }

    public IMLoginBindInfo(String json) throws JSONException {
        super(json);
    }
}
