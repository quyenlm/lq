package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.js.zzai;
import java.lang.ref.WeakReference;

final class zzog {
    /* access modifiers changed from: private */
    public final WeakReference<zzaka> zzIh;
    /* access modifiers changed from: private */
    public String zzIi;

    public zzog(zzaka zzaka) {
        this.zzIh = new WeakReference<>(zzaka);
    }

    /* access modifiers changed from: private */
    public final zzrd zze(zzai zzai) {
        return new zzoh(this, zzai);
    }

    /* access modifiers changed from: private */
    public final zzrd zzf(zzai zzai) {
        return new zzoj(this, zzai);
    }

    /* access modifiers changed from: private */
    public final zzrd zzg(zzai zzai) {
        return new zzok(this, zzai);
    }

    /* access modifiers changed from: private */
    public final zzrd zzh(zzai zzai) {
        return new zzol(this, zzai);
    }
}
