package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public final class a extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        zzbo.zzaf(true);
        zzbo.zzaf(dpVarArr.length == 2);
        double zzb = zzcxp.zzb(dpVarArr[0]);
        double zzb2 = zzcxp.zzb(dpVarArr[1]);
        if (Double.isNaN(zzb) || Double.isNaN(zzb2)) {
            return new dt(Double.valueOf(Double.NaN));
        }
        if (Double.isInfinite(zzb) && Double.isInfinite(zzb2)) {
            return new dt(Double.valueOf(Double.NaN));
        }
        boolean z = (((double) Double.compare(zzb, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE)) < FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) ^ (((double) Double.compare(zzb2, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE)) < FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE);
        if (Double.isInfinite(zzb) && !Double.isInfinite(zzb2)) {
            return new dt(Double.valueOf(z ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY));
        } else if (!Double.isInfinite(zzb) && Double.isInfinite(zzb2)) {
            return new dt(Double.valueOf(z ? -0.0d : FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE));
        } else if (zzb == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            if (zzb2 == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
                return new dt(Double.valueOf(Double.NaN));
            }
            return new dt(Double.valueOf(z ? -0.0d : FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE));
        } else if (Double.isInfinite(zzb) || zzb == FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE || zzb2 != FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) {
            return new dt(Double.valueOf(zzb / zzb2));
        } else {
            return new dt(Double.valueOf(z ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY));
        }
    }
}
