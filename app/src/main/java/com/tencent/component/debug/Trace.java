package com.tencent.component.debug;

public class Trace implements TraceLevel {
    private static volatile Tracer systemTracer = new LogcatTracer();

    public static void v(String tag, String msg) {
        v(tag, msg, (Throwable) null);
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (systemTracer != null) {
            systemTracer.trace(1, Thread.currentThread(), System.currentTimeMillis(), tag, msg, tr);
        }
    }

    public static void d(String tag, String msg) {
        d(tag, msg, (Throwable) null);
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (systemTracer != null) {
            systemTracer.trace(2, Thread.currentThread(), System.currentTimeMillis(), tag, msg, tr);
        }
    }

    public static void i(String tag, String msg) {
        i(tag, msg, (Throwable) null);
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (systemTracer != null) {
            systemTracer.trace(4, Thread.currentThread(), System.currentTimeMillis(), tag, msg, tr);
        }
    }

    public static void w(String tag, String msg) {
        w(tag, msg, (Throwable) null);
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (systemTracer != null) {
            systemTracer.trace(8, Thread.currentThread(), System.currentTimeMillis(), tag, msg, tr);
        }
    }

    public static void e(String tag, String msg) {
        e(tag, msg, (Throwable) null);
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (systemTracer != null) {
            systemTracer.trace(16, Thread.currentThread(), System.currentTimeMillis(), tag, msg, tr);
        }
    }

    public static void setSystemTracer(Tracer tracer) {
        systemTracer = tracer;
    }

    public static Tracer getSystemTracer() {
        return systemTracer;
    }
}
