package com.tencent.component.debug;

import com.tencent.component.utils.BitUtils;

public abstract class Tracer {
    private volatile boolean enabled;
    private TraceFormat traceFormat;
    private volatile int traceLevel;

    /* access modifiers changed from: protected */
    public abstract void doTrace(int i, Thread thread, long j, String str, String str2, Throwable th);

    /* access modifiers changed from: protected */
    public abstract void doTrace(String str);

    public Tracer() {
        this(63, true, TraceFormat.DEFAULT);
    }

    public Tracer(int level, boolean enable, TraceFormat format) {
        this.traceLevel = 63;
        this.enabled = true;
        this.traceFormat = TraceFormat.DEFAULT;
        setTraceLevel(level);
        setEnabled(enable);
        setTraceFormat(format);
    }

    public void trace(int level, Thread thread, long time, String tag, String msg, Throwable tr) {
        if (isEnabled() && BitUtils.has(this.traceLevel, level)) {
            doTrace(level, thread, time, tag, msg, tr);
        }
    }

    public void trace(int level, String formattedTrace) {
        if (isEnabled() && BitUtils.has(this.traceLevel, level)) {
            doTrace(formattedTrace);
        }
    }

    public int getTraceLevel() {
        return this.traceLevel;
    }

    public void setTraceLevel(int traceLevel2) {
        this.traceLevel = traceLevel2;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled2) {
        this.enabled = enabled2;
    }

    public TraceFormat getTraceFormat() {
        return this.traceFormat;
    }

    public void setTraceFormat(TraceFormat traceFormat2) {
        this.traceFormat = traceFormat2;
    }
}
