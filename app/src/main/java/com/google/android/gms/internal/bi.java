package com.google.android.gms.internal;

import android.os.Build;
import com.google.android.gms.common.internal.zzbo;

public final class bi implements zzcxo {
    public final dp<?> zzb(zzcwa zzcwa, dp<?>... dpVarArr) {
        boolean z = true;
        zzbo.zzaf(dpVarArr != null);
        if (dpVarArr.length != 0) {
            z = false;
        }
        zzbo.zzaf(z);
        String valueOf = String.valueOf("5.05-");
        return new eb(new StringBuilder(String.valueOf(valueOf).length() + 11).append(valueOf).append(Build.VERSION.SDK_INT).toString());
    }
}
