package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import java.util.ArrayList;

public final class zzcyf extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        zzbo.zzu(dpVarArr);
        zzbo.zzaf(dpVarArr.length == 2 || dpVarArr.length == 3);
        zzbo.zzaf(dpVarArr[0] instanceof dw);
        dw dwVar = dpVarArr[0];
        int zzc = (int) zzcxp.zzc(dpVarArr[1]);
        int max = zzc < 0 ? Math.max(dwVar.zzDs().size() + zzc, 0) : Math.min(zzc, dwVar.zzDs().size());
        int size = dwVar.zzDs().size();
        if (dpVarArr.length == 3) {
            int zzc2 = (int) zzcxp.zzc(dpVarArr[2]);
            size = zzc2 < 0 ? Math.max(dwVar.zzDs().size() + zzc2, 0) : Math.min(zzc2, dwVar.zzDs().size());
        }
        return new dw(new ArrayList(dwVar.zzDs().subList(max, Math.max(max, size))));
    }
}
