package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;

public final class e extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        zzbo.zzaf(true);
        zzbo.zzaf(dpVarArr.length == 1 || dpVarArr.length == 2);
        zzbo.zzaf(dpVarArr[0] instanceof eb);
        String value = dpVarArr[0].value();
        int zzc = dpVarArr.length == 2 ? (int) zzcxp.zzc(dpVarArr[1]) : 0;
        return (zzc < 0 || zzc >= value.length()) ? new eb("") : new eb(String.valueOf(value.charAt(zzc)));
    }
}
