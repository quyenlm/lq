package com.google.android.gms.analytics;

import android.util.Log;
import com.google.android.gms.analytics.zzl;
import java.lang.Thread;
import java.util.concurrent.FutureTask;

final class zzn extends FutureTask<T> {
    private /* synthetic */ zzl.zza zzael;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzn(zzl.zza zza, Runnable runnable, Object obj) {
        super(runnable, obj);
        this.zzael = zza;
    }

    /* access modifiers changed from: protected */
    public final void setException(Throwable th) {
        Thread.UncaughtExceptionHandler zzb = zzl.this.zzaei;
        if (zzb != null) {
            zzb.uncaughtException(Thread.currentThread(), th);
        } else if (Log.isLoggable("GAv4", 6)) {
            String valueOf = String.valueOf(th);
            Log.e("GAv4", new StringBuilder(String.valueOf(valueOf).length() + 37).append("MeasurementExecutor: job failed with ").append(valueOf).toString());
        }
        super.setException(th);
    }
}
