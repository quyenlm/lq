package com.google.android.gms.internal;

import java.lang.Thread;

final class zzzk implements Thread.UncaughtExceptionHandler {
    private /* synthetic */ zzzi zzSk;
    private /* synthetic */ Thread.UncaughtExceptionHandler zzSl;

    zzzk(zzzi zzzi, Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.zzSk = zzzi;
        this.zzSl = uncaughtExceptionHandler;
    }

    public final void uncaughtException(Thread thread, Throwable th) {
        try {
            this.zzSk.zza(thread, th);
            if (this.zzSl != null) {
                this.zzSl.uncaughtException(thread, th);
            }
        } catch (Throwable th2) {
            if (this.zzSl != null) {
                this.zzSl.uncaughtException(thread, th);
            }
            throw th2;
        }
    }
}
