package com.tencent.component.utils.thread;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

public class HandlerUtils {
    private static volatile HandlerThread handerThread = null;
    private static Handler uiHandler = new Handler(Looper.getMainLooper());

    public static Looper getNUILooper() {
        if (handerThread == null) {
            synchronized (HandlerUtils.class) {
                if (handerThread == null) {
                    handerThread = new HandlerThread("TheadUtils.handerThread");
                    handerThread.start();
                }
            }
        }
        return handerThread.getLooper();
    }

    public static void runUITask(Runnable runnable) {
        uiHandler.post(runnable);
    }

    public static void runUITask(Runnable runnable, long delayTime) {
        uiHandler.postDelayed(runnable, delayTime);
    }
}
