package com.tencent.imsdk.sns.base;

import com.tencent.imsdk.tool.json.JsonProp;
import org.json.JSONException;
import org.json.JSONObject;

public class IMFriendInfoExViber extends IMFriendInfo {
    public static final String ACTION = "action";
    public static final String IMG = "img";
    public static final String TAG = "tag";
    public static final String TYPE = "type";
    public static final int TYPE_BEAT_ME = 3;
    public static final int TYPE_HELP = 2;
    public static final int TYPE_INVITE = 1;
    public static final int TYPE_REWARD = 4;
    @JsonProp(name = "Has")
    public boolean has;
    @JsonProp(name = "Language")
    public String language;
    @JsonProp(name = "Name")
    public String name;
    @JsonProp(name = "Photo")
    public String photoId;
    @JsonProp(name = "token")
    public String token;

    public IMFriendInfoExViber() {
    }

    public IMFriendInfoExViber(String json) throws JSONException {
        super(json);
    }

    public IMFriendInfoExViber(JSONObject json) throws JSONException {
        super(json);
    }
}
