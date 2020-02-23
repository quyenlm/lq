package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;

public final class j extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        zzbo.zzaf(true);
        zzbo.zzaf(dpVarArr.length > 0 && dpVarArr.length <= 3);
        zzbo.zzaf(dpVarArr[0] instanceof eb);
        String value = dpVarArr[0].value();
        if (dpVarArr.length == 1) {
            return new eb(value);
        }
        String zzd = zzcxp.zzd(dpVarArr[1]);
        dp<?> dpVar = dpVarArr.length < 3 ? dv.zzbLu : dpVarArr[2];
        int indexOf = value.indexOf(zzd);
        if (indexOf == -1) {
            return new eb(value);
        }
        if (dpVar instanceof du) {
            dpVar = ((du) dpVar).zzDp().zzb(zzcwa, new eb(zzd), new dt(Double.valueOf((double) indexOf)), new eb(value));
        }
        String zzd2 = zzcxp.zzd(dpVar);
        String valueOf = String.valueOf(value.substring(0, indexOf));
        String valueOf2 = String.valueOf(value.substring(zzd.length() + indexOf));
        return new eb(new StringBuilder(String.valueOf(valueOf).length() + String.valueOf(zzd2).length() + String.valueOf(valueOf2).length()).append(valueOf).append(zzd2).append(valueOf2).toString());
    }
}
