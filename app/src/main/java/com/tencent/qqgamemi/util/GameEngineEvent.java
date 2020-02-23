package com.tencent.qqgamemi.util;

import android.content.Context;

public interface GameEngineEvent {
    void onCheckSDKPermission(Context context, boolean z);

    void onCheckSupportedSDKFeatureCompletion(Context context, int i);

    void onStartJudgementRecordingStatus(Context context, int i);

    void onStartMomentRecordingStatus(Context context, int i);
}
