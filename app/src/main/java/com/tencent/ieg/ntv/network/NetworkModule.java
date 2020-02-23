package com.tencent.ieg.ntv.network;

import android.os.Build;
import android.os.Handler;
import com.tencent.ieg.ntv.TVShowManager;
import com.tencent.ieg.ntv.ctrl.MatchController;
import com.tencent.ieg.ntv.event.Event;
import com.tencent.ieg.ntv.event.EventDeliverRewardResult;
import com.tencent.ieg.ntv.event.EventManager;
import com.tencent.ieg.ntv.event.net.EventForcePopUpData;
import com.tencent.ieg.ntv.event.net.EventGeneralWordData;
import com.tencent.ieg.ntv.event.net.EventGetInfoData;
import com.tencent.ieg.ntv.event.net.EventMatchInfoData;
import com.tencent.ieg.ntv.event.net.EventPageInfoData;
import com.tencent.ieg.ntv.event.net.EventPlayInfoData;
import com.tencent.ieg.ntv.event.net.EventRedDotInfoData;
import com.tencent.ieg.ntv.event.net.EventReserveResult;
import com.tencent.ieg.ntv.event.net.EventViewCount;
import com.tencent.ieg.ntv.model.DataForReport;
import com.tencent.ieg.ntv.model.MatchInfoModel;
import com.tencent.ieg.ntv.model.NTVDefine;
import com.tencent.ieg.ntv.utils.Logger;
import com.tencent.ieg.ntv.utils.Util;

public class NetworkModule {
    /* access modifiers changed from: private */
    public static final String TAG = NetworkModule.class.getSimpleName();
    private static NetworkModule sInstance;
    private boolean disConnected = false;
    private BaseInfo mBaseInfo;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler();
    /* access modifiers changed from: private */
    public boolean mHeartbeatStarted = false;
    /* access modifiers changed from: private */
    public String mIp;
    private int mPort;
    /* access modifiers changed from: private */
    public boolean mReconnecting = false;
    private RecvThread mRecvThread = null;
    /* access modifiers changed from: private */
    public boolean mRecvThreadRunning = false;
    private RewardInfo mRewardInfo;
    private int mUin;

    private static native int createConnection(int i, int i2, String str);

    private static native void destroyConnection();

    /* access modifiers changed from: private */
    public static native int reconnect();

    /* access modifiers changed from: private */
    public static native int recvServerMsg();

    private static native void requestBaseInfo(String str, String str2, String str3, String str4, String str5, int i, int i2, String str6, short s);

    private static native void requestDeliverReward(String str, short s, short s2, short s3);

    private static native void requestGetInfo(short s);

    private static native void requestHeartBeat(String str, String str2);

    private static native void requestRewardInfo(String str);

    private static native void requestSubscribeMatch(short s, int i);

    static {
        System.loadLibrary("ntv_network");
    }

    /* access modifiers changed from: private */
    public static void log(String msg) {
        Logger.d(TAG, msg);
    }

    private NetworkModule() {
    }

    public static NetworkModule getInstance() {
        if (sInstance == null) {
            sInstance = new NetworkModule();
        }
        return sInstance;
    }

    private static long getUin(String openId) {
        return Long.valueOf(Long.parseLong(openId, 10)).longValue() >> 26;
    }

    private static int safeLongToInt(long value) {
        if (value >= -2147483648L && value <= 2147483647L) {
            return (int) value;
        }
        throw new RuntimeException("long cast to int failed value:" + value);
    }

