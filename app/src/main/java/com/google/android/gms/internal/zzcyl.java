package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import java.util.ArrayList;

public final class zzcyl extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        zzbo.zzu(dpVarArr);
        zzbo.zzaf(dpVarArr.length >= 3);
        zzbo.zzaf(dpVarArr[0] instanceof dw);
        dw dwVar = dpVarArr[0];
        int zzc = (int) zzcxp.zzc(dpVarArr[1]);
        int max = zzc < 0 ? Math.max(dwVar.zzDs().size() + zzc, 0) : Math.min(zzc, dwVar.zzDs().size());
        int min = max + Math.min(Math.max((int) zzcxp.zzc(dpVarArr[2]), 0), dwVar.zzDs().size() - max);
        ArrayList arrayList = new ArrayList(dwVar.zzDs().subList(max, min));
        dwVar.zzDs().subList(max, min).clear();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 3; i < dpVarArr.length; i++) {
            arrayList2.add(dpVarArr[i]);
        }
        dwVar.zzDs().addAll(max, arrayList2);
        return new dw(arrayList);
    }
}
