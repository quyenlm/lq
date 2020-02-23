package com.tencent.ieg.ntv.model;

import com.tencent.ieg.ntv.network.HeartBeatInfo;
import com.tencent.ieg.ntv.utils.Logger;
import java.util.Date;
import java.util.HashSet;

public class DataForReport {
    public static final String EVENT_ID_LIVE_END = "1";
    public static final String EVENT_ID_LIVE_START = "0";
    public static final String EVENT_ID_MATCH_REPLAY = "2";
    private static final String TAG = DataForReport.class.getSimpleName();
    private static DataForReport ourInstance = null;
    private int MAX_LENGTH = 924;
    private int curLength = 0;
    private int lastTabIdx = -1;
    private long local_timestamp;
    private HeartBeatInfo.HeartBeatLogInfo logInfo;
    private int server_timestamp;
    private HashSet<String> webviewTabTexts = new HashSet<>();

    public void logLivePlayerTabEvent(int idx) {
        if (this.lastTabIdx != idx && this.curLength < this.MAX_LENGTH) {
            if (idx == 0) {
                HeartBeatInfo.HeartBeatLogInfo.Ainfo ainfo = new HeartBeatInfo.HeartBeatLogInfo.Ainfo();
                ainfo.id = "0";
                ainfo.value = getCurSecond();
                getLogInfo().add(ainfo);
            } else if (this.lastTabIdx == 0 && idx != 0) {
                HeartBeatInfo.HeartBeatLogInfo.Ainfo ainfo2 = new HeartBeatInfo.HeartBeatLogInfo.Ainfo();
                ainfo2.id = "1";
                ainfo2.value = getCurSecond();
                getLogInfo().add(ainfo2);
            }
            this.curLength = getLogInfo().toJSONString().length();
            Logger.d(TAG, "event log info length: " + this.curLength);
            this.lastTabIdx = idx;
        }
    }

    public void logWebViewTabEvent(String tabText) {
        if (this.curLength < this.MAX_LENGTH && this.webviewTabTexts != null && !this.webviewTabTexts.contains(tabText)) {
            HeartBeatInfo.HeartBeatLogInfo.Ainfo ainfo = new HeartBeatInfo.HeartBeatLogInfo.Ainfo();
            ainfo.id = tabText;
            ainfo.value = getCurSecond();
            getLogInfo().add(ainfo);
            this.webviewTabTexts.add(tabText);
            this.curLength = getLogInfo().toJSONString().length();
            Logger.d(TAG, "event log info length: " + this.curLength);
        }
    }

    public void logMatchReplayEvent(int matchId) {
        if (this.curLength < this.MAX_LENGTH) {
            HeartBeatInfo.HeartBeatLogInfo.Ainfo ainfo = new HeartBeatInfo.HeartBeatLogInfo.Ainfo();
            ainfo.id = "2";
            ainfo.value = matchId;
            getLogInfo().add(ainfo);
            this.curLength = getLogInfo().toJSONString().length();
            Logger.d(TAG, "event log info length: " + this.curLength);
        }
    }

    private int getCurSecond() {
        return (int) (((new Date().getTime() - this.local_timestamp) / 1000) + ((long) this.server_timestamp));
    }

    public HeartBeatInfo.HeartBeatLogInfo getLogInfo() {
        if (this.logInfo == null) {
            this.logInfo = new HeartBeatInfo.HeartBeatLogInfo();
        }
        return this.logInfo;
    }

    public void cleanLogInfo() {
        this.curLength = 0;
        if (this.logInfo != null) {
            this.logInfo = null;
        }
        this.webviewTabTexts = new HashSet<>();
    }

    public void setServerTimestamp(int timestamp) {
        this.server_timestamp = timestamp;
        this.local_timestamp = new Date().getTime();
    }

    public static DataForReport getInstance() {
        if (ourInstance == null) {
            ourInstance = new DataForReport();
        }
        return ourInstance;
    }

    private DataForReport() {
    }

    public void destroy() {
        cleanLogInfo();
        ourInstance = null;
    }
}
