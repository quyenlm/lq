package com.tencent.component.net.download.multiplex;

import com.tencent.component.utils.log.LogUtil;

public class DownloaderLog {
    public static void v(String tag, String msg) {
        LogUtil.v(tag, msg);
    }

    public static void v(String tag, String msg, Throwable tr) {
        LogUtil.v(tag, msg, tr);
    }

    public static void d(String tag, String msg) {
        LogUtil.d(tag, msg);
    }

    public static void d(String tag, String msg, Throwable tr) {
        LogUtil.d(tag, msg, tr);
    }

    public static void i(String tag, String msg) {
        LogUtil.i(tag, msg);
    }

    public static void i(String tag, String msg, Throwable tr) {
        LogUtil.i(tag, msg, tr);
    }

    public static void w(String tag, String msg) {
        LogUtil.w(tag, msg);
    }

    public static void w(String tag, String msg, Throwable tr) {
        LogUtil.w(tag, msg, tr);
    }

    public static void w(String tag, Throwable tr) {
        LogUtil.w(tag, tr);
    }

    public static void e(String tag, String msg) {
        LogUtil.e(tag, msg);
    }

    public static void e(String tag, String msg, Throwable tr) {
        LogUtil.e(tag, msg, tr);
    }
}
