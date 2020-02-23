package com.tencent.imsdk.game.base;

import android.content.Context;
import com.tencent.imsdk.IMCallback;
import com.tencent.imsdk.IMResult;
import com.tencent.imsdk.game.api.IMGameListener;
import com.tencent.imsdk.tool.etc.IMLogger;

public abstract class IMGameBase {
    public abstract void achieve(String str, int i);

    public abstract boolean available();

    public abstract void event(String str, int i);

    public abstract void getAchieve(IMGameListener iMGameListener, boolean z);

    public abstract void getLeaderBoard(IMGameListener iMGameListener, boolean z, String str);

    public abstract void getQuests(IMGameListener iMGameListener, boolean z, String... strArr);

    public abstract boolean initialize(Context context);

    public abstract boolean isInstalledPlayServices();

    public abstract void quit();

    public abstract void setIMGoogleCallback(IMGameListener iMGameListener);

    public abstract void setup();

    public abstract void showAchieve();

    public abstract void showLeaderBoard(String str);

    public abstract void showQuests();

    public abstract void submitScore(String str, int i);

    public void setup(IMCallback<IMResult> iMCallback) {
        IMLogger.e("need over ride this function !");
    }

    public void quit(IMCallback<IMResult> iMCallback) {
        IMLogger.e("need over ride this function !");
    }

    public void submitScore(String category, int score, IMCallback<IMResult> iMCallback) {
        IMLogger.e("need over ride this function !");
    }

    public void achieve(String achieveId, int count, IMCallback<IMResult> iMCallback) {
        IMLogger.e("need over ride this function !");
    }

    public void event(String event, int count, IMCallback<IMResult> iMCallback) {
        IMLogger.e("need over ride this function !");
    }

    public void showQuests(IMCallback<IMResult> iMCallback) {
        IMLogger.e("need over ride this function !");
    }

    public void showAchieve(IMCallback<IMResult> iMCallback) {
        IMLogger.e("need over ride this function !");
    }

    public void showLeaderBoard(String id, IMCallback<IMResult> iMCallback) {
        IMLogger.e("need over ride this function !");
    }
}
