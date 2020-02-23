package com.tencent.qt.base.net;

import android.text.TextUtils;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

public class PLog {
    public static final int LL_DEBUG = 1;
    public static final int LL_ERROR = 4;
    public static final int LL_INFO = 2;
    public static final int LL_VERBOSE = 0;
    public static final int LL_WARN = 3;
    private static boolean debug = false;

    public enum StoreMode {
        none,
        fixed,
        flexible
    }

    public enum TraceMode {
        none,
        realtime,
        offline,
        all
    }

    private static native void native_debug(boolean z, int i);

    private static native void native_log(int i, String str, String str2);

    private static native boolean native_trace(int i, String str);

    static {
        GlobalPref.getInstant().loadLibary();
    }

    protected static void enableLog(boolean d, int level) {
        debug = d;
        try {
            native_debug(d, level);
        } catch (UnsatisfiedLinkError e) {
        }
    }

    protected static boolean trace(TraceMode mode, StoreMode sm, String path) {
        if (!debug) {
            throw new IllegalStateException("you should enable log before modifing trace mode");
        }
        if (mode.equals(TraceMode.offline) || mode.equals(TraceMode.all)) {
            if (TextUtils.isEmpty(path)) {
                throw new IllegalArgumentException("path should not be null for offline and all mode");
            }
            File dir = new File(path);
            if ((!dir.exists() || !dir.isDirectory()) && !dir.mkdirs()) {
                return false;
            }
        }
        try {
            native_trace((sm.ordinal() << 4) | mode.ordinal(), path);
            return false;
        } catch (UnsatisfiedLinkError e) {
            return false;
        }
    }

    private static String buildWholeMessage(String format, Object... args) {
        if (args == null || args.length == 0) {
            return format;
        }
        return String.format(format, args);
    }

    public static void v(String tag, String format, Object... args) {
        if (debug) {
            try {
                native_log(0, tag, buildWholeMessage(format, args));
            } catch (UnsatisfiedLinkError e) {
            }
        }
    }

    public static void i(String tag, String format, Object... args) {
        if (debug) {
            try {
                native_log(2, tag, buildWholeMessage(format, args));
            } catch (UnsatisfiedLinkError e) {
            }
        }
    }

    public static void d(String tag, String format, Object... args) {
        if (debug) {
            try {
                native_log(1, tag, buildWholeMessage(format, args));
            } catch (UnsatisfiedLinkError e) {
            }
        }
    }

    public static void w(String tag, String format, Object... args) {
        try {
            native_log(3, tag, buildWholeMessage(format, args));
        } catch (UnsatisfiedLinkError e) {
        }
    }

    public static void e(String tag, String format, Object... args) {
        try {
            native_log(4, tag, buildWholeMessage(format, args));
        } catch (UnsatisfiedLinkError e) {
        }
    }

    private static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }
        StringWriter sw = new StringWriter();
        tr.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    public static void e(String tag, Throwable e) {
        if (debug) {
            e(tag, getStackTraceString(e), new Object[0]);
        }
    }

    public static void printStackTrace(Throwable e) {
        if (debug) {
            e("VideoException", e);
        }
    }
}
