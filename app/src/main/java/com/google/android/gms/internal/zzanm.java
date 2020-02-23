package com.google.android.gms.internal;

import android.os.Handler;
import com.google.android.gms.common.internal.zzbo;

abstract class zzanm {
    private static volatile Handler zzagY;
    /* access modifiers changed from: private */
    public final zzamj zzafJ;
    /* access modifiers changed from: private */
    public volatile long zzagZ;
    private final Runnable zzv = new zzann(this);

    zzanm(zzamj zzamj) {
        zzbo.zzu(zzamj);
        this.zzafJ = zzamj;
    }

    private final Handler getHandler() {
        Handler handler;
        if (zzagY != null) {
            return zzagY;
        }
        synchronized (zzanm.class) {
            if (zzagY == null) {
                zzagY = new Handler(this.zzafJ.getContext().getMainLooper());
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

    public final long zzlz() {
        if (this.zzagZ == 0) {
            return 0;
        }
        return Math.abs(this.zzafJ.zzkq().currentTimeMillis() - this.zzagZ);
    }

    public final void zzs(long j) {
        cancel();
        if (j >= 0) {
            this.zzagZ = this.zzafJ.zzkq().currentTimeMillis();
            if (!getHandler().postDelayed(this.zzv, j)) {
                this.zzafJ.zzkr().zze("Failed to schedule delayed post. time", Long.valueOf(j));
            }
        }
    }

    public final void zzt(long j) {
        long j2 = 0;
        if (zzbo()) {
            if (j < 0) {
                cancel();
                return;
            }
            long abs = j - Math.abs(this.zzafJ.zzkq().currentTimeMillis() - this.zzagZ);
            if (abs >= 0) {
                j2 = abs;
            }
            getHandler().removeCallbacks(this.zzv);
            if (!getHandler().postDelayed(this.zzv, j2)) {
                this.zzafJ.zzkr().zze("Failed to adjust delayed post. time", Long.valueOf(j2));
            }
        }
    }
}
