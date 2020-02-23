package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import java.util.List;

public final class zzcye extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        boolean z = true;
        zzbo.zzu(dpVarArr);
        if (dpVarArr.length != 1) {
            z = false;
        }
        zzbo.zzaf(z);
        zzbo.zzaf(dpVarArr[0] instanceof dw);
        dv dvVar = dv.zzbLu;
        List zzDs = dpVarArr[0].zzDs();
        return !zzDs.isEmpty() ? (dp) zzDs.remove(0) : dvVar;
    }
}
