package com.google.android.gms.internal;

import android.os.Handler;
import com.google.android.gms.common.internal.zzbo;

abstract class zzcer {
    private static volatile Handler zzagY;
    /* access modifiers changed from: private */
    public volatile long zzagZ;
    /* access modifiers changed from: private */
    public final zzcgl zzboe;
    /* access modifiers changed from: private */
    public boolean zzbpA = true;
    private final Runnable zzv = new zzces(this);

    zzcer(zzcgl zzcgl) {
        zzbo.zzu(zzcgl);
        this.zzboe = zzcgl;
    }

    private final Handler getHandler() {
        Handler handler;
        if (zzagY != null) {
            return zzagY;
        }
        synchronized (zzcer.class) {
            if (zzagY == null) {
                zzagY = new Handler(this.zzboe.getContext().getMainLooper());
            }
            handler = zzagY;
        }
        return handler;
    }

    public final void cancel() {
        this.zzagZ = 0;
        getHandler().removeCallbacks(this.zzv);
    }

    public abstract void run();

    public final boolean zzbo() {
        return this.zzagZ != 0;
    }

    public final void zzs(long j) {
        cancel();
        if (j >= 0) {
            this.zzagZ = this.zzboe.zzkq().currentTimeMillis();
            if (!getHandler().postDelayed(this.zzv, j)) {
                this.zzboe.zzwF().zzyx().zzj("Failed to schedule delayed post. time", Long.valueOf(j));
            }
        }
    }
}
