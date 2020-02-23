package com.tencent.qqgamemi.util;

import android.content.Context;
import com.tencent.component.utils.log.LogUtil;
import com.tencent.qqgamemi.event.EventID;
import com.tencent.qqgamemi.event.EventRouter;

public class Cocos2dxMessageEvent implements GameEngineEvent {
    private static final String TAG = "Cocos2dxMessageEvent";

    private static boolean isCocos2d(Context context) {
        return true;
    }

    public void onStartMomentRecordingStatus(Context context, int errorCode) {
        LogUtil.i(TAG, "onStartMomentRecordingStatus:" + errorCode);
        if (isCocos2d(context)) {
            EventRouter.getInstance().broadcastEvent(EventID.GAMEJOY_STARTRECORDING_RESULT, Integer.valueOf(errorCode));
        }
    }

    public void onStartJudgementRecordingStatus(Context context, int errorCode) {
        LogUtil.i(TAG, "onStartJudgementRecordingStatus:" + errorCode);
        if (isCocos2d(context)) {
            EventRouter.getInstance().broadcastEvent(EventID.GAMEJOY_START_JUDGEMENT_RECORDING_RESULT, Integer.valueOf(errorCode));
        }
    }

    public void onCheckSupportedSDKFeatureCompletion(Context context, int switchs) {
        LogUtil.i(TAG, "onCheckSupportedSDKFeatureCompletion:" + switchs);
        if (isCocos2d(context)) {
            EventRouter.getInstance().broadcastEvent(EventID.GAMEJOY_SDK_FEATURE_CHECK_RESULT, Integer.valueOf(switchs));
        }
    }

    public void onCheckSDKPermission(Context context, boolean isPermission) {
        LogUtil.i(TAG, "onCheckSDKPermission:" + isPermission);
        if (isCocos2d(context)) {
            EventRouter.getInstance().broadcastEvent(EventID.GAMEJOY_SDK_PERMISSION_CHECK_RESULT, Boolean.valueOf(isPermission));
        }
    }
}
