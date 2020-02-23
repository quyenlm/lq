package com.tencent.msdk.stat.common;

import android.util.Log;
import com.tencent.tp.a.h;

public final class StatLogger {
    private String a = "default";
    private boolean b = true;
    private int c = 2;

    public StatLogger() {
    }

    public StatLogger(String str) {
        this.a = str;
    }

    private String a() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace == null) {
            return null;
        }
        for (StackTraceElement stackTraceElement : stackTrace) {
            if (!stackTraceElement.isNativeMethod() && !stackTraceElement.getClassName().equals(Thread.class.getName()) && !stackTraceElement.getClassName().equals(getClass().getName())) {
                return "[" + Thread.currentThread().getName() + h.a + Thread.currentThread().getId() + "): " + stackTraceElement.getFileName() + ":" + stackTraceElement.getLineNumber() + "]";
            }
        }
        return null;
    }

    public void d(Object obj) {
        if (isDebugEnable()) {
            debug(obj);
        }
    }

    public void debug(Object obj) {
        if (this.c <= 3) {
            String a2 = a();
            Log.d(this.a, a2 == null ? obj.toString() : a2 + " - " + obj);
        }
    }

    public void e(Object obj) {
        if (isDebugEnable()) {
            error(obj);
        }
    }

    public void e(Throwable th) {
        if (isDebugEnable()) {
            error(th);
        }
    }

    public void error(Object obj) {
        if (this.c <= 6) {
            String a2 = a();
            Log.e(this.a, a2 == null ? obj.toString() : a2 + " - " + obj);
        }
    }

    public void error(Throwable th) {
        if (this.c <= 6) {
            Log.e(this.a, "", th);
        }
    }

    public int getLogLevel() {
        return this.c;
    }

    public void i(Object obj) {
        if (isDebugEnable()) {
            info(obj);
        }
    }

    public void info(Object obj) {
        if (this.c <= 4) {
            String a2 = a();
            Log.i(this.a, a2 == null ? obj.toString() : a2 + " - " + obj);
        }
    }

    public boolean isDebugEnable() {
        return this.b;
    }

    public void setDebugEnable(boolean z) {
        this.b = z;
    }

    public void setLogLevel(int i) {
        this.c = i;
    }

    public void setTag(String str) {
        this.a = str;
    }

    public void v(Object obj) {
        if (isDebugEnable()) {
            verbose(obj);
        }
    }

    public void verbose(Object obj) {
        if (this.c <= 2) {
            String a2 = a();
            Log.v(this.a, a2 == null ? obj.toString() : a2 + " - " + obj);
        }
    }

    public void w(Object obj) {
        if (isDebugEnable()) {
            warn(obj);
        }
    }

    public void warn(Object obj) {
        if (this.c <= 5) {
            String a2 = a();
            Log.w(this.a, a2 == null ? obj.toString() : a2 + " - " + obj);
        }
    }
}
