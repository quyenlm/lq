package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;

public final class zzcyx extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        boolean z = true;
        zzbo.zzaf(true);
        zzbo.zzaf(dpVarArr.length == 2);
        if (zzcxp.zzc(dpVarArr[1], dpVarArr[0])) {
            z = false;
        }
        return new ds(Boolean.valueOf(z));
    }
}
