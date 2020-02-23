package com.google.android.gms.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzcsa;
import com.google.android.gms.safetynet.zzf;

final class zzcsl extends zzcru {
    private /* synthetic */ zzcsa.zze zzbCa;

    zzcsl(zzcsa.zze zze) {
        this.zzbCa = zze;
    }

    public final void zza(Status status, zzf zzf) {
        this.zzbCa.setResult(new zzcsa.zzh(status, zzf));
    }
}
