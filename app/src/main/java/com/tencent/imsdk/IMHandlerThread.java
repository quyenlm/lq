package com.tencent.imsdk;

import android.os.Handler;
import android.os.HandlerThread;
import com.tencent.imsdk.tool.etc.IMLogger;

public class IMHandlerThread {
    private static Handler handler = null;
    private static HandlerThread handlerThread;

    public static Handler getHandler() {
        if (handlerThread == null) {
            handlerThread = new HandlerThread(IMHandlerThread.class.getName(), 10);
        }
        if (handler == null) {
            IMLogger.d("start a handler thread ...");
            handlerThread.start();
            for (int counter = 0; counter < 10 && !handlerThread.isAlive(); counter++) {
                IMLogger.d("handler thread is not alive ...");
                handlerThread.run();
            }
            IMLogger.d("getting handler ...");
            handler = new Handler(handlerThread.getLooper());
            if (handler == null) {
                IMLogger.e("get handler error ...");
            }
        }
        if (handler != null) {
            IMLogger.d("get handler ...");
        } else {
            IMLogger.e("handler is null !");
        }
        return handler;
    }
}
