package com.tencent.imsdk.game.base;

import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.tool.json.JsonProp;
import org.json.JSONException;
import org.json.JSONObject;

public class IMLeaderboardVariant extends IMResult {
    public static final int TIME_SPAN_ALL_TIME = 2;
    public static final int TIME_SPAN_DAILY = 0;
    public static final int TIME_SPAN_WEEKLY = 1;
    @JsonProp(name = "collection")
    public int collection;
    @JsonProp(name = "displayPlayerRank")
    public String displayPlayerRank;
    @JsonProp(name = "displayPlayerScore")
    public String displayPlayerScore;
    @JsonProp(name = "timeSpan")
    public int timeSpan;

    public IMLeaderboardVariant() {
    }

    public IMLeaderboardVariant(int errCode) {
        super(errCode);
    }

    public IMLeaderboardVariant(int errCode, String message) {
        super(errCode, message);
    }

    public IMLeaderboardVariant(JSONObject object) throws JSONException {
        super(object);
    }

    public IMLeaderboardVariant(String json) throws JSONException {
        super(json);
    }
}
