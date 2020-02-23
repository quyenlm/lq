package com.tencent.imsdk.game.base;

import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.tool.json.JsonList;
import com.tencent.imsdk.tool.json.JsonProp;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public class IMLeaderBoardResult extends IMResult {
    @JsonProp(name = "displayName")
    public String displayName;
    @JsonProp(name = "iconImageUrl")
    public String iconImageUrl;
    @JsonProp(name = "leaderBoardId")
    public String leaderBoardId;
    @JsonList(type = "com.tencent.imsdk.game.base.IMLeaderboardVariant")
    @JsonProp(name = "leaderboardVariant")
    public ArrayList<IMLeaderboardVariant> leaderboardVariant;
    @JsonProp(name = "scoreOrder")
    public int scoreOrder;

    public IMLeaderBoardResult() {
    }

    public IMLeaderBoardResult(int errCode) {
        super(errCode);
    }

    public IMLeaderBoardResult(int errCode, String message) {
        super(errCode, message);
    }

    public IMLeaderBoardResult(JSONObject object) throws JSONException {
        super(object);
    }

    public IMLeaderBoardResult(String json) throws JSONException {
        super(json);
    }
}
