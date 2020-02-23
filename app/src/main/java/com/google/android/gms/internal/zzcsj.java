package com.google.android.gms.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzcsa;

final class zzcsj extends zzcru {
    private /* synthetic */ zzcsa.zzc zzbBY;

    zzcsj(zzcsa.zzc zzc) {
        this.zzbBY = zzc;
    }

    public final void zza(Status status, boolean z) {
        this.zzbBY.setResult(new zzcsa.zzj(status, z));
    }
}
