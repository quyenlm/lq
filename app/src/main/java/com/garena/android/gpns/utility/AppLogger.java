package com.garena.android.gpns.utility;

import android.util.Log;
import java.lang.Thread;

public final class AppLogger {
    private static final String APP_LOG_FLAG = "GPNS MSDK";
    public static boolean ENABLED = false;

    public static abstract class NetworkThreadExceptionHandler implements Thread.UncaughtExceptionHandler {
        public void uncaughtException(Thread thread, Throwable ex) {
            AppLogger.i("NETWORK THREAD CRASH");
            AppLogger.e(ex);
        }
    }

    public static void e(Throwable e) {
        if (ENABLED) {
            Log.e(APP_LOG_FLAG, e.getMessage(), e);
        }
    }

    public static void e(String s) {
        if (ENABLED) {
            Log.e(APP_LOG_FLAG, s);
        }
    }

    public static void i(String s) {
        if (ENABLED) {
            Log.i(APP_LOG_FLAG, s);
        }
    }

    public static void d(String s) {
        if (ENABLED) {
            Log.d(APP_LOG_FLAG, s);
        }
    }

    public static void f(String s) {
    }

    private AppLogger() {
    }
}