    public void init() {
        String ntvSvr = TVShowManager.getInstance().getI18NText(NTVDefine.KEY_NTV_SVR);
        if (ntvSvr == null || ntvSvr.length() <= 0 || ntvSvr.indexOf(":") < 0) {
            ntvSvr = Util.getString(NTVDefine.KCONF_STRING_SERVER_CFG);
        }
        if (ntvSvr == null || ntvSvr.length() <= 0 || ntvSvr.indexOf(":") < 0) {
            log("init, invalid ntv server, stop connecting.");
            return;
        }
        String[] serverConfig = ntvSvr.split(":");
        this.mIp = serverConfig[0];
        this.mPort = Integer.parseInt(serverConfig[1]);
        this.mUin = safeLongToInt(getUin(Util.getMConfig(NTVDefine.KEY_MCONF_GAME_OPENID)));
        log("init mIp:" + this.mIp + ", mPort:" + this.mPort + " ,mUin:" + this.mUin);
        Util.getIpByHost(this.mIp, new Util.DNSListener() {
            public void onComplete(String ip) {
                NetworkModule.log("getIpByHost onComplete ip:" + ip);
                if (ip != null) {
                    String unused = NetworkModule.this.mIp = ip;
                    NetworkModule.this.initConnection();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void initConnection() {
        if (createConnection(this.mUin, this.mPort, this.mIp) == 0) {
            this.mRecvThreadRunning = true;
            this.mRecvThread = new RecvThread();
            this.mRecvThread.start();
            sendBaseInfoRequest();
        }
    }

    public void destroy() {
        this.mRecvThreadRunning = false;
        try {
            if (this.mRecvThread != null) {
                this.mRecvThread.join();
            }
        } catch (InterruptedException e) {
            Logger.w(TAG, (Exception) e);
        }
        destroyConnection();
    }

    private class RecvThread extends Thread {
        private static final int HEARTBEAT_INTERVAL = 60000;
        private static final int INTERVAL = 100;

        private RecvThread() {
        }

        public void run() {
            int heartbeatCounter = 0;
            while (NetworkModule.this.mRecvThreadRunning) {
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    Logger.w(NetworkModule.TAG, (Exception) e);
                }
                if (NetworkModule.this.mHeartbeatStarted && (heartbeatCounter = heartbeatCounter + 100) >= 60000) {
                    NetworkModule.this.sendHeartBeatRequest();
                    heartbeatCounter = 0;
                }
                recv();
            }
        }

        private void recv() {
            NetworkModule.this.mHandler.post(new Runnable() {
                public void run() {
                    if (NetworkModule.this.mRecvThreadRunning) {
                        int ret = NetworkModule.recvServerMsg();
                        if (ret == 0 || ret == 1201) {
                            boolean unused = NetworkModule.this.mReconnecting = false;
                        } else {
                            boolean unused2 = NetworkModule.this.mReconnecting = true;
                        }
                        if (NetworkModule.this.mReconnecting) {
                            NetworkModule.log("reconnecting...");
                            NetworkModule.log("reconnect ret:" + NetworkModule.reconnect());
                        }
                    }
                }
            });
        }
    }

    public void onNetWorkChange() {
        Util.NetworkType netType = Util.getNetworkType();
        log("onNetWorkChange - netType=" + netType);
        if (netType != Util.NetworkType.WIFI && netType != Util.NetworkType.MOBILE_2G && netType != Util.NetworkType.MOBILE_3G && netType != Util.NetworkType.MOBILE_4G) {
            this.disConnected = true;
        } else if (this.disConnected) {
            sendBaseInfoRequest();
        }
    }

    public BaseInfo getBaseInfo() {
        return this.mBaseInfo;
    }

    private void sendBaseInfoRequest() {
        log("sendBaseInfoRequest");
        String strZoneid = Util.getMConfig(NTVDefine.KEY_MCONF_GAME_PARTITIONID);
        if (strZoneid.isEmpty()) {
            strZoneid = "0";
        }
        String deviceModel = Build.MODEL;
        String deviceSystem = String.valueOf(Build.VERSION.SDK_INT);
        short gameid = 0;
        String sGameid = Util.getMConfig(NTVDefine.KEY_MCONF_GAME_GAMEID);
        try {
            gameid = Short.parseShort(sGameid);
        } catch (Exception e) {
            log("sendBaseInfoRequest parse string gameid to short exception, sgameid:" + sGameid);
        }
        requestBaseInfo(Util.getMConfig(NTVDefine.KEY_MCONF_GAME_LANGUAGE), Util.getMConfig(NTVDefine.KEY_MCONF_GAME_OPENID), Util.getMConfig(NTVDefine.KEY_MCONF_GAME_TOKEN), deviceSystem, deviceModel, 1, Integer.parseInt(strZoneid), Util.getMConfig(NTVDefine.KEY_MCONF_GAME_FIREBASETOKEN), gameid);
    }

    public void sendHeartBeatRequest() {
        String logInfo = DataForReport.getInstance().getLogInfo().toJSONString();
        log("sendHeartBeatRequest, lang=" + Util.getMConfig(NTVDefine.KEY_MCONF_GAME_LANGUAGE) + ", loginfo=" + logInfo);
        requestHeartBeat(Util.getMConfig(NTVDefine.KEY_MCONF_GAME_LANGUAGE), logInfo);
        DataForReport.getInstance().cleanLogInfo();
    }

    private void onBaseInfoResponse(int result, int timestamp, String baseInfoStr) {
        log("onBaseInfoResponse in.");
        if (this.disConnected) {
            this.disConnected = false;
            return;
        }
        log("onBaseInfoResponse continue.");
        if (result == 0) {
            this.mHeartbeatStarted = true;
            sendHeartBeatRequest();
            sendHolderRewardInfoRequest();
            sendMatchInfoRequest(MatchController.MatchInfoType.TYPE_SUBSCRIBE);
        }
        DataForReport.getInstance().setServerTimestamp(timestamp);
        Util.setServerTimestamp(timestamp);
        if (this.mBaseInfo == null) {
            this.mBaseInfo = new BaseInfo();
        }
        if (this.mBaseInfo.parse(baseInfoStr)) {
            postBaseInfo();
        }
    }

    private void onHeartBeatResponse(int result, int num, String baseInfoStr) {
        if (result == 1) {
            sendBaseInfoRequest();
            return;
        }
        EventViewCount eventViewCount = new EventViewCount();
        eventViewCount.viewCount = num;
        EventManager.getInstance().post(5006, eventViewCount);
        if (this.mBaseInfo == null) {
            this.mBaseInfo = new BaseInfo();
        }
        if (this.mBaseInfo.parse(baseInfoStr)) {
            postBaseInfo();
        }
    }

    private void postBaseInfo() {
        log("postBaseInfo");
        if (this.mBaseInfo.isPageInfoUpdated()) {
            EventPageInfoData event = new EventPageInfoData();
            if (event.parse(this.mBaseInfo.getPageInfoArray())) {
                EventManager.getInstance().post(5001, event);
            }
            this.mBaseInfo.setPageInfoUpdated(false);
        }
        if (this.mBaseInfo.isGeneralWordUpdated()) {
            EventGeneralWordData event2 = new EventGeneralWordData();
            if (event2.parse(this.mBaseInfo.getGeneralWordArray())) {
                EventManager.getInstance().post(5002, event2);
            }
            this.mBaseInfo.setGeneralWordUpdated(false);
        }
        if (MatchInfoModel.getInstance().getUpdatable()) {
            EventMatchInfoData event3 = new EventMatchInfoData();
            if (MatchInfoModel.getInstance().getMatchInfoList() != null) {
                event3.matchInfoList = MatchInfoModel.getInstance().getMatchInfoList();
                EventManager.getInstance().post(5003, event3);
                MatchInfoModel.getInstance().setUpdatabled(false);
            }
        }
        EventPlayInfoData playInfoData = null;
        if (this.mBaseInfo.isPlayInfoUpdated()) {
            EventPlayInfoData event4 = new EventPlayInfoData();
            if (event4.parse(this.mBaseInfo.getPlayInfoObject())) {
                EventManager.getInstance().post(5005, event4);
                playInfoData = event4;
            }
            this.mBaseInfo.setPlayInfoUpdated(false);
        }
        if (this.mBaseInfo.isForcePopUpUpdated()) {
            EventForcePopUpData event5 = new EventForcePopUpData();
            event5.playInfo = playInfoData;
            if (event5.parse(this.mBaseInfo.getForcePopUpArray())) {
                EventManager.getInstance().post(5004, event5);
            }
            this.mBaseInfo.setForcePopUpUpdated(false);
        }
        if (this.mBaseInfo.isRedDotInfoUpdated()) {
            EventRedDotInfoData event6 = new EventRedDotInfoData();
            if (event6.parse(this.mBaseInfo.getRedDotInfoArray())) {
                EventManager.getInstance().post(5007, event6);
            }
            this.mBaseInfo.setRedDotInfoUpdated(false);
        }
    }

    public void sendHolderRewardInfoRequest() {
        log("sendHolderBonusRequest : curMSecond=" + Util.getCurrentMSeconds());
        requestRewardInfo(Util.getMConfig(NTVDefine.KEY_MCONF_GAME_OPENID));
    }

    public RewardInfo getRewardInfo() {
        return this.mRewardInfo;
    }

    public void updateRewardInfo_local(RewardInfo info) {
        this.mRewardInfo = info;
    }

    private void onRewardInfoResponse(short result, short rewardId, short itemNum, int startTime, int endTime, String itemArrStr) {
        RewardInfo rewardInfo = new RewardInfo();
        rewardInfo.parse(result, rewardId, itemNum, startTime, endTime, itemArrStr);
        this.mRewardInfo = rewardInfo;
        EventManager.getInstance().post(NTVDefine.EVT_HOLDER_BONUS_ON_REWARDINFO_RESULT, (Event) null);
    }

    public void sendHolderDeliverRewardRequest(short rewardId, short itemId, short status) {
        log("sendHolderDeliverRewardRequest, rewardId=" + rewardId + ", itemId=" + itemId + ", status=" + status);
        requestDeliverReward(Util.getMConfig(NTVDefine.KEY_MCONF_GAME_OPENID), rewardId, itemId, status);
    }

    private void onDeliverRewardResponse(short result, short rewardId, short itemId, short status) {
        EventManager.getInstance().post(NTVDefine.EVT_HOLDER_BONUS_ON_DELIVER_RESULT, new EventDeliverRewardResult(result, rewardId, itemId, status));
    }

    public void sendRequestSubscribeMatch(short optType, int matchId) {
        log("sendRequestSubscribeMatch : optType=" + optType + ", matchId=" + matchId);
        requestSubscribeMatch(optType, matchId);
    }

    public void onSubscribeMatchResponse(short result, short optType, int matchId) {
        EventManager.getInstance().post(NTVDefine.EVT_MATCH_RESERVE_RESULT, new EventReserveResult(result, optType, matchId));
        if (MatchInfoModel.getInstance().updateSingleMatch(result, optType, matchId)) {
            EventGetInfoData evt2 = new EventGetInfoData();
            evt2.mResult = result;
            EventManager.getInstance().post(NTVDefine.EVT_MATCH_GET_INFO_RESULT_UPDATE_ADAPTER, evt2);
        }
    }

    public void sendMatchInfoRequest(short infoType) {
        sendRequestGetInfo(infoType);
    }

    public void sendRequestGetInfo(short infoType) {
        log("sendRequestGetInfo : infoType=" + infoType);
        requestGetInfo(infoType);
    }

    public void onGetInfoResponse(short result, String info) {
        EventGetInfoData evt = new EventGetInfoData();
        evt.mResult = result;
        if (evt.parse(info)) {
            EventManager.getInstance().post(NTVDefine.EVT_MATCH_GET_INFO_RESULT, evt);
            EventManager.getInstance().post(NTVDefine.EVT_MATCH_GET_INFO_RESULT_UPDATE_ADAPTER, evt);
        }
    }

    private static void onBaseInfo(int result, int timestamp, String baseInfo) {
        log("onBaseInfo result:" + result);
        log("onBaseInfo timestamp:" + timestamp);
        log("onBaseInfo baseInfo:" + baseInfo);
        getInstance().onBaseInfoResponse(result, timestamp, baseInfo);
    }

    private static void onHeartBeat(int result, int num, short updateType, String baseInfo) {
        log("onHeartBeat result:" + result + ", num:" + num + ", updateType:" + updateType);
        log("onHeartBeat baseInfo:" + baseInfo);
        getInstance().onHeartBeatResponse(result, num, baseInfo);
    }

    private static void onRewardInfo(short result, short rewardId, short itemNum, int startTime, int endTime, String itemArrStr) {
        log("onRewardInfo: result=" + result + ", rewardId=" + rewardId + ", itemNum=" + itemNum + ", startTime=" + startTime + ", endTime=" + endTime);
        log("onRewardInfo: itemArrStr=" + itemArrStr);
        getInstance().onRewardInfoResponse(result, rewardId, itemNum, startTime, endTime, itemArrStr);
    }

    private static void onDeliverReward(short result, short rewardId, short itemId, short status) {
        log("onDeliverReward: result=" + result + ", rewardId=" + rewardId + ", itemId=" + itemId + ", status=" + status);
        getInstance().onDeliverRewardResponse(result, rewardId, itemId, status);
    }

    private static void onSubscribeMatch(short result, short optType, int matchId) {
        log("onSubscribeMatch: result=" + result + ", optType=" + optType + ", matchId=" + matchId);
        getInstance().onSubscribeMatchResponse(result, optType, matchId);
    }

    private static void onGetInfo(short result, String jsonInfo) {
        log("onGetInfo: result=" + result + ", jsonInfo=" + jsonInfo);
        getInstance().onGetInfoResponse(result, jsonInfo);
    }
}
