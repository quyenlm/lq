package com.tencent.qqgamemi.util;

import android.content.Context;
import com.tencent.component.utils.log.LogUtil;
import com.tencent.qqgamemi.QMiConfig;
import com.tencent.qqgamemi.event.ue.UnrealEngineEventID;
import com.tencent.qqgamemi.event.ue.UnrealEngineEventManager;

public class UnrealEngineMessageEvent implements GameEngineEvent {
    private static final String TAG = "UnrealEngineMessageEvent";
    private Boolean mIsUnrealEngine = null;

    private boolean isUnrealEngine(Context context) {
        if (this.mIsUnrealEngine != null) {
            return this.mIsUnrealEngine.booleanValue();
        }
        boolean isUE = QMiConfig.getInstance().isUnrealEngine(context);
        this.mIsUnrealEngine = new Boolean(isUE);
        return isUE;
    }

    public void onStartMomentRecordingStatus(Context context, int errorCode) {
        LogUtil.i(TAG, "onStartMomentRecordingStatus:" + errorCode);
        if (isUnrealEngine(context)) {
            UnrealEngineEventManager.getInstance().notify(UnrealEngineEventID.GAMEJOY_STARTRECORDING_RESULT, errorCode);
        }
    }

    public void onStartJudgementRecordingStatus(Context context, int errorCode) {
        LogUtil.i(TAG, "onStartJudgementRecordingStatus:" + errorCode);
        if (isUnrealEngine(context)) {
            UnrealEngineEventManager.getInstance().notify(UnrealEngineEventID.GAMEJOY_START_JUDGEMENT_RECORDING_RESULT, errorCode);
        }
    }

    public void onCheckSupportedSDKFeatureCompletion(Context context, int switchs) {
        LogUtil.i(TAG, "onCheckSupportedSDKFeatureCompletion:" + switchs);
        if (isUnrealEngine(context)) {
            UnrealEngineEventManager.getInstance().notify(UnrealEngineEventID.GAMEJOY_SDK_FEATURE_CHECK_RESULT, switchs);
        }
    }

    public void onCheckSDKPermission(Context context, boolean isPermission) {
        LogUtil.i(TAG, "onCheckSDKPermission:" + isPermission);
        if (isUnrealEngine(context)) {
            UnrealEngineEventManager.getInstance().notify(UnrealEngineEventID.GAMEJOY_SDK_PERMISSION_CHECK_RESULT, isPermission);
        }
    }
}
