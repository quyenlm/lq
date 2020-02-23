package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;

public final class zzcyu extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        boolean z = false;
        zzbo.zzaf(true);
        zzbo.zzaf(dpVarArr.length == 2);
        eb ebVar = dpVarArr[0];
        eb ebVar2 = dpVarArr[1];
        if ((ebVar instanceof dz) || (ebVar instanceof dw) || (ebVar instanceof du)) {
            ebVar = new eb(zzcxp.zzd(ebVar));
        }
        eb ebVar3 = ((ebVar2 instanceof dz) || (ebVar2 instanceof dw) || (ebVar2 instanceof du)) ? new eb(zzcxp.zzd(ebVar2)) : ebVar2;
        if ((((ebVar instanceof eb) && (ebVar3 instanceof eb)) || (!Double.isNaN(zzcxp.zzb(ebVar)) && !Double.isNaN(zzcxp.zzb(ebVar3)))) && !zzcxp.zzb(ebVar3, ebVar)) {
            z = true;
        }
        return new ds(Boolean.valueOf(z));
    }
}
