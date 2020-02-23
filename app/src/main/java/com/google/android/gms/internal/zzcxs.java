package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import java.util.List;

public final class zzcxs extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        zzbo.zzu(dpVarArr);
        zzbo.zzaf(dpVarArr.length == 2);
        zzbo.zzaf(dpVarArr[0] instanceof dw);
        zzbo.zzaf(dpVarArr[1] instanceof du);
        dw dwVar = dpVarArr[0];
        du duVar = dpVarArr[1];
        List zzDs = dwVar.zzDs();
        int size = zzDs.size();
        boolean z = true;
        int i = 0;
        while (z && i < size && i < dwVar.zzDs().size()) {
            boolean zza = dwVar.zzbH(i) ? zzcxp.zza(duVar.zzDp().zzb(zzcwa, (dp) zzDs.get(i), new dt(Double.valueOf((double) i)), dwVar)) & z : z;
            i++;
            z = zza;
        }
        return new ds(Boolean.valueOf(z));
    }
}
