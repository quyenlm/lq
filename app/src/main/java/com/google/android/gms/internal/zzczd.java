package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import java.util.List;

public final class zzczd extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        zzbo.zzaf(true);
        zzbo.zzaf(dpVarArr.length == 3);
        zzbo.zzaf(dpVarArr[1] instanceof eb);
        zzbo.zzaf(dpVarArr[2] instanceof dw);
        dp<?> dpVar = dpVarArr[0];
        String value = dpVarArr[1].value();
        List zzDs = dpVarArr[2].zzDs();
        if (dpVar.zzfY(value)) {
            dp<?> zzfZ = dpVar.zzfZ(value);
            if (zzfZ instanceof du) {
                return ((du) zzfZ).zzDp().zzb(zzcwa, (dp[]) zzDs.toArray(new dp[zzDs.size()]));
            }
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(value).length() + 35).append("Apply TypeError: ").append(value).append(" is not a function").toString());
        } else if (dpVar.zzga(value)) {
            zzcxo zzgb = dpVar.zzgb(value);
            zzDs.add(0, dpVar);
            return zzgb.zzb(zzcwa, (dp[]) zzDs.toArray(new dp[zzDs.size()]));
        } else {
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(value).length() + 40).append("Apply TypeError: object has no ").append(value).append(" property").toString());
        }
    }
}
