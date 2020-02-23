package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;

public final class zzcya extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        zzbo.zzu(dpVarArr);
        zzbo.zzaf(dpVarArr.length > 0);
        zzbo.zzaf(dpVarArr[0] instanceof dw);
        dw dwVar = dpVarArr[0];
        int size = dwVar.zzDs().size();
        dwVar.setSize((dpVarArr.length + size) - 1);
        for (int i = 1; i < dpVarArr.length; i++) {
            dwVar.zza(size, dpVarArr[i]);
            size++;
        }
        return new dt(Double.valueOf((double) size));
    }
}
