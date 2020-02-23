package com.tencent.ieg.ntv.utils;

import android.util.Log;

public class Logger {
    private static boolean mEnable = true;
    private static int onceLen = 2048;

    public static void setEnable(boolean enable) {
        mEnable = enable;
    }

    public static boolean getEnable() {
        return mEnable;
    }

    public static void d(String tag, String msg) {
        if (mEnable) {
            while (msg.length() >= onceLen) {
                Log.d(tag, msg.substring(0, onceLen));
                msg = msg.substring(onceLen);
            }
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (mEnable) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (mEnable) {
            Log.w(tag, msg);
        }
    }

    public static void w(String tag, Exception e) {
        if (mEnable) {
            Log.e(tag, "", e);
        }
    }

    public static void e(String tag, String msg) {
        if (mEnable) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, Exception e) {
        if (mEnable) {
            Log.e(tag, "", e);
        }
    }

    public static void printStackTrace(Exception e) {
        if (mEnable) {
            e.printStackTrace();
        }
    }
}
