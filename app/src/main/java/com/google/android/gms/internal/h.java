package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public final class h extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        zzbo.zzaf(true);
        zzbo.zzaf(dpVarArr.length == 2 || dpVarArr.length == 3);
        zzbo.zzaf(dpVarArr[0] instanceof eb);
        String value = dpVarArr[0].value();
        String zzd = zzcxp.zzd(dpVarArr[1]);
        double d = Double.POSITIVE_INFINITY;
        if (dpVarArr.length == 3 && !Double.isNaN(zzcxp.zzb(dpVarArr[2]))) {
            d = zzcxp.zzc(dpVarArr[2]);
        }
        return new dt(Double.valueOf((double) value.lastIndexOf(zzd, (int) Math.min(Math.max(d, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE), (double) value.length()))));
    }
}
