package com.huawei.iaware.sdk.gamesdk;

import android.util.Log;

public class IAwareGameSdk {
    private static final int MAX_STR_SIZE = 256;
    private GameCallBack gameCbk = new GameCallBack() {
        public void getPhoneInfo(String str) {
            Log.i("IAwareGameSdk", "info=" + str + " and mPhoneInfo is " + IAwareGameSdk.this.mPhoneInfo);
            String unused = IAwareGameSdk.this.mPhoneInfo = str;
        }
    };
    private IAwareGameSdkAdapter mIAwareGameSdkAdapter = null;
    /* access modifiers changed from: private */
    public String mPhoneInfo = "";

    public interface GameCallBack {
        void getPhoneInfo(String str);
    }

    public String getPhoneInfo() {
        Log.i("IAwareGameSdk", "getPhoneInfo, level: " + this.mPhoneInfo);
        return this.mPhoneInfo;
    }

    public boolean registerGame(String str) {
        return registerGame(str, this.gameCbk);
    }

    public boolean registerGame(String str, GameCallBack gameCallBack) {
        Log.i("IAwareGameSdk", "registerGame, packageName:" + str);
        if (str == null || str.length() <= 0 || gameCallBack == null) {
            return false;
        }
        if (this.mIAwareGameSdkAdapter != null) {
            return true;
        }
        this.mIAwareGameSdkAdapter = new IAwareGameSdkAdapter();
        return this.mIAwareGameSdkAdapter.registerGameCallback(str, gameCallBack);
    }

    public void updateGameAppInfo(String str) {
        Log.i("IAwareGameSdk", "updateGameAppInfo, json: " + str);
        if (str != null && str.length() > 0 && str.length() <= 256 && this.mIAwareGameSdkAdapter != null) {
            this.mIAwareGameSdkAdapter.reportData(str);
        }
    }
}
