package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;

public final class zzcyw extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        boolean z = true;
        zzbo.zzaf(true);
        zzbo.zzaf(dpVarArr.length == 1);
        if (zzcxp.zza(dpVarArr[0])) {
            z = false;
        }
        return new ds(Boolean.valueOf(z));
    }
}
