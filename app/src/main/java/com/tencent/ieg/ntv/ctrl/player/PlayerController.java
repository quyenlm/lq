package com.tencent.ieg.ntv.ctrl.player;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import com.tencent.ieg.ntv.TVShowManager;
import com.tencent.ieg.ntv.ctrl.HolderBonusController;
import com.tencent.ieg.ntv.event.Event;
import com.tencent.ieg.ntv.event.EventHideSystemUI;
import com.tencent.ieg.ntv.event.EventManager;
import com.tencent.ieg.ntv.event.IEventListener;
import com.tencent.ieg.ntv.event.net.EventPlayInfoData;
import com.tencent.ieg.ntv.event.net.EventViewCount;
import com.tencent.ieg.ntv.model.NTVDefine;
import com.tencent.ieg.ntv.network.BaseInfo;
import com.tencent.ieg.ntv.network.CommonTipCachInfo;
import com.tencent.ieg.ntv.network.NetworkModule;
import com.tencent.ieg.ntv.utils.Logger;
import com.tencent.ieg.ntv.utils.Util;
import com.tencent.ieg.ntv.view.PlayerView;
import com.tencent.rtmp2.ITXLivePlayListener;
import com.tencent.rtmp2.TXLiveBase;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerController {
    private static final int RESOLUTION_NONE = -1;
    /* access modifiers changed from: private */
    public static final String TAG = PlayerController.class.getSimpleName();
    private static PlayerController sInstance;
    private CommonTipCachInfo ctcInfo = null;
    /* access modifiers changed from: private */
    public long firstClickTime = 0;
    /* access modifiers changed from: private */
    public boolean isDoubleClick;
    /* access modifiers changed from: private */
    public HashSet<Integer> mAutoSwitchChannelHistory = new HashSet<>();
    /* access modifiers changed from: private */
    public boolean mChannelSideBarShown = false;
    /* access modifiers changed from: private */
    public Context mContext;
    private boolean mContinueConfirmed = false;
    /* access modifiers changed from: private */
    public int mCurChannel = 0;
    /* access modifiers changed from: private */
    public int mCurResolution = -1;
    private boolean mEnableImmersive = true;
    /* access modifiers changed from: private */
    public boolean mEnableVideoViewClick = true;
    private boolean mFullScreen = false;
    private boolean mImmersive = false;
    private Timer mImmersiveTimer;
    private ITXLivePlayListener mLivePlayListener = new ITXLivePlayListener() {
        public void onPlayEvent(int event, Bundle param) {
            PlayerController.log("chltag onPlayEvent:" + event);
            if (event == -1307 || event == 3005 || event == -2301) {
                PlayerController.log("need auto switch channel");
                if (PlayerController.this.mAutoSwitchChannelHistory.size() == 0) {
                    PlayerController.log("auto switch channel init, cur:" + PlayerController.this.mCurChannel);
                    PlayerController.this.mAutoSwitchChannelHistory.add(Integer.valueOf(PlayerController.this.mCurChannel));
                }
                List<EventPlayInfoData.ChannelInfo> channelInfoList = PlayerController.this.mPlayInfo.channelInfoList;
                if (channelInfoList == null || channelInfoList.size() <= 0 || PlayerController.this.mAutoSwitchChannelHistory.size() >= channelInfoList.size()) {
                    PlayerController.log("auto switch channel failed, already tried all");
                    PlayerController.this.onPlayError();
                    return;
                }
                for (int i = 0; i < channelInfoList.size(); i++) {
                    if (!PlayerController.this.mAutoSwitchChannelHistory.contains(Integer.valueOf(i))) {
                        PlayerController.log("auto switch channel to:" + i);
                        PlayerController.this.mAutoSwitchChannelHistory.add(Integer.valueOf(i));
                        PlayerController.this.onPlayLoading();
                        PlayerController.this.switchChannel(i);
                        return;
                    }
                }
                PlayerController.log("auto switch channel failed, should never reach here??");
                PlayerController.this.onPlayError();
            } else if (event == 2008 || event == 2103) {
                PlayerController.this.onPlayLoading();
            } else if (event <= -1300) {
                PlayerController.this.onPlayError();
            } else {
                if (event == 2004 && PlayerController.this.mAutoSwitchChannelHistory.size() > 0) {
                    PlayerController.log("play success, clear mAutoSwitchChannelHistory");
                    PlayerController.this.mAutoSwitchChannelHistory.clear();
                }
                PlayerController.this.onPlayNormal();
            }
        }

        public void onNetStatus(Bundle status) {
        }
    };
    /* access modifiers changed from: private */
    public boolean mManualPause = false;
    private BroadcastReceiver mNetworkChangeReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction() != null && intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE") && !isInitialStickyBroadcast()) {
                PlayerController.log("network changed");
                PlayerController.this.startPlayer(true);
                NetworkModule.getInstance().onNetWorkChange();
            }
        }
    };
    /* access modifiers changed from: private */
    public EventPlayInfoData mPlayInfo;
    private int mPlayOrStopStyle;
    private Player mPlayer;
    private PlayerView mPlayerViewEmbeded;
    private PlayerView mPlayerViewFullscreen;
    private boolean mRegisterNetworkChangeReceiver = false;
    /* access modifiers changed from: private */
    public boolean mResolutionSideBarShown = false;
    private PlayerView.UIEventListener mUIEventCallback = new PlayerView.UIEventListener() {
        public void onViewers() {
        }

        public void onVideoView() {
            if (PlayerController.this.mEnableVideoViewClick) {
                if (PlayerController.this.firstClickTime > 0) {
                    long unused = PlayerController.this.secondClickTime = System.currentTimeMillis();
                    if (PlayerController.this.secondClickTime - PlayerController.this.firstClickTime < 200) {
                        long unused2 = PlayerController.this.firstClickTime = 0;
                        boolean unused3 = PlayerController.this.isDoubleClick = true;
                        PlayerController.this.onPlayerViewClick(true);
                        return;
                    }
                }
                long unused4 = PlayerController.this.firstClickTime = System.currentTimeMillis();
                boolean unused5 = PlayerController.this.isDoubleClick = false;
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(200);
                            long unused = PlayerController.this.firstClickTime = 0;
                            if (!PlayerController.this.isDoubleClick) {
                                PlayerController.this.onPlayerViewClick(false);
                            }
                        } catch (InterruptedException e) {
                            Logger.d(PlayerController.TAG, "exception:" + e);
                        }
                    }
                }).start();
            }
        }

        public void onPlayPause() {
            if (PlayerController.this.mManualPause) {
                PlayerController.this.realStartPlayer();
                boolean unused = PlayerController.this.mManualPause = false;
                return;
            }
            PlayerController.this.realPausePlayer();
            boolean unused2 = PlayerController.this.mManualPause = true;
        }

        public void onPlayRefresh() {
            Logger.d(PlayerController.TAG, "onPlayRefresh.");
            PlayerController.this.startPlayer(true);
        }

        public void onFullscreen() {
            PlayerController.this.onFullScreenEvt();
        }

        public void onResolution() {
            if (!PlayerController.this.mResolutionSideBarShown) {
                PlayerController.this.getCurPlayerView().setResolutionSideBarVisible(true);
                boolean unused = PlayerController.this.mResolutionSideBarShown = true;
                PlayerController.this.getCurPlayerView().setCtrlBarVisible(false);
                PlayerController.this.unScheduleImmersive();
            }
        }

        public void onResolution(int index) {
            int unused = PlayerController.this.mCurResolution = index;
            PlayerController.this.startPlayer(true);
            if (PlayerController.this.mResolutionSideBarShown) {
                PlayerController.this.getCurPlayerView().setResolutionSideBarVisible(false);
                boolean unused2 = PlayerController.this.mResolutionSideBarShown = false;
                PlayerController.this.getCurPlayerView().setCtrlBarVisible(true);
            }
        }

        public void onWIFIContinue() {
            PlayerController.this.continueIfNotWifi();
        }

        public void onChannel() {
            PlayerController.log("onChannel");
            if (!PlayerController.this.mChannelSideBarShown) {
                PlayerController.this.getCurPlayerView().setChannelSideBarVisible(true);
                boolean unused = PlayerController.this.mChannelSideBarShown = true;
                PlayerController.this.getCurPlayerView().setCtrlBarVisible(false);
                PlayerController.this.unScheduleImmersive();
            }
        }

        public void onChannel(int index) {
            PlayerController.log("onChannel index:" + index);
            PlayerController.this.switchChannel(index);
            if (PlayerController.this.mChannelSideBarShown) {
                PlayerController.this.getCurPlayerView().setChannelSideBarVisible(false);
                boolean unused = PlayerController.this.mChannelSideBarShown = false;
                PlayerController.this.getCurPlayerView().setCtrlBarVisible(true);
            }
        }
    };
    private IEventListener mViewCountUpdater = new IEventListener() {
        public void onEvent(int eventId, Event event) {
            PlayerController.this.updateViewCount(((EventViewCount) event).viewCount);
        }
    };
    private IEventListener mViewHideNavigationUI = new IEventListener() {
        public void onEvent(int eventId, Event event) {
            if (((EventHideSystemUI) event).hasFocus) {
                ((Activity) PlayerController.this.mContext).runOnUiThread(new Runnable() {
                    public void run() {
                        PlayerController.this.CheckIfHideSystemUI();
                    }
                });
            }
        }
    };
    private int mViewerNum = 8;
    private IEventListener onPlayInfoEvent = new IEventListener() {
        public void onEvent(int eventId, Event event) {
            PlayerController.this.onPlayInfo((EventPlayInfoData) event);
            EventManager.getInstance().unregister(eventId, this);
        }
    };
    /* access modifiers changed from: private */
    public long secondClickTime = 0;

    /* access modifiers changed from: private */
    public static void log(String msg) {
        Logger.d(TAG, msg);
    }

    public static PlayerController getInstance() {
        if (sInstance == null) {
            sInstance = new PlayerController();
        }
        return sInstance;
    }

    public static void destroyInstance() {
        sInstance.mPlayer.stop();
        sInstance = null;
    }

    public void init(Context context, PlayerView playerView) {
        this.mContext = context;
        this.mPlayer = new Player(this.mContext, this.mLivePlayListener);
        this.mPlayerViewEmbeded = playerView;
        this.mPlayerViewEmbeded.setUIEventListener(this.mUIEventCallback);
        log("TXLiveBase.getSDKVersionStr(): " + TXLiveBase.getSDKVersionStr());
        BaseInfo baseInfo = NetworkModule.getInstance().getBaseInfo();
        if (baseInfo != null) {
            EventPlayInfoData playInfoData = new EventPlayInfoData();
            if (playInfoData.parse(baseInfo.getPlayInfoObject())) {
                log("EventPlayInfoData parse success");
                onPlayInfo(playInfoData);
            }
        } else {
            EventManager.getInstance().register(5005, this.onPlayInfoEvent);
        }
        EventManager.getInstance().register(5006, this.mViewCountUpdater);
        EventManager.getInstance().register(NTVDefine.EVT_HIDE_NAVIGATION_UI, this.mViewHideNavigationUI);
    }

    public void onPlayInfo(EventPlayInfoData playInfo) {
        log("onPlayInfo");
        if (dataIntegrityCheckingPass(playInfo)) {
            this.mPlayInfo = playInfo;
            updateCurPlayerView();
            return;
        }
        Logger.e(TAG, "onPlayInfo playInfo invalid");
        getCurPlayerView().setCtrlBarVisible(false);
        disableImmersive();
    }

    private boolean dataIntegrityCheckingPass(EventPlayInfoData playInfo) {
        if (playInfo == null) {
            Logger.e(TAG, "dataIntegrityCheckingPass : false, playInfo = null.");
            return false;
        } else if (playInfo.channelInfoList == null || playInfo.channelInfoList.size() <= 0) {
            Logger.e(TAG, "dataIntegrityCheckingPass : false, playInfo.channelInfoList = null or playInfo.channelInfoList.size() = 0.");
            return false;
        } else {
            int i = 0;
            while (i < playInfo.channelInfoList.size()) {
                EventPlayInfoData.ChannelInfo channelInfo = playInfo.channelInfoList.get(i);
                if (channelInfo == null) {
                    Logger.e(TAG, "dataIntegrityCheckingPass : false, channelInfo = null.");
                    return false;
                } else if (channelInfo.playUrlList == null || channelInfo.playUrlList.size() <= 0) {
                    Logger.e(TAG, "dataIntegrityCheckingPass : false, channelInfo.playUrlList = null or channelInfo.playUrlList.size() = 0.");
                    return false;
                } else if (channelInfo.defaultIndex < 0 || channelInfo.defaultIndex >= channelInfo.playUrlList.size()) {
                    Logger.e(TAG, "dataIntegrityCheckingPass : false, defaultIndex out of bound.");
                    return false;
                } else {
                    i++;
                }
            }
            return true;
        }
    }

    /* access modifiers changed from: private */
    public void updateViewCount(int viewCount) {
        this.mViewerNum = viewCount;
        getCurPlayerView().setViewNumText(this.mViewerNum);
    }

    /* access modifiers changed from: private */
    public void startPlayer(boolean forcePlay) {
        Util.NetworkType netType = Util.getNetworkType();
        log("startPlayer netType:" + netType);
        log("startPlayer mCurChannel=" + this.mCurChannel + ", mCurResolution" + this.mCurResolution + ", forcePlay" + forcePlay);
        getCurPlayerView().setChannelSelected(this.mCurChannel);
        if (this.mCurResolution == -1) {
            log("startPlayer mCurResolution none, use default");
            this.mCurResolution = this.mPlayInfo.channelInfoList.get(this.mCurChannel).defaultIndex;
        }
        getCurPlayerView().setResolutionSelected(this.mCurResolution);
        if (netType == Util.NetworkType.WIFI) {
            getCurPlayerView().showCommonTip(true, false, TVShowManager.getInstance().getI18NText(NTVDefine.KEY_LOADING_VIDEO));
            if (forcePlay) {
                realStartPlayer();
            } else {
                getCurPlayerView().showCommonTip(false, false, "");
            }
            scheduleImmersive();
        } else if (netType == Util.NetworkType.MOBILE_2G || netType == Util.NetworkType.MOBILE_3G || netType == Util.NetworkType.MOBILE_4G) {
            this.mContinueConfirmed = false;
            realPausePlayer();
            getCurPlayerView().showCommonTip(true, true, TVShowManager.getInstance().getI18NText(NTVDefine.KEY_NOT_WIFI_TIP_TEXT));
        } else {
            realPausePlayer();
            setImmersive(false, true);
            getCurPlayerView().showCommonTip(true, false, TVShowManager.getInstance().getI18NText(NTVDefine.KEY_NETWORK_ERROR));
        }
    }

    /* access modifiers changed from: private */
    public void continueIfNotWifi() {
        this.mContinueConfirmed = true;
        getCurPlayerView().showCommonTip(false, false, "");
        realStartPlayer();
        scheduleImmersive();
    }

    /* access modifiers changed from: private */
    public void realStartPlayer() {
        log("realStartPlayer in.");
        if (this.mCurResolution == -1) {
            log("realStartPlayer mCurResolution none, use default");
            this.mCurResolution = this.mPlayInfo.channelInfoList.get(this.mCurChannel).defaultIndex;
        }
        this.mPlayer.play(getCurPlayUrl(), getCurPlayerView().getTXCloudVideoView());
        getCurPlayerView().setPlayPauseBtnImage(0);
        getCurPlayerView().setChannelSelected(this.mCurChannel);
        getCurPlayerView().setResolutionSelected(this.mCurResolution);
        this.mManualPause = false;
    }

    /* access modifiers changed from: private */
    public void realPausePlayer() {
        this.mPlayer.pause();
        getCurPlayerView().setPlayPauseBtnImage(1);
    }

    /* access modifiers changed from: private */
    public PlayerView getCurPlayerView() {
        return this.mFullScreen ? this.mPlayerViewFullscreen : this.mPlayerViewEmbeded;
    }

    /* access modifiers changed from: private */
    public void unScheduleImmersive() {
        if (this.mEnableImmersive && this.mImmersiveTimer != null) {
            this.mImmersiveTimer.cancel();
            this.mImmersiveTimer = null;
        }
    }

    private void scheduleImmersive() {
        if (this.mEnableImmersive && !this.mImmersive) {
            if (this.mImmersiveTimer == null) {
                this.mImmersiveTimer = new Timer();
            }
            this.mImmersiveTimer.schedule(new TimerTask() {
                public void run() {
                    ((Activity) PlayerController.this.mContext).runOnUiThread(new Runnable() {
                        public void run() {
                            PlayerController.this.setImmersive(true, true);
                        }
                    });
                }
            }, 3000);
        }
    }

    /* access modifiers changed from: private */
    public void setImmersive(boolean value, boolean bTransition) {
        if (this.mEnableImmersive) {
            log("setImmersive:" + value);
            getCurPlayerView().setInViewImmersive(value, bTransition);
            this.mImmersive = value;
        }
    }

    /* access modifiers changed from: private */
    public void playerViewSingleClick() {
        boolean z = false;
        if (this.mResolutionSideBarShown || this.mChannelSideBarShown) {
            getCurPlayerView().setResolutionSideBarVisible(false);
            this.mResolutionSideBarShown = false;
            getCurPlayerView().setChannelSideBarVisible(false);
            this.mChannelSideBarShown = false;
            getCurPlayerView().setCtrlBarVisible(true);
        } else {
            unScheduleImmersive();
            if (!this.mImmersive) {
                z = true;
            }
            setImmersive(z, true);
        }
        scheduleImmersive();
    }

    /* access modifiers changed from: private */
    public void onPlayerViewClick(final boolean isdoubleclick) {
        ((Activity) this.mContext).runOnUiThread(new Runnable() {
            public void run() {
                if (isdoubleclick) {
                    PlayerController.this.onFullScreenEvt();
                } else {
                    PlayerController.this.playerViewSingleClick();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void onFullScreenEvt() {
        boolean isFullscreenIssueDevice = Util.isFullscreenIssueDevice();
        if (isFullscreenIssueDevice) {
            this.mPlayer.pause();
        }
        if (!this.mFullScreen) {
            if (this.mPlayerViewFullscreen == null) {
                this.mPlayerViewFullscreen = new PlayerView(this.mContext);
                this.mPlayerViewFullscreen.setFullScreen(true);
                this.mPlayerViewFullscreen.setUIEventListener(this.mUIEventCallback);
            }
            TVShowManager.getInstance().onReportEvent("ntv_full_screen");
            ((WindowManager) this.mContext.getSystemService("window")).addView(this.mPlayerViewFullscreen, new WindowManager.LayoutParams(-1, -1, 2, 1024, -3));
            this.mPlayerViewFullscreen.setFullscreenBtnImage(1);
            this.mPlayer.setPlayerView(this.mPlayerViewFullscreen.getTXCloudVideoView());
            this.mFullScreen = true;
        } else {
            ((WindowManager) this.mContext.getSystemService("window")).removeView(this.mPlayerViewFullscreen);
            this.mPlayer.setPlayerView(this.mPlayerViewEmbeded.getTXCloudVideoView());
            this.mFullScreen = false;
        }
        if (isFullscreenIssueDevice) {
            this.mPlayer.resume();
        }
        updateCurPlayerView();
    }

    public void onHolderBonus() {
        HolderBonusController.getInstance().showHolderBonusList();
    }

    /* access modifiers changed from: private */
    public void switchChannel(int index) {
        this.mCurChannel = index;
        updateResolutionTexts();
        this.mCurResolution = this.mPlayInfo.channelInfoList.get(this.mCurChannel).defaultIndex;
        startPlayer(true);
    }

    private void updateCurPlayerView() {
        String tag;
        int i;
        getCurPlayerView().setTitleText(this.mPlayInfo.title);
        getCurPlayerView().setSubTitleText(this.mPlayInfo.subTitle);
        getCurPlayerView().setViewNumText(this.mViewerNum);
        if (this.mPlayInfo.status == 1) {
            tag = TVShowManager.getInstance().getI18NText(NTVDefine.KEY_VIDEO_TYPE_LIVE);
        } else {
            tag = TVShowManager.getInstance().getI18NText(NTVDefine.KEY_VIDEO_TYPE_REPLAY);
            getCurPlayerView().hideBtnChannel();
        }
        getCurPlayerView().setTypeTagText(tag);
        getCurPlayerView().setPlayPauseBtnImage(getPlayOrStopStyle());
        PlayerView curPlayerView = getCurPlayerView();
        if (this.mFullScreen) {
            i = 1;
        } else {
            i = 0;
        }
        curPlayerView.setFullscreenBtnImage(i);
        updateChannelTexts();
        updateResolutionTexts();
        if (this.mCurResolution != -1) {
            getCurPlayerView().setChannelSelected(this.mCurChannel);
            getCurPlayerView().setResolutionSelected(this.mCurResolution);
        }
        CommonTipCachInfo info = getInstance().getCommonTipInfoCach();
        setImmersive(false, true);
        if (info != null && !info.mShowPannel) {
            scheduleImmersive();
        }
        if (info != null) {
            getCurPlayerView().showCommonTip(info.mShowPannel, info.mShowBtn, info.mTipText);
        }
        getCurPlayerView().setPlayPauseBtnImage(getInstance().getPlayOrStopStyle());
    }

    private void updateChannelTexts() {
        List<EventPlayInfoData.ChannelInfo> channelInfoList = this.mPlayInfo.channelInfoList;
        ArrayList<String> channelTextList = new ArrayList<>();
        for (int i = 0; i < channelInfoList.size(); i++) {
            channelTextList.add(channelInfoList.get(i).name);
        }
        getCurPlayerView().setChannelList(channelTextList);
    }

    private void updateResolutionTexts() {
        List<EventPlayInfoData.PlayUrl> playUrlList = this.mPlayInfo.channelInfoList.get(this.mCurChannel).playUrlList;
        ArrayList<String> resolutionTextList = new ArrayList<>();
        for (int j = 0; j < playUrlList.size(); j++) {
            resolutionTextList.add(playUrlList.get(j).name);
        }
        getCurPlayerView().setResolutionList(resolutionTextList);
    }

    public void resume() {
        log("app resume");
        if (this.mPlayInfo != null) {
            if (!this.mManualPause) {
                if (!this.mContinueConfirmed) {
                    startPlayer(true);
                } else {
                    realStartPlayer();
                }
            }
            if (this.mContext != null && !this.mRegisterNetworkChangeReceiver) {
                this.mContext.registerReceiver(this.mNetworkChangeReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
                this.mRegisterNetworkChangeReceiver = true;
            }
        }
    }

    public void pause() {
        log("app pause");
        if (this.mPlayInfo != null) {
            if (!this.mManualPause) {
                realPausePlayer();
            }
            if (this.mContext != null && this.mRegisterNetworkChangeReceiver) {
                this.mContext.unregisterReceiver(this.mNetworkChangeReceiver);
                this.mRegisterNetworkChangeReceiver = false;
            }
        }
    }

    private String getCurPlayUrl() {
        try {
            return this.mPlayInfo.channelInfoList.get(this.mCurChannel).playUrlList.get(this.mCurResolution).url;
        } catch (Exception e) {
            Logger.e(TAG, e);
            return "";
        }
    }

    /* access modifiers changed from: private */
    public void onPlayLoading() {
        setImmersive(false, false);
        unScheduleImmersive();
        getCurPlayerView().showCommonTip(true, false, TVShowManager.getInstance().getI18NText(NTVDefine.KEY_LOADING_VIDEO));
    }

    /* access modifiers changed from: private */
    public void onPlayError() {
        setImmersive(false, false);
        unScheduleImmersive();
        getCurPlayerView().showCommonTip(true, false, TVShowManager.getInstance().getI18NText(NTVDefine.KEY_NETWORK_ERROR));
    }

    /* access modifiers changed from: private */
    public void onPlayNormal() {
        setImmersive(true, false);
        unScheduleImmersive();
        getCurPlayerView().showCommonTip(false, false, "");
    }

    public void disableImmersive() {
        this.mEnableImmersive = false;
    }

    public void disableVideoViewClick() {
        this.mEnableVideoViewClick = false;
    }

    @TargetApi(19)
    public void CheckIfHideSystemUI() {
        View view;
        if (getSystemVersion() >= 19 && this.mContext != null) {
            if (!this.mFullScreen || this.mPlayerViewFullscreen == null) {
                view = ((Activity) this.mContext).getWindow().getDecorView();
            } else {
                view = this.mPlayerViewFullscreen;
            }
            if (view != null) {
                int visibility = view.getSystemUiVisibility();
                Logger.i(TAG, "cur visibility int: " + visibility);
                Logger.i(TAG, "options int: " + 5894);
                if (visibility != 5894) {
                    Logger.i(TAG, "apply options " + 5894);
                    view.setSystemUiVisibility(5894);
                }
            }
        }
    }

    public static int getSystemVersion() {
        return Build.VERSION.SDK_INT;
    }

    public void setCommonTipInfoCach(CommonTipCachInfo info) {
        this.ctcInfo = info;
    }

    public CommonTipCachInfo getCommonTipInfoCach() {
        return this.ctcInfo;
    }

    public void setPlayOrStopStyle(int style) {
        this.mPlayOrStopStyle = style;
    }

    public int getPlayOrStopStyle() {
        return this.mPlayOrStopStyle;
    }
}
