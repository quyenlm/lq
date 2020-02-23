package com.tencent.ieg.ntv.ctrl.player;

import android.content.Context;
import com.tencent.ieg.ntv.utils.Logger;
import com.tencent.rtmp2.ITXLivePlayListener;
import com.tencent.rtmp2.TXLiveBase;
import com.tencent.rtmp2.TXLivePlayConfig;
import com.tencent.rtmp2.TXLivePlayer;
import com.tencent.rtmp2.ui.TXCloudVideoView;

public class Player {
    private static final String TAG = Player.class.getSimpleName();
    private Context mContext;
    private TXLivePlayer mLivePlayer;
    private ITXLivePlayListener mPlayListener;
    private boolean mPlaying = false;
    private TXCloudVideoView mView;

    private static void log(String msg) {
        Logger.d(TAG, msg);
    }

    public Player(Context context, ITXLivePlayListener _playListener) {
        this.mContext = context;
        this.mPlayListener = _playListener;
    }

    private void initLivePlayer() {
        this.mLivePlayer = new TXLivePlayer(this.mContext);
        if (!Logger.getEnable()) {
            TXLiveBase.setLogLevel(6);
        }
        TXLivePlayConfig mLivePlayConfig = new TXLivePlayConfig();
        mLivePlayConfig.setAutoAdjustCacheTime(true);
        mLivePlayConfig.setMinAutoAdjustCacheTime(1.0f);
        mLivePlayConfig.setMaxAutoAdjustCacheTime(5.0f);
        this.mLivePlayer.enableHardwareDecode(true);
        this.mLivePlayer.setConfig(mLivePlayConfig);
        this.mLivePlayer.setPlayListener(this.mPlayListener);
        this.mLivePlayer.setRenderMode(1);
    }

    public void play(String url, TXCloudVideoView view) {
        stop();
        log("play:" + url);
        if (this.mLivePlayer == null) {
            initLivePlayer();
        }
        this.mView = view;
        this.mLivePlayer.setPlayerView(this.mView);
        this.mLivePlayer.startPlay(url, getUrlType(url));
        this.mPlaying = true;
    }

    private int getUrlType(String url) {
        int iType = 1;
        if (url.toLowerCase().indexOf("rtmp:") >= 0) {
            iType = 0;
        }
        if (url.toLowerCase().indexOf(".acc") >= 0) {
            return 5;
        }
        return iType;
    }

    public void stop() {
        log("stop mPlaying:" + this.mPlaying);
        if (this.mPlaying) {
            this.mView.onDestroy();
            this.mLivePlayer.stopPlay(true);
            this.mPlaying = false;
        }
    }

    public void resume() {
        log("resume mPlaying:" + this.mPlaying);
        if (this.mPlaying) {
            this.mLivePlayer.resume();
        }
    }

    public void pause() {
        log("pause mPlaying:" + this.mPlaying);
        if (this.mPlaying) {
            this.mLivePlayer.pause();
        }
    }

    public void setPlayerView(TXCloudVideoView view) {
        if (this.mPlaying) {
            if (this.mView != view) {
                this.mView.onDestroy();
            }
            this.mView = view;
            this.mLivePlayer.setPlayerView(this.mView);
        }
    }
}
