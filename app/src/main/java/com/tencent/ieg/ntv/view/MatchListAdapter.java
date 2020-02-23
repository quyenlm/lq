package com.tencent.ieg.ntv.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.tencent.ieg.ntv.TVShowManager;
import com.tencent.ieg.ntv.event.net.EventMatchInfoData;
import com.tencent.ieg.ntv.model.NTVDefine;
import com.tencent.ieg.ntv.utils.Logger;
import java.util.ArrayList;

public class MatchListAdapter extends ArrayAdapter<MatchViewInfo> {
    private static final String TAG = MatchListAdapter.class.getSimpleName();

    public MatchListAdapter(@NonNull Context context, ArrayList<MatchViewInfo> matches) {
        super(context, 0, matches);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new MatchView(getContext());
        }
        MatchViewInfo matchViewInfo = (MatchViewInfo) getItem(position);
        MatchView matchView = (MatchView) convertView;
        matchView.setMatchId(matchViewInfo.iMatchId);
        matchView.setMatchDesc(matchViewInfo.desc);
        matchView.setMatchTime(matchViewInfo.time);
        matchView.setTeamIcon(matchViewInfo.teams.get(0).logoUrl, matchViewInfo.teams.get(1).logoUrl);
        matchView.setScore(matchViewInfo.teams.get(0).score, matchViewInfo.teams.get(1).score, matchViewInfo.isFinished);
        matchView.setTeamName(matchViewInfo.teams.get(0).name, matchViewInfo.teams.get(1).name);
        matchView.setsReplayUrl(matchViewInfo.replayUrl);
        Logger.d(TAG, "logReserve: iMatchId:" + matchViewInfo.iMatchId + ",iStatus:" + matchViewInfo.iStatus);
        if (matchViewInfo.iStatus == EventMatchInfoData.MatchStatus.FINISH) {
            matchView.setTagInfo(TVShowManager.getInstance().getI18NText(NTVDefine.KEY_MATCH_STATUS_FINISH), EventMatchInfoData.MatchStatus.FINISH);
        } else if (matchViewInfo.iStatus == EventMatchInfoData.MatchStatus.RESERVABLE) {
            matchView.setTagInfo(TVShowManager.getInstance().getI18NText(NTVDefine.KEY_MATCH_STATUS_RESERVABLE), EventMatchInfoData.MatchStatus.RESERVABLE);
        } else if (matchViewInfo.iStatus == EventMatchInfoData.MatchStatus.RESERVED) {
            matchView.setTagInfo(TVShowManager.getInstance().getI18NText(NTVDefine.KEY_MATCH_STATUS_RESERVED), EventMatchInfoData.MatchStatus.RESERVED);
        } else if (matchViewInfo.iStatus == EventMatchInfoData.MatchStatus.REPLAY) {
            matchView.setTagInfo(TVShowManager.getInstance().getI18NText(NTVDefine.KEY_MATCH_STATUS_REPLAY), EventMatchInfoData.MatchStatus.REPLAY);
        } else {
            matchView.setTagInfo("", EventMatchInfoData.MatchStatus.NOTSHOW);
        }
        matchView.setDate(matchViewInfo.date, matchViewInfo.isShowDate);
        return convertView;
    }
}
