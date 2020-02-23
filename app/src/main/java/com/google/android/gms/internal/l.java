package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public final class l extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        zzbo.zzaf(true);
        zzbo.zzaf(dpVarArr.length > 0 && dpVarArr.length <= 3);
        zzbo.zzaf(dpVarArr[0] instanceof eb);
        String value = dpVarArr[0].value();
        double zzc = dpVarArr.length < 2 ? 0.0d : zzcxp.zzc(dpVarArr[1]);
        double length = (double) value.length();
        if (dpVarArr.length == 3 && dpVarArr[2] != dv.zzbLu) {
            length = zzcxp.zzc(dpVarArr[2]);
        }
        int max = zzc < FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? (int) Math.max(zzc + ((double) value.length()), FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) : (int) Math.min(zzc, (double) value.length());
        return new eb(value.substring(max, Math.max(0, (length < FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE ? (int) Math.max(((double) value.length()) + length, FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE) : (int) Math.min(length, (double) value.length())) - max) + max));
    }
}
