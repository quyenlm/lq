package com.tencent.component.debug;

import android.util.Log;

public final class LogcatTracer extends Tracer {
    public static final LogcatTracer Instance = new LogcatTracer();

    /* access modifiers changed from: protected */
    public void doTrace(int level, Thread thread, long time, String tag, String msg, Throwable tr) {
        switch (level) {
            case 1:
                Log.v(tag, msg, tr);
                return;
            case 2:
                Log.d(tag, msg, tr);
                return;
            case 4:
                Log.i(tag, msg, tr);
                return;
            case 8:
                Log.w(tag, msg, tr);
                return;
            case 16:
                Log.e(tag, msg, tr);
                return;
            case 32:
                Log.e(tag, msg, tr);
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void doTrace(String formattedTrace) {
        Log.v("", formattedTrace);
    }
}
