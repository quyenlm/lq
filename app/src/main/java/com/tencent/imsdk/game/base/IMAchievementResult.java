package com.tencent.imsdk.game.base;

import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.tool.json.JsonProp;
import org.json.JSONException;
import org.json.JSONObject;

public class IMAchievementResult extends IMResult {
    public static final int STATE_HIDDEN = 2;
    public static final int STATE_REVEALED = 1;
    public static final int STATE_UNLOCKED = 0;
    @JsonProp(name = "achievementId")
    public String achievementId;
    @JsonProp(name = "achievementName")
    public String achievementName;
    @JsonProp(name = "currentSteps")
    public int currentSteps;
    @JsonProp(name = "description")
    public String description;
    @JsonProp(name = "player")
    public IMGoogleGamePlayer player;
    @JsonProp(name = "revealedImageUrl")
    public String revealedImageUrl;
    @JsonProp(name = "state")
    public int state;
    @JsonProp(name = "totalSteps")
    public int totalSteps;
    @JsonProp(name = "unlockedImageUrl")
    public String unlockedImageUrl;

    public IMAchievementResult() {
    }

    public IMAchievementResult(int errCode) {
        super(errCode);
    }

    public IMAchievementResult(int errCode, String message) {
        super(errCode, message);
    }

    public IMAchievementResult(JSONObject object) throws JSONException {
        super(object);
    }

    public IMAchievementResult(String json) throws JSONException {
        super(json);
    }
}
