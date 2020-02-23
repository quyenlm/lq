package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.common.internal.zzbo;

public final class af implements zzcxo {
    private final zzcua zzbJY;

    public af(Context context) {
        this(zzcua.zzbu(context));
    }

    private af(zzcua zzcua) {
        this.zzbJY = zzcua;
    }

    public final dp<?> zzb(zzcwa zzcwa, dp<?>... dpVarArr) {
        boolean z = true;
        zzbo.zzaf(dpVarArr != null);
        zzbo.zzaf(dpVarArr.length == 0);
        if (this.zzbJY.isLimitAdTrackingEnabled()) {
            z = false;
        }
        return new ds(Boolean.valueOf(z));
    }
}
