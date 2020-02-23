package com.tencent.ngame.utility;

import android.util.Log;

public class DebugLog {
    public static final int DEBUG = 3;
    public static final int ERROR = 6;
    public static final int INFO = 4;
    public static final int VERBOSE = 2;
    public static final int WARN = 5;
    protected static int mLogLevel = 5;

    public static int getLogLevel() {
        return mLogLevel;
    }

    public static void setLogLevel(int logLevel) {
        mLogLevel = logLevel;
    }

    private static boolean isLoggable(int level) {
        return mLogLevel <= level;
    }

    public static void v(String tag, String msg) {
        if (isLoggable(2)) {
            Log.d(tag, msg);
        }
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (isLoggable(2)) {
            Log.d(tag, msg, tr);
        }
    }

    public static void d(String tag, String msg) {
        if (isLoggable(3)) {
            Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (isLoggable(3)) {
            Log.d(tag, msg, tr);
        }
    }

    public static void i(String tag, String msg) {
        if (isLoggable(4)) {
            Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (isLoggable(4)) {
            Log.d(tag, msg, tr);
        }
    }

    public static void w(String tag, String msg) {
        if (isLoggable(5)) {
            Log.w(tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (isLoggable(5)) {
            Log.d(tag, msg, tr);
        }
    }

    public static void e(String tag, String msg) {
        if (isLoggable(6)) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (isLoggable(6)) {
            Log.d(tag, msg, tr);
        }
    }

    public static String getStackTraceString(Throwable tr) {
        return Log.getStackTraceString(tr);
    }
}
