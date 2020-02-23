package com.tencent.imsdk.unity.login;

import android.os.Handler;
import android.os.HandlerThread;

public class LoginHandler {
    private static Handler handler = null;
    private static HandlerThread handlerThread;

    public static Handler getHandler() {
        if (handlerThread == null) {
            handlerThread = new HandlerThread(LoginHandler.class.getName());
        }
        if (handler == null) {
            handlerThread.start();
            handler = new Handler(handlerThread.getLooper());
        }
        return handler;
    }
}
