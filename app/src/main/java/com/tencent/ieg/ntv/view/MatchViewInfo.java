package com.tencent.ieg.ntv.view;

import com.tencent.ieg.ntv.event.net.EventMatchInfoData;
import java.util.List;

public class MatchViewInfo {
    public String date;
    public String desc;
    public int iMatchId;
    public int iStatus;
    public boolean isFinished;
    public boolean isShowDate = true;
    public String replayUrl;
    public List<EventMatchInfoData.TeamInfo> teams;
    public String time;
}
