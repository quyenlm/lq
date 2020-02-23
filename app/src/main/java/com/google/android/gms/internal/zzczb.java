package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;

public final class zzczb implements zzcxo {
    public final dp<?> zzb(zzcwa zzcwa, dp<?>... dpVarArr) {
        zzbo.zzaf(dpVarArr != null);
        zzbo.zzaf(dpVarArr.length == 3);
        dp<?> zza = zzcxp.zza(ed.zza(zzcwa, (dp) dpVarArr[0])) ? ed.zza(zzcwa, (dp) dpVarArr[1]) : ed.zza(zzcwa, (dp) dpVarArr[2]);
        if (!(zza instanceof dv) || zza == dv.zzbLu || zza == dv.zzbLt) {
            return zza;
        }
        throw new IllegalArgumentException("Illegal InternalType passed to Ternary.");
    }
}
