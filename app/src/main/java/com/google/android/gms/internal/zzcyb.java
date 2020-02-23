package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import java.util.List;

public final class zzcyb extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        dp<?> zzbG;
        int i;
        zzbo.zzu(dpVarArr);
        zzbo.zzaf(dpVarArr.length == 2 || dpVarArr.length == 3);
        zzbo.zzaf(dpVarArr[0] instanceof dw);
        zzbo.zzaf(dpVarArr[1] instanceof du);
        dw dwVar = dpVarArr[0];
        du duVar = dpVarArr[1];
        List zzDs = dwVar.zzDs();
        int size = zzDs.size();
        if (dpVarArr.length == 3) {
            i = 0;
            zzbG = dpVarArr[2];
        } else {
            zzbo.zzae(size > 0);
            zzbG = dwVar.zzbG(0);
            int i2 = 1;
            int i3 = 0;
            while (true) {
                if (i3 >= size) {
                    break;
                } else if (dwVar.zzbH(i3)) {
                    zzbG = dwVar.zzbG(i3);
                    i2 = i3 + 1;
                    break;
                } else {
                    i3++;
                }
            }
            zzbo.zzae(i3 < size);
            i = i2;
        }
        int i4 = i;
        dp<?> dpVar = zzbG;
        while (i4 < size && i4 < dwVar.zzDs().size()) {
            dp<?> zzb = dwVar.zzbH(i4) ? duVar.zzDp().zzb(zzcwa, dpVar, (dp) zzDs.get(i4), new dt(Double.valueOf((double) i4)), dwVar) : dpVar;
            i4++;
            dpVar = zzb;
        }
        return dpVar;
    }
}
