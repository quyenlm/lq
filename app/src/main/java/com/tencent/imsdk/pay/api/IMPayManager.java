package com.tencent.imsdk.pay.api;

import android.os.Handler;
import android.os.Looper;

public class IMPayManager {
    private static Object lock = new Object();
    private static IMPayManager payManager;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public static IMPayManager getInstance() {
        IMPayManager iMPayManager;
        synchronized (lock) {
            if (payManager == null) {
                payManager = new IMPayManager();
            }
            iMPayManager = payManager;
        }
        return iMPayManager;
    }

    public Handler getMainHandler() {
        return this.mHandler;
    }
}
