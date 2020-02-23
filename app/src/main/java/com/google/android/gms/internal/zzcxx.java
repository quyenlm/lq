package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import java.util.List;

public final class zzcxx extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        int i;
        zzbo.zzu(dpVarArr);
        zzbo.zzaf(dpVarArr.length > 0 && dpVarArr.length <= 3);
        zzbo.zzaf(dpVarArr[0] instanceof dw);
        dw dwVar = dpVarArr[0];
        dv dvVar = dpVarArr.length < 2 ? dv.zzbLu : dpVarArr[1];
        List zzDs = dwVar.zzDs();
        int size = zzDs.size();
        int i2 = size - 1;
        if (dpVarArr.length == 3) {
            int zzc = (int) zzcxp.zzc(dpVarArr[2]);
            i2 = zzc < 0 ? size - Math.abs(zzc) : Math.min(zzc, size - 1);
        }
        int i3 = i2;
        while (true) {
            if (i3 >= 0) {
                if (dwVar.zzbH(i3) && zzcxp.zzd(dvVar, (dp) zzDs.get(i3))) {
                    i = i3;
                    break;
                }
                i3--;
            } else {
                i = -1;
                break;
            }
        }
        return new dt(Double.valueOf((double) i));
    }
}
