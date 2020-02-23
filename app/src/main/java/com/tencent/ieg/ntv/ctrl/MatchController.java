package com.tencent.ieg.ntv.ctrl;

import android.app.Activity;
import android.content.Context;
import com.tencent.ieg.ntv.TVShowManager;
import com.tencent.ieg.ntv.event.Event;
import com.tencent.ieg.ntv.event.EventGetInfoDataUI;
import com.tencent.ieg.ntv.event.EventManager;
import com.tencent.ieg.ntv.event.EventMatchReserveUI;
import com.tencent.ieg.ntv.event.EventReserveResultUI;
import com.tencent.ieg.ntv.event.IEventListener;
import com.tencent.ieg.ntv.event.net.EventGetInfoData;
import com.tencent.ieg.ntv.event.net.EventMatchInfoData;
import com.tencent.ieg.ntv.event.net.EventReserveResult;
import com.tencent.ieg.ntv.model.DataForReport;
import com.tencent.ieg.ntv.model.MatchInfoModel;
import com.tencent.ieg.ntv.model.NTVDefine;
import com.tencent.ieg.ntv.network.NetworkModule;
import com.tencent.ieg.ntv.utils.Logger;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MatchController {
    /* access modifiers changed from: private */
    public static final String TAG = MatchController.class.getSimpleName();
    private static MatchController sInstance;
    /* access modifiers changed from: private */
    public Context mContext;
    private long mMiniGap = 1800000;
    private IEventListener onGetInfoResultHandler = new IEventListener() {
        public void onEvent(int eventId, Event event) {
            EventGetInfoData evtData = (EventGetInfoData) event;
            Logger.d(MatchController.TAG, "logReserve: onGetInfoResultHandler , result:" + evtData.mResult);
            if (evtData.mSubInfo != null) {
                final EventGetInfoDataUI evt = new EventGetInfoDataUI();
                evt.mSubInfo = evtData.mSubInfo;
                evt.mTagText = TVShowManager.getInstance().getI18NText(NTVDefine.KEY_MATCH_STATUS_RESERVED);
                evt.mStatus = EventMatchInfoData.MatchStatus.RESERVED;
                ((Activity) MatchController.this.mContext).runOnUiThread(new Runnable() {
                    public void run() {
                        EventManager.getInstance().post(NTVDefine.EVT_MATCH_GET_INFO_RESULT_SUB, evt);
                    }
                });
                MatchController.this.initTimerCheck();
            }
        }
    };
    private IEventListener onReserveClickHandler = new IEventListener() {
        public void onEvent(int eventId, Event event) {
            EventMatchReserveUI evt = (EventMatchReserveUI) event;
            if (evt != null) {
                Logger.d(MatchController.TAG, "logReserve: onReserveClickHandler - evt.optType:" + evt.optType + ", evt.matchId:" + evt.matchId);
                if (evt.optType == EventMatchReserveUI.RESERVE_TYPE_RESERVE || evt.optType == EventMatchReserveUI.RESERVE_TYPE_CANCEL) {
                    NetworkModule.getInstance().sendRequestSubscribeMatch(evt.optType, evt.matchId);
                } else if (evt.optType == EventMatchReserveUI.RESERVE_TYPE_REPLAY) {
                    DataForReport.getInstance().logMatchReplayEvent(evt.matchId);
                    ReplayCtrl.getInstance().onShow(true, evt.replayurl);
                }
            }
        }
    };
    private IEventListener onReserveResultHandler = new IEventListener() {
        public void onEvent(int eventId, Event event) {
            String tagText;
            int status;
            EventReserveResult evt = (EventReserveResult) event;
            Logger.d(MatchController.TAG, "logReserve: onReserveResultHandler - evt.optType:" + evt.mOptType + ", evt.mMatchId:" + evt.mMatchId + ", evt.mResult:" + evt.mResult);
            if (evt != null && evt.mResult == 0) {
                if (evt.mOptType == EventMatchReserveUI.RESERVE_TYPE_RESERVE) {
                    tagText = TVShowManager.getInstance().getI18NText(NTVDefine.KEY_MATCH_STATUS_RESERVED);
                    status = EventMatchInfoData.MatchStatus.RESERVED;
                } else if (evt.mOptType == EventMatchReserveUI.RESERVE_TYPE_CANCEL) {
                    tagText = TVShowManager.getInstance().getI18NText(NTVDefine.KEY_MATCH_STATUS_RESERVABLE);
                    status = EventMatchInfoData.MatchStatus.RESERVABLE;
                } else {
                    return;
                }
                final EventReserveResultUI evtUI = new EventReserveResultUI(evt.mResult, evt.mOptType, evt.mMatchId, tagText, status);
                ((Activity) MatchController.this.mContext).runOnUiThread(new Runnable() {
                    public void run() {
                        EventManager.getInstance().post(NTVDefine.EVT_MATCH_RESERVE_RESULT_UPDATE_UI, evtUI);
                    }
                });
            }
        }
    };
    private Timer updateTimer;

    public static class MatchInfoType {
        public static short TYPE_MATCH = 1;
        public static short TYPE_SUBSCRIBE = 4;
        public static short TYPE_TEAM = 2;
    }

    public static MatchController getInstance() {
        if (sInstance == null) {
            sInstance = new MatchController();
        }
        return sInstance;
    }

    public void destroy() {
        EventManager.getInstance().unregister(NTVDefine.EVT_MATCH_RESERVE_CLICK_UI, this.onReserveClickHandler);
        EventManager.getInstance().unregister(NTVDefine.EVT_MATCH_RESERVE_RESULT, this.onReserveResultHandler);
        EventManager.getInstance().unregister(NTVDefine.EVT_MATCH_GET_INFO_RESULT, this.onGetInfoResultHandler);
    }

    public void init(Context context) {
        this.mContext = context;
        EventManager.getInstance().register(NTVDefine.EVT_MATCH_RESERVE_CLICK_UI, this.onReserveClickHandler);
        EventManager.getInstance().register(NTVDefine.EVT_MATCH_RESERVE_RESULT, this.onReserveResultHandler);
        EventManager.getInstance().register(NTVDefine.EVT_MATCH_GET_INFO_RESULT, this.onGetInfoResultHandler);
    }

    /* access modifiers changed from: private */
    public void initTimerCheck() {
        if (this.updateTimer != null) {
            this.updateTimer.cancel();
        }
        List<EventMatchInfoData.MatchInfo> matchInfoList = MatchInfoModel.getInstance().getMatchInfoList();
        if (matchInfoList != null && matchInfoList.size() > 0) {
            long curMiniGap = 0;
            for (int i = 0; i < matchInfoList.size(); i++) {
                EventMatchInfoData.MatchInfo info = matchInfoList.get(i);
                long curMilSecond = System.currentTimeMillis();
                if (info.status == EventMatchInfoData.MatchStatus.RESERVABLE && curMilSecond < info.time * 1000) {
                    long gap = (info.time * 1000) - curMilSecond;
                    if (curMiniGap == 0 || gap < curMiniGap) {
                        curMiniGap = gap;
                    }
                }
            }
            if (curMiniGap > 0 && curMiniGap <= this.mMiniGap) {
                long period = curMiniGap > 10000 ? 10000 : 1000;
                this.updateTimer = new Timer();
                this.updateTimer.schedule(new TimerTask() {
                    public void run() {
                        MatchController.this.triggerTimerCheck();
                    }
                }, 1000, period);
            }
        }
    }

    /* access modifiers changed from: private */
    public void triggerTimerCheck() {
        List<EventMatchInfoData.MatchInfo> matchInfoList = MatchInfoModel.getInstance().getMatchInfoList();
        if (matchInfoList != null && matchInfoList.size() > 0) {
            int i = 0;
            while (i < matchInfoList.size()) {
                EventMatchInfoData.MatchInfo info = matchInfoList.get(i);
                long curMilSecond = System.currentTimeMillis();
                if (info.status != EventMatchInfoData.MatchStatus.RESERVABLE || curMilSecond < info.time * 1000) {
                    i++;
                } else {
                    matchInfoList.get(i).status = EventMatchInfoData.MatchStatus.NOTSHOW;
                    final EventReserveResultUI evtUI = new EventReserveResultUI(0, 0, info.matchid, "", EventMatchInfoData.MatchStatus.NOTSHOW);
                    ((Activity) this.mContext).runOnUiThread(new Runnable() {
                        public void run() {
                            EventManager.getInstance().post(NTVDefine.EVT_MATCH_RESERVE_RESULT_UPDATE_UI, evtUI);
                        }
                    });
                    initTimerCheck();
                    return;
                }
            }
        } else if (this.updateTimer != null) {
            this.updateTimer.cancel();
        }
    }
}
