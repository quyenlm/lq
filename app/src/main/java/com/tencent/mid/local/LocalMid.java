package com.tencent.mid.local;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

public class LocalMid {
    private static Context a = null;
    private static Handler b = null;
    private static LocalMid c = null;

    private LocalMid(Context context) {
        HandlerThread handlerThread = new HandlerThread(LocalMid.class.getSimpleName());
        handlerThread.start();
        b = new Handler(handlerThread.getLooper());
        a = context.getApplicationContext();
    }

    public static LocalMid getInstance(Context context) {
        if (c == null) {
            synchronized (LocalMid.class) {
                if (c == null) {
                    c = new LocalMid(context);
                }
            }
        }
        return c;
    }

    public String getLocalMid() {
        return h.a(a).a().c;
    }

    public boolean isMidValid(String str) {
        return i.c(str);
    }
}
