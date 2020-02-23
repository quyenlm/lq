package com.tencent.qqgamemi.util;

import android.content.Context;
import com.tencent.component.utils.log.LogUtil;
import com.tencent.qqgamemi.QMiConfig;
import com.unity3d.player.UnityPlayer;

public class UnityMessageEvent implements GameEngineEvent {
    private static String TAG = "UnityMessageEvent";
    private static final String UnityMessageNotifyClassName = "GameJoyUnityNotify";

    public static void sendMessage(Context context, String fun, String param) {
        if (QMiConfig.getInstance().isUnity(context)) {
            LogUtil.i(TAG, String.format("fun:%s,param:%s", new Object[]{fun, param}));
            UnityPlayer.UnitySendMessage(UnityMessageNotifyClassName, fun, param);
        }
    }

    public void onStartMomentRecordingStatus(Context context, int errorCode) {
        sendMessage(context, "onStartMomentRecordingStatus", String.valueOf(errorCode));
    }

    public void onStartJudgementRecordingStatus(Context context, int errorCode) {
        sendMessage(context, "onStartJudgementRecordingStatus", String.valueOf(errorCode));
    }

    public void onCheckSupportedSDKFeatureCompletion(Context context, int switchs) {
        sendMessage(context, "OnCheckSupportedSDKFeatureCompletion", String.valueOf(switchs));
    }

    public void onCheckSDKPermission(Context context, boolean isPermission) {
        sendMessage(context, "OnFinishCheckSDKPremission", String.valueOf(isPermission ? "true" : "false"));
    }
}
