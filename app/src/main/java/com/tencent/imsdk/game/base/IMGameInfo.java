package com.tencent.imsdk.game.base;

import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.tool.json.JsonProp;
import org.json.JSONException;
import org.json.JSONObject;

public class IMGameInfo extends IMResult {
    @JsonProp(name = "achievementCount")
    public int achievementCount;
    @JsonProp(name = "applicationId")
    public String applicationId;
    @JsonProp(name = "description")
    public String description;
    @JsonProp(name = "displayName")
    public String displayName;
    @JsonProp(name = "iconImageUrl")
    public String iconImageUrl;
    @JsonProp(name = "leaderBoardCount")
    public int leaderBoardCount;
    @JsonProp(name = "primaryCategory")
    public String primaryCategory;
    @JsonProp(name = "secondaryCategory")
    public String secondaryCategory;
    @JsonProp(name = "themeColor")
    public String themeColor;

    public IMGameInfo() {
    }

    public IMGameInfo(int errCode) {
        super(errCode);
    }

    public IMGameInfo(int errCode, String message) {
        super(errCode, message);
    }

    public IMGameInfo(JSONObject object) throws JSONException {
        super(object);
    }

    public IMGameInfo(String json) throws JSONException {
        super(json);
    }
}
