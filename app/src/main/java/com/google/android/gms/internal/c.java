package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public final class c extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        boolean z = true;
        zzbo.zzaf(true);
        zzbo.zzaf(dpVarArr.length == 2);
        double zzb = zzcxp.zzb(dpVarArr[0]);
        double zzb2 = zzcxp.zzb(dpVarArr[1]);
        if (Double.isNaN(zzb) || Double.isNaN(zzb2)) {
            return new dt(Double.valueOf(Double.NaN));
        }
        if ((Double.isInfinite(zzb) && zzb2 == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) || (zzb == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE && Double.isInfinite(zzb2))) {
            return new dt(Double.valueOf(Double.NaN));
        }
        if (!Double.isInfinite(zzb) && !Double.isInfinite(zzb2)) {
            return new dt(Double.valueOf(zzb * zzb2));
        }
        boolean z2 = ((double) Double.compare(zzb, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE)) < FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE;
        if (((double) Double.compare(zzb2, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE)) >= FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            z = false;
        }
        return new dt(Double.valueOf(z2 ^ z ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY));
    }
}
