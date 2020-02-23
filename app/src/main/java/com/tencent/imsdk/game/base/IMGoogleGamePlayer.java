package com.tencent.imsdk.game.base;

import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.tool.json.JsonProp;
import org.json.JSONException;
import org.json.JSONObject;

public class IMGoogleGamePlayer extends IMResult {
    @JsonProp(name = "displayName")
    public String displayName;
    @JsonProp(name = "iconImageUrl")
    public String iconImageUrl;
    @JsonProp(name = "levelNumber")
    public int levelNumber;
    @JsonProp(name = "playerId")
    public String playerId;
    @JsonProp(name = "retrievedTimestamp")
    public long retrievedTimestamp;
    @JsonProp(name = "title")
    public String title;

    public IMGoogleGamePlayer() {
    }

    public IMGoogleGamePlayer(int errCode) {
        super(errCode);
    }

    public IMGoogleGamePlayer(int errCode, String message) {
        super(errCode, message);
    }

    public IMGoogleGamePlayer(JSONObject object) throws JSONException {
        super(object);
    }

    public IMGoogleGamePlayer(String json) throws JSONException {
        super(json);
    }
}
