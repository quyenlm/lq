package com.google.android.gms.internal;

import java.lang.Thread;

final class zzzj implements Thread.UncaughtExceptionHandler {
    private /* synthetic */ Thread.UncaughtExceptionHandler zzSj;
    private /* synthetic */ zzzi zzSk;

    zzzj(zzzi zzzi, Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.zzSk = zzzi;
        this.zzSj = uncaughtExceptionHandler;
    }

    public final void uncaughtException(Thread thread, Throwable th) {
        try {
            this.zzSk.zza(thread, th);
            if (this.zzSj != null) {
                this.zzSj.uncaughtException(thread, th);
            }
        } catch (Throwable th2) {
            if (this.zzSj != null) {
                this.zzSj.uncaughtException(thread, th);
            }
            throw th2;
        }
    }
}
