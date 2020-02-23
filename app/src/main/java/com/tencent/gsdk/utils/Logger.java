package com.tencent.gsdk.utils;

import android.util.Log;

public class Logger {
    private static String TAG = "gsdk";
    public static boolean isDebug = false;
    private static ILogObserver sLogObserver = null;

    public interface ILogObserver {
        void onLog(String str);
    }

    public static void setLogObserver(ILogObserver iLogObserver) {
        sLogObserver = iLogObserver;
    }

    public static void d(String str) {
        d(str, (Throwable) null);
    }

    public static void d(String str, Throwable th) {
        if (isDebug) {
            log(3, str, th);
        }
    }

    public static void i(String str) {
        i(str, (Throwable) null);
    }

    public static void i(String str, Throwable th) {
        if (isDebug) {
            log(4, str, th);
        }
    }

    public static void w(String str) {
        w(str, (Throwable) null);
    }

    public static void w(String str, Throwable th) {
        if (isDebug) {
            log(5, str, th);
        }
    }

    public static void e(String str) {
        e(str, (Throwable) null);
    }

    public static void e(String str, Throwable th) {
        if (isDebug) {
            log(6, str, th);
        }
    }

    private static void log(int i, String str, Throwable th) {
        if (th != null) {
            str = str + "\n" + Log.getStackTraceString(th);
        }
        if (sLogObserver != null) {
            sLogObserver.onLog(str);
        }
        Log.println(i, TAG, str);
    }
}
