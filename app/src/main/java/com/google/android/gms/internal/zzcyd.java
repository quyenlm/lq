package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import java.util.Collections;

public final class zzcyd extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        boolean z = true;
        zzbo.zzu(dpVarArr);
        if (dpVarArr.length != 1) {
            z = false;
        }
        zzbo.zzaf(z);
        zzbo.zzaf(dpVarArr[0] instanceof dw);
        Collections.reverse(dpVarArr[0].zzDs());
        return dpVarArr[0];
    }
}
