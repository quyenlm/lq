package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import java.util.List;

public final class zzcxv extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        int i;
        int i2 = 0;
        zzbo.zzu(dpVarArr);
        zzbo.zzaf(dpVarArr.length > 0 && dpVarArr.length <= 3);
        zzbo.zzaf(dpVarArr[0] instanceof dw);
        dw dwVar = dpVarArr[0];
        dv dvVar = dpVarArr.length < 2 ? dv.zzbLu : dpVarArr[1];
        dv dvVar2 = dpVarArr.length < 3 ? dv.zzbLu : dpVarArr[2];
        List zzDs = dwVar.zzDs();
        int size = zzDs.size();
        if (dvVar2 != dv.zzbLu) {
            int zzc = (int) zzcxp.zzc(dvVar2);
            i2 = zzc < 0 ? Math.max(size - Math.abs(zzc), 0) : zzc;
        }
        int i3 = i2;
        while (true) {
            if (i3 < size) {
                if (dwVar.zzbH(i3) && zzcxp.zzd(dvVar, (dp) zzDs.get(i3))) {
                    i = i3;
                    break;
                }
                i3++;
            } else {
                i = -1;
                break;
            }
        }
        return new dt(Double.valueOf((double) i));
    }
}
