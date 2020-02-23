package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import java.util.HashMap;
import java.util.Map;

public final class z extends zzcxq {
    private final aa zzbJX;

    public z(aa aaVar) {
        this.zzbJX = aaVar;
    }

    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        zzbo.zzu(dpVarArr);
        zzbo.zzaf(dpVarArr.length > 0);
        zzbo.zzaf(dpVarArr[0] instanceof eb);
        String value = dpVarArr[0].value();
        HashMap hashMap = new HashMap();
        if (dpVarArr.length >= 2 && dpVarArr[1] != dv.zzbLu) {
            zzbo.zzaf(dpVarArr[1] instanceof dz);
            for (Map.Entry entry : dpVarArr[1].zzDt().entrySet()) {
                zzbo.zzae(!(entry.getValue() instanceof ea));
                zzbo.zzae(!ed.zzm((dp) entry.getValue()));
                hashMap.put((String) entry.getKey(), ((dp) entry.getValue()).zzDl());
            }
        }
        return ed.zzQ(this.zzbJX.zzd(value, hashMap));
    }
}
