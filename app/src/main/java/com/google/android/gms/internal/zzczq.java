package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import java.util.ArrayList;
import java.util.List;

public final class zzczq extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        zzbo.zzaf(true);
        zzbo.zzaf(dpVarArr.length == 2 || dpVarArr.length == 3);
        zzbo.zzaf(dpVarArr[1] instanceof dw);
        if (dpVarArr.length == 3) {
            zzbo.zzaf(dpVarArr[2] instanceof dw);
        }
        List arrayList = new ArrayList();
        if (zzcxp.zza(dpVarArr[0])) {
            arrayList = dpVarArr[1].zzDs();
        } else if (dpVarArr.length > 2) {
            arrayList = dpVarArr[2].zzDs();
        }
        dv zza = ed.zza(zzcwa, (List<dp<?>>) arrayList);
        return (!(zza instanceof dv) || !ed.zzm(zza)) ? dv.zzbLu : zza;
    }
}
