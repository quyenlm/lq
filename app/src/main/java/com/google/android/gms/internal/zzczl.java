package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import java.util.Iterator;
import java.util.List;

public final class zzczl extends zzcxq {
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        zzbo.zzu(dpVarArr);
        zzbo.zzaf(dpVarArr.length == 3);
        zzbo.zzaf(dpVarArr[0] instanceof eb);
        String value = dpVarArr[0].value();
        zzbo.zzaf(zzcwa.has(value));
        dp<?> dpVar = dpVarArr[1];
        zzbo.zzu(dpVar);
        dw dwVar = dpVarArr[2];
        zzbo.zzaf(dwVar instanceof dw);
        List zzDs = dwVar.zzDs();
        Iterator<dp<?>> zzDk = dpVar.zzDk();
        while (zzDk.hasNext()) {
            zzcwa.zzb(value, zzDk.next());
            dv zza = ed.zza(zzcwa, (List<dp<?>>) zzDs);
            if (zza == dv.zzbLr) {
                return dv.zzbLu;
            }
            if (zza.zzDr()) {
                return zza;
            }
        }
        return dv.zzbLu;
    }
}
