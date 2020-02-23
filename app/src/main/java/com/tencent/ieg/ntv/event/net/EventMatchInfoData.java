package com.tencent.ieg.ntv.event.net;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.tencent.ieg.ntv.event.Event;
import com.tencent.ieg.ntv.utils.Logger;
import com.tencent.ieg.ntv.utils.Util;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class EventMatchInfoData extends Event {
    private static final String TAG = EventMatchInfoData.class.getSimpleName();
    public List<MatchInfo> matchInfoList;

    public static class MatchInfo {
        public String desc;
        public boolean isFinished;
        public int matchid;
        public String replayurl;
        public int status;
        public List<TeamInfo> teams;
        public long time;
    }

    public static class MatchStatus {
        public static int FINISH = 1;
        public static int NOTSHOW = 0;
        public static int REPLAY = 4;
        public static int RESERVABLE = 2;
        public static int RESERVED = 3;
    }

    public static class TeamInfo {
        public String logoUrl;
        public String name;
        public String score;
    }

    public boolean parse(JSONArray arr) {
        if (arr != null) {
            try {
                this.matchInfoList = new LinkedList();
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject matchInfoObj = arr.getJSONObject(i);
                    MatchInfo matchInfo = new MatchInfo();
                    matchInfo.matchid = matchInfoObj.has("id") ? matchInfoObj.getInt("id") : 0;
                    matchInfo.desc = matchInfoObj.getString("desc");
                    matchInfo.time = (long) matchInfoObj.getInt("time");
                    matchInfo.teams = new LinkedList();
                    matchInfo.replayurl = matchInfoObj.optString("replay_url", "");
                    matchInfo.isFinished = matchInfoObj.optInt("status") == 1;
                    if (Util.getCurrentMSeconds() < matchInfo.time * 1000) {
                        matchInfo.status = MatchStatus.RESERVABLE;
                    } else if (matchInfo.replayurl != null && matchInfo.replayurl.length() > 5) {
                        matchInfo.status = MatchStatus.REPLAY;
                    } else if (matchInfoObj.getInt("status") == MatchStatus.FINISH) {
                        matchInfo.status = MatchStatus.FINISH;
                    } else {
                        matchInfo.status = MatchStatus.NOTSHOW;
                    }
                    JSONArray arrTeam = matchInfoObj.getJSONArray("teams");
                    if (arrTeam != null) {
                        for (int k = 0; k < arrTeam.length(); k++) {
                            JSONObject teamObj = arrTeam.getJSONObject(k);
                            TeamInfo teamInfo = new TeamInfo();
                            teamInfo.name = teamObj.getString("name");
                            teamInfo.logoUrl = teamObj.optString("logo_url");
                            teamInfo.score = teamObj.optString(FirebaseAnalytics.Param.SCORE, "-");
                            matchInfo.teams.add(teamInfo);
                        }
                    }
                    this.matchInfoList.add(matchInfo);
                }
                return true;
            } catch (Exception e) {
                Logger.w(TAG, e);
            }
        }
        return false;
    }
}
