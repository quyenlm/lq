package com.tencent.ieg.ntv.view;

import android.annotation.TargetApi;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ListView;
import android.widget.TextView;
import com.tencent.ieg.ntv.R;
import com.tencent.ieg.ntv.event.Event;
import com.tencent.ieg.ntv.event.EventManager;
import com.tencent.ieg.ntv.event.IEventListener;
import com.tencent.ieg.ntv.event.net.EventGetInfoData;
import com.tencent.ieg.ntv.event.net.EventMatchInfoData;
import com.tencent.ieg.ntv.model.MatchInfoModel;
import com.tencent.ieg.ntv.model.NTVDefine;
import com.tencent.ieg.ntv.utils.Logger;
import com.tencent.ieg.ntv.utils.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MatchScheduleFragment extends BaseContentFragment {
    private static final String TAG = MatchScheduleFragment.class.getSimpleName();
    private ViewGroup mDateBar;
    /* access modifiers changed from: private */
    public TextView mDateText;
    /* access modifiers changed from: private */
    public MatchListAdapter matchListAdapter;
    private IEventListener onGetInfoResultHandlerAdapter = new IEventListener() {
        public void onEvent(int eventId, Event event) {
            EventGetInfoData evtData = (EventGetInfoData) event;
            MatchScheduleFragment.log("logReserve: onGetInfoResultHandlerAdapter, result=" + evtData.mResult);
            if (evtData.mResult == 0) {
                List<EventMatchInfoData.MatchInfo> matchInfoList = MatchInfoModel.getInstance().getMatchInfoList();
                Collections.sort(matchInfoList, new ComparatorMatchInfo());
                ArrayList<MatchViewInfo> matchViewInfoList = MatchScheduleFragment.this.handleData(matchInfoList);
                MatchScheduleFragment.this.matchListAdapter.clear();
                MatchScheduleFragment.this.matchListAdapter.addAll(matchViewInfoList);
                MatchScheduleFragment.this.matchListAdapter.notifyDataSetChanged();
            }
        }
    };
    private IEventListener updateHandler = new IEventListener() {
        public void onEvent(int eventId, Event event) {
            MatchScheduleFragment.this.onMatchInfoList(((EventMatchInfoData) event).matchInfoList);
        }
    };

    /* access modifiers changed from: private */
    public static void log(String msg) {
        Logger.d(TAG, msg);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    public void onShow() {
        super.onShow();
    }

    public void onHidden() {
        super.onHidden();
    }

    public void destroy() {
        EventManager.getInstance().unregister(5003, this.updateHandler);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        log("onViewCreated");
        EventManager.getInstance().register(5003, this.updateHandler);
        EventManager.getInstance().register(NTVDefine.EVT_MATCH_GET_INFO_RESULT_UPDATE_ADAPTER, this.onGetInfoResultHandlerAdapter);
        onMatchInfoList(MatchInfoModel.getInstance().getMatchInfoList());
        this.mDateBar = (ViewGroup) view.findViewById(R.id.header_date_bar);
        this.mDateText = (TextView) view.findViewById(R.id.header_date_text);
    }

    /* access modifiers changed from: private */
    @TargetApi(23)
    public void onMatchInfoList(List<EventMatchInfoData.MatchInfo> matchInfoList) {
        Logger.d(TAG, "onMatchInfoList");
        if (matchInfoList == null) {
            Logger.d(TAG, "onMatchInfoList is null.");
            return;
        }
        Collections.sort(matchInfoList, new ComparatorMatchInfo());
        int selectionIndex = getSelectionMatch(matchInfoList);
        ArrayList<MatchViewInfo> matchViewInfoList = handleData(matchInfoList);
        if (this != null) {
            final ListView listView = (ListView) getView().findViewById(R.id.match_list_view);
            listView.setTranscriptMode(0);
            listView.setSelector(new ColorDrawable(0));
            this.matchListAdapter = new MatchListAdapter(getContext(), matchViewInfoList);
            listView.setAdapter(this.matchListAdapter);
            listView.setSelection(selectionIndex);
            try {
                listView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    public void onScrollChanged() {
                        MatchView mv = (MatchView) listView.getChildAt(0);
                        if (mv != null) {
                            MatchScheduleFragment.this.mDateText.setText((String) mv.mDateText.getText());
                        }
                    }
                });
            } catch (Exception e) {
                Logger.e(TAG, "onMatchInfoList exception:" + e.toString());
                if (this.mDateBar != null) {
                    this.mDateBar.setVisibility(8);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public ArrayList<MatchViewInfo> handleData(List<EventMatchInfoData.MatchInfo> matchInfoList) {
        ArrayList<MatchViewInfo> matchViewInfoList = new ArrayList<>();
        if (matchInfoList != null) {
            log("matchInfoList size:" + matchInfoList.size());
            for (int i = 0; i < matchInfoList.size(); i++) {
                MatchViewInfo matchViewInfo = new MatchViewInfo();
                matchViewInfo.iMatchId = matchInfoList.get(i).matchid;
                matchViewInfo.date = Util.getDateString(matchInfoList.get(i).time);
                matchViewInfo.desc = matchInfoList.get(i).desc;
                matchViewInfo.time = Util.getTimeString(matchInfoList.get(i).time);
                matchViewInfo.teams = matchInfoList.get(i).teams;
                matchViewInfo.iStatus = matchInfoList.get(i).status;
                matchViewInfo.isFinished = matchInfoList.get(i).isFinished;
                matchViewInfo.replayUrl = matchInfoList.get(i).replayurl;
                if (i == 0) {
                    matchViewInfo.isShowDate = true;
                } else {
                    matchViewInfo.isShowDate = !matchViewInfo.date.equals(matchViewInfoList.get(matchViewInfoList.size() + -1).date);
                }
                log("logReserve: onMatchInfoList - iMatchId:" + matchInfoList.get(i).matchid + ", status:" + matchInfoList.get(i).status);
                matchViewInfoList.add(matchViewInfo);
            }
        } else {
            for (int i2 = 0; i2 < 5; i2++) {
                MatchViewInfo matchViewInfo2 = new MatchViewInfo();
                matchViewInfo2.desc = "test match" + i2;
                matchViewInfoList.add(matchViewInfo2);
            }
        }
        return matchViewInfoList;
    }

    private static class ComparatorMatchInfo implements Comparator {
        private ComparatorMatchInfo() {
        }

        public int compare(Object arg0, Object arg1) {
            int flag = (int) (((EventMatchInfoData.MatchInfo) arg0).time - ((EventMatchInfoData.MatchInfo) arg1).time);
            if (flag == 0) {
            }
            return flag;
        }
    }

    private static int getSelectionMatch(List<EventMatchInfoData.MatchInfo> matchInfoList) {
        int newMark = 0;
        if (matchInfoList != null) {
            long curSeconds = new Date().getTime();
            if (matchInfoList.size() > 1) {
                newMark = 0;
                for (int i = 0; i < matchInfoList.size(); i++) {
                    if (curSeconds > matchInfoList.get(i).time * 1000) {
                        newMark = i;
                    }
                }
            }
        }
        return newMark;
    }
}
