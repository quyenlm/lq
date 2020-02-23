package com.google.android.gms.internal;

import java.util.concurrent.Callable;

public final class zzdq implements Callable {
    private final zzdb zzpJ;
    private final zzax zzro;

    public zzdq(zzdb zzdb, zzax zzax) {
        this.zzpJ = zzdb;
        this.zzro = zzax;
    }

    /* access modifiers changed from: private */
    /* renamed from: zzV */
    public final Void call() throws Exception {
        if (this.zzpJ.zzL() != null) {
            this.zzpJ.zzL().get();
        }
        zzax zzK = this.zzpJ.zzK();
        if (zzK == null) {
            return null;
        }
        try {
            synchronized (this.zzro) {
                adp.zza(this.zzro, adp.zzc(zzK));
            }
            return null;
        } catch (ado e) {
            return null;
        }
    }
}
