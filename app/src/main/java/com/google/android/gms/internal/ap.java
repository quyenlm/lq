package com.google.android.gms.internal;

import android.os.Build;
import com.google.android.gms.common.internal.zzbo;

public final class ap implements zzcxo {
    private final String zzbKb = Build.BRAND;

    public final dp<?> zzb(zzcwa zzcwa, dp<?>... dpVarArr) {
        boolean z = true;
        zzbo.zzaf(dpVarArr != null);
        if (dpVarArr.length != 0) {
            z = false;
        }
        zzbo.zzaf(z);
        return new eb(this.zzbKb);
    }
}
