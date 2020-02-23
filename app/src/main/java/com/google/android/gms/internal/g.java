package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public final class g extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        zzbo.zzaf(true);
        zzbo.zzaf(dpVarArr.length == 2 || dpVarArr.length == 3);
        zzbo.zzaf(dpVarArr[0] instanceof eb);
        String value = dpVarArr[0].value();
        return new dt(Double.valueOf((double) value.indexOf(zzcxp.zzd(dpVarArr[1]), (int) Math.min(Math.max(dpVarArr.length < 3 ? 0.0d : zzcxp.zzc(dpVarArr[2]), FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE), (double) value.length()))));
    }
}
