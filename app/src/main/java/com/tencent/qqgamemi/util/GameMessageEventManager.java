package com.tencent.qqgamemi.util;

import android.content.Context;
import com.tencent.component.utils.log.LogUtil;
import com.tencent.qqgamemi.QMiConfig;

public class GameMessageEventManager {
    private static String TAG = "GameMessageEventManager";
    private static volatile GameEngineEvent sGameEngine;
    private static volatile GameMessageEventManager sInstance;

    private GameMessageEventManager(Context context) {
        sGameEngine = new GameEngineEventFactory().createGameEngineEvent(context);
    }

    public static GameMessageEventManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (GameMessageEventManager.class) {
                if (sInstance == null) {
                    sInstance = new GameMessageEventManager(context);
                }
            }
        }
        return sInstance;
    }

    public void onStartMomentRecordingStatus(Context context, int errorCode) {
        LogUtil.i(TAG, "onStartMomentRecordingStatus:" + errorCode);
        if (sGameEngine != null) {
            sGameEngine.onStartMomentRecordingStatus(context, errorCode);
        }
    }

    public void onStartJudgementRecordingStatus(Context context, int errorCode) {
        LogUtil.i(TAG, "onStartJudgementRecordingStatus:" + errorCode);
        if (sGameEngine != null) {
            sGameEngine.onStartJudgementRecordingStatus(context, errorCode);
        }
    }

    public void onCheckSupportedSDKFeatureCompletion(Context context, int switchs) {
        LogUtil.i(TAG, "onCheckSupportedSDKFeatureCompletion:" + switchs);
        if (sGameEngine != null) {
            sGameEngine.onCheckSupportedSDKFeatureCompletion(context, switchs);
        }
    }

    public void onCheckSDKPermission(Context context, boolean isPermission) {
        LogUtil.i(TAG, "onCheckSDKPermission:" + isPermission);
        if (sGameEngine != null) {
            sGameEngine.onCheckSDKPermission(context, isPermission);
        }
    }

    private class GameEngineEventFactory {
        private GameEngineEventFactory() {
        }

        public GameEngineEvent createGameEngineEvent(Context context) {
            if (QMiConfig.getInstance().isUnity(context)) {
                return new UnityMessageEvent();
            }
            if (QMiConfig.getInstance().isUnrealEngine(context)) {
                return new UnrealEngineMessageEvent();
            }
            return new Cocos2dxMessageEvent();
        }
    }
}
