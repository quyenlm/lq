package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public final class bg extends zzcxq {
    private static final dt zzbKg = new dt(Double.valueOf(FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE));
    private static final dt zzbKh = new dt(Double.valueOf(2.147483647E9d));

    private static boolean zzg(dp<?> dpVar) {
        return (dpVar instanceof dt) && !Double.isNaN(((dt) dpVar).zzDo().doubleValue());
    }

    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        double d;
        zzbo.zzaf(true);
        double d2 = FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        dt dtVar = dpVarArr.length > 0 ? dpVarArr[0] : zzbKg;
        dt dtVar2 = dpVarArr.length > 1 ? dpVarArr[1] : zzbKh;
        if (!zzg(dtVar) || !zzg(dtVar2) || !zzcxp.zzb(dtVar, dtVar2)) {
            d = 2.147483647E9d;
        } else {
            double doubleValue = dtVar.zzDo().doubleValue();
            d = dtVar2.zzDo().doubleValue();
            d2 = doubleValue;
        }
        return new dt(Double.valueOf((double) Math.round(((d - d2) * Math.random()) + d2)));
    }
}
