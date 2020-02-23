package com.tsf4g.tx;

import android.util.Log;

public class TXLog {
    public static final int DEBUG = 2;
    public static final int ERROR = 5;
    public static final int INFO = 3;
    private static int LEVEL = 1;
    public static final int NONE = 6;
    public static final int VERBOSE = 1;
    public static final int WARNING = 4;

    private TXLog() {
    }

    public static void d(String str, String str2) {
        if (LEVEL <= 2) {
            Log.d(str, str2);
        }
    }

    public static void e(String str, String str2) {
        if (LEVEL <= 5) {
            Log.e(str, str2);
        }
    }

    public static void i(String str, String str2) {
        if (LEVEL <= 3) {
            Log.i(str, str2);
        }
    }

    public static void setLogLevel(int i) {
        LEVEL = i;
    }

    public static void v(String str, String str2) {
        if (LEVEL <= 1) {
            Log.v(str, str2);
        }
    }

    public static void w(String str, String str2) {
        if (LEVEL <= 4) {
            Log.w(str, str2);
        }
    }
}
