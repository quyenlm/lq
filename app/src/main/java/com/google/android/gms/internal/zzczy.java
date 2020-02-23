package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import java.util.List;

public final class zzczy implements zzcxo {
    public final dp<?> zzb(zzcwa zzcwa, dp<?>... dpVarArr) {
        zzbo.zzaf(dpVarArr != null);
        zzbo.zzaf(dpVarArr.length == 4);
        dp zza = ed.zza(zzcwa, (dp) dpVarArr[3]);
        zzbo.zzaf(zza instanceof dw);
        List zzDs = ((dw) zza).zzDs();
        ds dsVar = dpVarArr[2];
        zzbo.zzaf(dsVar instanceof ds);
        if (dsVar.zzDn().booleanValue()) {
            dv zza2 = ed.zza(zzcwa, (List<dp<?>>) zzDs);
            if (zza2 == dv.zzbLr) {
                return dv.zzbLu;
            }
            if (zza2.zzDr()) {
                return zza2;
            }
        }
        while (zzcxp.zza(ed.zza(zzcwa, (dp) dpVarArr[0]))) {
            dv zza3 = ed.zza(zzcwa, (List<dp<?>>) zzDs);
            if (zza3 == dv.zzbLr) {
                return dv.zzbLu;
            }
            if (zza3.zzDr()) {
                return zza3;
            }
            ed.zza(zzcwa, (dp) dpVarArr[1]);
        }
        return dv.zzbLu;
    }
}
