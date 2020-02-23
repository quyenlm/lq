package com.google.android.gms.internal;

import java.util.concurrent.atomic.AtomicReference;

public abstract class zzcal {
    private final AtomicReference<zzcaj> zzbbL = new AtomicReference<>();

    public final void flush() {
        zzcaj zzcaj = this.zzbbL.get();
        if (zzcaj != null) {
            zzcaj.flush();
        }
    }

    public final void zzn(String str, int i) {
        zzcaj zzcaj = this.zzbbL.get();
        if (zzcaj == null) {
            zzcaj = zzuQ();
            if (!this.zzbbL.compareAndSet((Object) null, zzcaj)) {
                zzcaj = this.zzbbL.get();
            }
        }
        zzcaj.zzr(str, i);
    }

    /* access modifiers changed from: protected */
    public abstract zzcaj zzuQ();
}
