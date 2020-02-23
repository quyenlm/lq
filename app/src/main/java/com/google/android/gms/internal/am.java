package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;

public final class am implements zzcxo {
    private final dp<?> zzbKa;

    public am(dp<?> dpVar) {
        this.zzbKa = (dp) zzbo.zzu(dpVar);
    }

    public final dp<?> zzb(zzcwa zzcwa, dp<?>... dpVarArr) {
        boolean z = true;
        zzbo.zzaf(dpVarArr != null);
        if (dpVarArr.length != 0) {
            z = false;
        }
        zzbo.zzaf(z);
        return this.zzbKa;
    }
}
