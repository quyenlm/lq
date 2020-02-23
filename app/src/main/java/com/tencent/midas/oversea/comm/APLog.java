package com.tencent.midas.oversea.comm;

import android.util.Log;

public class APLog {
    private static boolean a = false;

    public static void d(String str, String str2) {
        if (a) {
            Log.d("TencentPay", str + " | " + str2);
            saveLogInfo(str + " | " + str2);
        }
    }

    public static void e(String str, String str2) {
        Log.e("TencentPay", str + " | " + str2);
        saveLogInfo(str + " | " + str2);
    }

    public static String getLine() {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        int lineNumber = stackTraceElement.getLineNumber();
        return stackTraceElement.getFileName() + " Line:" + String.valueOf(lineNumber) + " ";
    }

    public static boolean getLogEnable() {
        return a;
    }

    public static void i(String str, String str2) {
        if (a) {
            Log.i("TencentPay", str + " | " + str2);
            saveLogInfo(str + " | " + str2);
        }
    }

    public static String makeLogTag(Class<?> cls) {
        return "AP_" + cls.getSimpleName();
    }

    public static void saveLogInfo(String str) {
    }

    public static void setLogEnable(boolean z) {
        a = z;
    }

    public static void v(String str, String str2) {
        if (a) {
            Log.v("TencentPay", str + " | " + str2);
            saveLogInfo(str + " | " + str2);
        }
    }

    public static void w(String str, String str2) {
        Log.w("TencentPay", str + " | " + str2);
        saveLogInfo(str + " | " + str2);
    }
}
