package com.tencent.qqgamemi.event.ue;

import com.tencent.component.utils.log.LogUtil;

public class UnrealEngineEventDispatcher {
    private static final String LOG_TAG = "UnrealEngineEvent";

    public static native void native_onNotify(int i, int i2);

    public static native void native_onNotify(int i, long j);

    public static native void native_onNotify(int i, Object obj);

    public static native void native_onNotify(int i, boolean z);

    public void onNotify(int event, int msg) {
        try {
            LogUtil.i(LOG_TAG, "onNotify: " + event + " , " + msg);
            native_onNotify(event, msg);
            LogUtil.i(LOG_TAG, "onNotify after : " + event);
        } catch (Throwable tr) {
            LogUtil.e(LOG_TAG, "onNotify failed: " + event + " , " + msg);
            tr.printStackTrace();
        }
    }

    public void onNotify(int event, boolean msg) {
        try {
            LogUtil.i(LOG_TAG, "onNotify: " + event + " , " + msg);
            native_onNotify(event, msg);
            LogUtil.i(LOG_TAG, "onNotify after : " + event);
        } catch (Throwable tr) {
            LogUtil.e(LOG_TAG, "onNotify failed: " + event + " , " + msg);
            tr.printStackTrace();
        }
    }

    public void onNotify(int event, long msg) {
        try {
            LogUtil.i(LOG_TAG, "onNotify: " + event + " , " + msg);
            native_onNotify(event, msg);
            LogUtil.i(LOG_TAG, "onNotify after : " + event);
        } catch (Throwable tr) {
            LogUtil.e(LOG_TAG, "onNotify failed: " + event + " , " + msg);
            tr.printStackTrace();
        }
    }

    public void onNotify(int event, Object msg) {
        try {
            LogUtil.i(LOG_TAG, "onNotify: " + event + " , " + msg);
            native_onNotify(event, msg);
            LogUtil.i(LOG_TAG, "onNotify after : " + event);
        } catch (Throwable tr) {
            LogUtil.e(LOG_TAG, "onNotify failed: " + event + " , " + msg);
            tr.printStackTrace();
        }
    }
}
