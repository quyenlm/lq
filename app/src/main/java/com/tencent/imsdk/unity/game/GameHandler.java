package com.tencent.imsdk.unity.game;

import android.os.Handler;
import android.os.HandlerThread;

public class GameHandler {
    private static Handler handler = null;
    private static HandlerThread handlerThread = new HandlerThread(GameHandler.class.getName());

    public static Handler getHandler() {
        if (handler == null) {
            handlerThread.start();
            handler = new Handler(handlerThread.getLooper());
        }
        return handler;
    }
}
