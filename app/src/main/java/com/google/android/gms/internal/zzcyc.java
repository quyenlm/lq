package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import java.util.List;

public final class zzcyc extends zzcxq {
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
            i = size - 1;
            zzbG = dpVarArr[2];
        } else {
            zzbo.zzae(size > 0);
            zzbG = dwVar.zzbG(size - 1);
            int i2 = size - 2;
            int i3 = size - 1;
            while (true) {
                if (i3 < 0) {
                    break;
                } else if (dwVar.zzbH(i3)) {
                    zzbG = dwVar.zzbG(i3);
                    i2 = i3 - 1;
                    break;
                } else {
                    i3--;
                }
            }
            zzbo.zzae(i3 >= 0);
            i = i2;
        }
        int i4 = i;
        dp<?> dpVar = zzbG;
        while (i4 >= 0) {
            dp<?> zzb = dwVar.zzbH(i4) ? duVar.zzDp().zzb(zzcwa, dpVar, (dp) zzDs.get(i4), new dt(Double.valueOf((double) i4)), dwVar) : dpVar;
            i4--;
            dpVar = zzb;
        }
        return dpVar;
    }
}
