package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;

public final class f extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        zzbo.zzaf(true);
        zzbo.zzaf(dpVarArr.length > 0);
        zzbo.zzaf(dpVarArr[0] instanceof eb);
        StringBuilder sb = new StringBuilder(dpVarArr[0].value());
        for (int i = 1; i < dpVarArr.length; i++) {
            sb.append(zzcxp.zzd(dpVarArr[i]));
        }
        return new eb(sb.toString());
    }
}
