package com.tencent.tdm.system;

import android.util.Log;

public class TXLog {
    private static int LEVEL = 1;

    private TXLog() {
    }

    public static void setLogLevel(int level) {
        LEVEL = level;
    }

    public static int getLogLevel() {
        return LEVEL;
    }

    public static void d(String tag, String message) {
        if (LEVEL <= 0) {
            Log.d(tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (LEVEL <= 1) {
            Log.i(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (LEVEL <= 2) {
            Log.w(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (LEVEL <= 3) {
            Log.e(tag, message);
        }
    }
}
