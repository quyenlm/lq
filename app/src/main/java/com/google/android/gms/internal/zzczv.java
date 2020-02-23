package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import java.util.List;

public final class zzczv extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        boolean z;
        zzbo.zzaf(true);
        zzbo.zzaf(dpVarArr.length == 3);
        zzbo.zzaf(dpVarArr[1] instanceof dw);
        zzbo.zzaf(dpVarArr[2] instanceof dw);
        dp<?> dpVar = dpVarArr[0];
        List zzDs = dpVarArr[1].zzDs();
        List zzDs2 = dpVarArr[2].zzDs();
        zzbo.zzaf(zzDs2.size() <= zzDs.size() + 1);
        int i = 0;
        boolean z2 = false;
        while (i < zzDs.size()) {
            if (z2 || zzcxp.zzd(dpVar, ed.zza(zzcwa, (dp) zzDs.get(i)))) {
                dp<?> zza = ed.zza(zzcwa, (dp) zzDs2.get(i));
                if (!(zza instanceof dv)) {
                    z = true;
                    i++;
                    z2 = z;
                } else if (zza == dv.zzbLs || ((dv) zza).zzDr()) {
                    return zza;
                } else {
                    if (zza == dv.zzbLr) {
                        return dv.zzbLu;
                    }
                }
            }
            z = z2;
            i++;
            z2 = z;
        }
        if (zzDs.size() < zzDs2.size()) {
            dp<?> zza2 = ed.zza(zzcwa, (dp) zzDs2.get(zzDs2.size() - 1));
            if ((zza2 instanceof dv) && (zza2 == dv.zzbLs || ((dv) zza2).zzDr())) {
                return zza2;
            }
        }
        return dv.zzbLu;
    }
}
