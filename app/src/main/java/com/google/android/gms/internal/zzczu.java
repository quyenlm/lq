package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public final class zzczu extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        boolean z = true;
        zzbo.zzaf(true);
        zzbo.zzaf(dpVarArr.length == 3);
        dz dzVar = dpVarArr[0];
        dp<?> dpVar = dpVarArr[1];
        dp<?> dpVar2 = dpVarArr[2];
        zzbo.zzaf(dzVar != dv.zzbLt);
        zzbo.zzaf(dzVar != dv.zzbLu);
        zzbo.zzaf(!ed.zzm(dzVar));
        zzbo.zzaf(!ed.zzm(dpVar));
        zzbo.zzaf(!ed.zzm(dpVar2));
        if (ed.zzl(dzVar)) {
            return dpVar2;
        }
        String zzd = zzcxp.zzd(dpVar);
        if (dzVar instanceof dz) {
            dz dzVar2 = dzVar;
            if (!dzVar2.isImmutable()) {
                dzVar2.zzc(zzd, dpVar2);
            }
            return dpVar2;
        }
        if (dzVar instanceof dw) {
            dw dwVar = (dw) dzVar;
            if ("length".equals(zzd)) {
                double zzb = zzcxp.zzb(dpVar2);
                if (Double.isInfinite(zzb) || zzb != Math.floor(zzb)) {
                    z = false;
                }
                zzbo.zzaf(z);
                dwVar.setSize((int) zzb);
                return dpVar2;
            }
            double zzb2 = zzcxp.zzb(dpVar);
            if (!Double.isInfinite(zzb2) && zzb2 >= FirebaseRemoteConfig.DEFAULT_VALUE_FOR_DOUBLE && zzd.equals(Integer.toString((int) zzb2))) {
                dwVar.zza((int) zzb2, dpVar2);
                return dpVar2;
            }
        }
        dzVar.zzc(zzd, dpVar2);
        return dpVar2;
    }
}
