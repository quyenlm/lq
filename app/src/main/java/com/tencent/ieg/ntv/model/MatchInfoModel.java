package com.tencent.ieg.ntv.model;

import com.tencent.ieg.ntv.event.EventMatchReserveUI;
import com.tencent.ieg.ntv.event.net.EventMatchInfoData;
import java.util.List;
import org.json.JSONArray;

public class MatchInfoModel {
    private static MatchInfoModel sInstance;
    private JSONArray mMatchInfoArray = new JSONArray();
    private boolean mUpdatabled;
    private List<EventMatchInfoData.MatchInfo> matchInfoList = null;

    public static MatchInfoModel getInstance() {
        if (sInstance == null) {
            sInstance = new MatchInfoModel();
        }
        return sInstance;
    }

    private MatchInfoModel() {
    }

    public void setMatchInfoArray(JSONArray arr) {
        this.mMatchInfoArray = arr;
        if (arr != null && arr.length() > 0) {
            EventMatchInfoData matchInfoData = new EventMatchInfoData();
            matchInfoData.parse(arr);
            this.matchInfoList = matchInfoData.matchInfoList;
        }
    }

    public JSONArray getMatchInfoArray() {
        return this.mMatchInfoArray;
    }

    public List<EventMatchInfoData.MatchInfo> getMatchInfoList() {
        return this.matchInfoList;
    }

    public void setUpdatabled(boolean updatabled) {
        this.mUpdatabled = updatabled;
    }

    public boolean getUpdatable() {
        return this.mUpdatabled;
    }

    public boolean updateSubData(List<Integer> subInfo) {
        boolean hasUpdate = false;
        if (subInfo != null && subInfo.size() > 0 && this.matchInfoList != null && this.matchInfoList.size() > 0) {
            for (int i = 0; i < subInfo.size(); i++) {
                for (int j = 0; j < this.matchInfoList.size(); j++) {
                    EventMatchInfoData.MatchInfo matchInfo = this.matchInfoList.get(j);
                    if (subInfo.get(i).intValue() == matchInfo.matchid) {
                        matchInfo.status = EventMatchInfoData.MatchStatus.RESERVED;
                        hasUpdate = true;
                    }
                }
            }
        }
        return hasUpdate;
    }

    public boolean updateSingleMatch(short result, short optType, int matchId) {
        boolean hasUpdate = false;
        if (result == 0 && this.matchInfoList != null && this.matchInfoList.size() > 0) {
            for (int i = 0; i < this.matchInfoList.size(); i++) {
                if (this.matchInfoList.get(i).matchid == matchId) {
                    if (optType == EventMatchReserveUI.RESERVE_TYPE_RESERVE) {
                        this.matchInfoList.get(i).status = EventMatchInfoData.MatchStatus.RESERVED;
                        hasUpdate = true;
                    } else if (optType == EventMatchReserveUI.RESERVE_TYPE_CANCEL) {
                        this.matchInfoList.get(i).status = EventMatchInfoData.MatchStatus.RESERVABLE;
                        hasUpdate = true;
                    }
                }
            }
        }
        return hasUpdate;
    }
}
