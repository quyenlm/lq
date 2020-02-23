package com.tencent.ieg.ntv.event.net;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.tencent.ieg.ntv.event.Event;
import com.tencent.ieg.ntv.event.net.EventMatchInfoData;
import com.tencent.ieg.ntv.model.MatchInfoModel;
import com.tencent.ieg.ntv.model.RedDotInfo;
import com.tencent.ieg.ntv.utils.Logger;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class EventGetInfoData extends Event {
    private static final String TAG = EventGetInfoData.class.getSimpleName();
    public List<MatchInfoNew> mMatchInfo = null;
    public short mResult;
    public List<Integer> mSubInfo = null;
    public List<TeamInfoNew> mTeamInfo = null;

    public static class MatchInfoNew {
        public String desc;
        public int id;
        public short status;
        public List<MatchTeamNew> teams;
        public int time;
    }

    public static class MatchTeamNew {
        public int id;
        public short score;
    }

    public static class TeamInfoNew {
        public int id;
        public String logo;
        public String name;
    }

    public boolean parse(String jsoninfo) {
        List<EventMatchInfoData.MatchInfo> matchInfoList = MatchInfoModel.getInstance().getMatchInfoList();
        try {
            JSONObject jsonObject = new JSONObject(jsoninfo);
            if (jsonObject == null) {
                return false;
            }
            if (jsonObject.has(RedDotInfo.RedDotType.TYPE_MATCHINFO)) {
                this.mMatchInfo = new LinkedList();
                JSONArray jsonArray = jsonObject.optJSONArray(RedDotInfo.RedDotType.TYPE_MATCHINFO);
                if (jsonArray != null && jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        MatchInfoNew matchInfoNew = new MatchInfoNew();
                        JSONObject jobj = jsonArray.getJSONObject(i);
                        if (jobj != null) {
                            matchInfoNew.id = jobj.optInt("id");
                            matchInfoNew.desc = jobj.optString("desc");
                            matchInfoNew.time = jobj.optInt("time");
                            matchInfoNew.status = (short) jobj.optInt("status");
                            List<MatchTeamNew> teamList = new LinkedList<>();
                            JSONArray jarr = jobj.optJSONArray("teams");
                            if (jarr != null && jarr.length() > 0) {
                                MatchTeamNew mtn = new MatchTeamNew();
                                JSONObject jo = jarr.getJSONObject(i);
                                if (jo != null) {
                                    mtn.id = jo.optInt("id");
                                    mtn.score = (short) jo.optInt(FirebaseAnalytics.Param.SCORE);
                                    teamList.add(mtn);
                                }
                            }
                            matchInfoNew.teams = teamList;
                            this.mMatchInfo.add(matchInfoNew);
                        }
                    }
                }
            }
            if (jsonObject.has("team_info")) {
                this.mTeamInfo = new LinkedList();
                JSONArray jsonArray2 = jsonObject.optJSONArray("team_info");
                if (jsonArray2 != null && jsonArray2.length() > 0) {
                    for (int i2 = 0; i2 < jsonArray2.length(); i2++) {
                        TeamInfoNew tin = new TeamInfoNew();
                        JSONObject job = jsonArray2.getJSONObject(i2);
                        if (job != null) {
                            tin.id = job.optInt("id");
                            tin.name = job.optString("name");
                            tin.logo = job.optString("logo");
                        }
                        this.mTeamInfo.add(tin);
                    }
                }
            }
            if (!jsonObject.has("sub_info")) {
                return false;
            }
            this.mSubInfo = new LinkedList();
            JSONArray jsonArray3 = jsonObject.optJSONArray("sub_info");
            if (jsonArray3 != null && jsonArray3.length() > 0) {
                for (int i3 = 0; i3 < jsonArray3.length(); i3++) {
                    this.mSubInfo.add(Integer.valueOf(((Integer) jsonArray3.get(i3)).intValue()));
                }
            }
            return MatchInfoModel.getInstance().updateSubData(this.mSubInfo);
        } catch (Exception e) {
            Logger.d(TAG, "parse exception:" + e);
            return false;
        }
    }
}
