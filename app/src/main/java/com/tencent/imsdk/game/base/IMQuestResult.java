package com.tencent.imsdk.game.base;

import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.tool.json.JsonProp;
import org.json.JSONException;
import org.json.JSONObject;

public class IMQuestResult extends IMResult {
    @JsonProp(name = "acceptedTimestamp")
    public long acceptedTimestamp;
    @JsonProp(name = "description")
    public String description;
    @JsonProp(name = "displayName")
    public String displayName;
    @JsonProp(name = "endTimestamp")
    public long endTimestamp;
    @JsonProp(name = "game")
    public IMGameInfo game;
    @JsonProp(name = "lastUpdatedTimestamp")
    public long lastUpdatedTimestamp;
    @JsonProp(name = "questId")
    public String questId;
    @JsonProp(name = "startTimestamp")
    public long startTimestamp;
    @JsonProp(name = "state")
    public int state;

    public IMQuestResult() {
    }

    public IMQuestResult(int errCode) {
        super(errCode);
    }

    public IMQuestResult(int errCode, String message) {
        super(errCode, message);
    }

    public IMQuestResult(JSONObject object) throws JSONException {
        super(object);
    }

    public IMQuestResult(String json) throws JSONException {
        super(json);
    }
}
