package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;

public final class t extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        boolean z = true;
        zzbo.zzaf(true);
        if (dpVarArr.length != 1) {
            z = false;
        }
        zzbo.zzaf(z);
        zzbo.zzaf(dpVarArr[0] instanceof eb);
        return new eb(dpVarArr[0].value().trim());
    }
}
