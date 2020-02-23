package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;

public final class zzczz extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        zzbo.zzaf(true);
        zzbo.zzaf(dpVarArr.length == 2);
        dv dvVar = dpVarArr[0];
        dv dvVar2 = dpVarArr[1];
        if ((!(dvVar instanceof dv) || dvVar == dv.zzbLu || dvVar == dv.zzbLt) && (!(dvVar2 instanceof dv) || dvVar2 == dv.zzbLu || dvVar2 == dv.zzbLt)) {
            if ((dvVar instanceof dz) || (dvVar instanceof dw) || (dvVar instanceof du)) {
                dvVar = new eb(zzcxp.zzd(dvVar));
            }
            eb ebVar = ((dvVar2 instanceof dz) || (dvVar2 instanceof dw) || (dvVar2 instanceof du)) ? new eb(zzcxp.zzd(dvVar2)) : dvVar2;
            if (!(dvVar instanceof eb) && !(ebVar instanceof eb)) {
                return new dt(Double.valueOf(zzcxp.zza(dvVar, ebVar)));
            }
            String valueOf = String.valueOf(zzcxp.zzd(dvVar));
            String valueOf2 = String.valueOf(zzcxp.zzd(ebVar));
            return new eb(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        }
        throw new IllegalArgumentException("Illegal InternalType passed to Add.");
    }
}
