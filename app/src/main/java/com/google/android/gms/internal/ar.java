package com.google.android.gms.internal;

import android.os.Build;
import com.google.android.gms.common.internal.zzbo;

public final class ar implements zzcxo {
    private final String zzaUg = Build.MODEL;

    public final dp<?> zzb(zzcwa zzcwa, dp<?>... dpVarArr) {
        boolean z = true;
        zzbo.zzaf(dpVarArr != null);
        if (dpVarArr.length != 0) {
            z = false;
        }
        zzbo.zzaf(z);
        return new eb(this.zzaUg);
    }
}
