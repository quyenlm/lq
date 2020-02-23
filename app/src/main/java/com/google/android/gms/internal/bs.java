package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;

public abstract class bs extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        boolean z = true;
        zzbo.zzaf(true);
        if (dpVarArr.length != 2) {
            z = false;
        }
        zzbo.zzaf(z);
        try {
            double zzb = zzcxp.zzb(dpVarArr[0]);
            double zzb2 = zzcxp.zzb(dpVarArr[1]);
            return (Double.isNaN(zzb) || Double.isNaN(zzb2)) ? new ds(false) : new ds(Boolean.valueOf(zze(zzb, zzb2)));
        } catch (IllegalArgumentException e) {
            return new ds(false);
        }
    }

    /* access modifiers changed from: protected */
    public abstract boolean zze(double d, double d2);
}
