package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import java.util.ArrayList;
import java.util.List;

public final class zzcxy extends zzcxq {
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
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (i < size && i < dwVar.zzDs().size()) {
            dp<?> dpVar = null;
            if (dwVar.zzbH(i)) {
                dp<?> zzb = duVar.zzDp().zzb(zzcwa, (dp) zzDs.get(i), new dt(Double.valueOf((double) i)), dwVar);
                zzbo.zzae(!ed.zzm(zzb));
                dpVar = zzb;
            }
            arrayList.add(dpVar);
            i++;
        }
        return new dw(arrayList);
    }
}
