package com.google.android.gms.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzcsa;
import com.google.android.gms.safetynet.zza;

final class zzcsi extends zzcru {
    private /* synthetic */ zzcsa.zzb zzbBX;

    zzcsi(zzcsa.zzb zzb) {
        this.zzbBX = zzb;
    }

    public final void zza(Status status, zza zza) {
        this.zzbBX.setResult(new zzcsa.zza(status, zza));
    }
}
