package com.beetalk.sdk.networking;

import android.os.Handler;
import android.os.Looper;
import com.beetalk.sdk.helper.BBLogger;

public class UILoop {
    private static UILoop __instance;
    private Handler m_uiHandler = new Handler(Looper.getMainLooper());

    private UILoop() {
    }

    public static UILoop getInstance() {
        if (__instance == null) {
            __instance = new UILoop();
        }
        return __instance;
    }

    public void init() {
        BBLogger.i("BBUILoop init", new Object[0]);
    }

    public void post(Runnable call) {
        this.m_uiHandler.post(call);
    }

    public void delayPost(Runnable r, int delayMillis) {
        this.m_uiHandler.postDelayed(r, (long) delayMillis);
    }

    public void cancelPost(Runnable r) {
        this.m_uiHandler.removeCallbacks(r);
    }
}
