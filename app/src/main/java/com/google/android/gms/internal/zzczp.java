package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;

public final class zzczp extends zzcxq {
    public static final zzczp zzbJW = new zzczp();

    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        int i;
        int i2;
        zzbo.zzaf(true);
        zzbo.zzaf(dpVarArr.length == 2);
        dw dwVar = dpVarArr[0];
        dp<?> dpVar = dpVarArr[1];
        zzbo.zzaf(!ed.zzm(dwVar));
        zzbo.zzaf(!ed.zzm(dpVar));
        String zzd = zzcxp.zzd(dpVar);
        if (dwVar instanceof dw) {
            if ("length".equals(zzd)) {
                return new ds(true);
            }
            double zzb = zzcxp.zzb(dpVar);
            if (zzb == Math.floor(zzb) && zzd.equals(Integer.toString((int) zzb)) && (i2 = (int) zzb) >= 0 && i2 < dwVar.zzDs().size()) {
                return new ds(true);
            }
        } else if (dwVar instanceof eb) {
            if ("length".equals(zzd)) {
                return new ds(true);
            }
            double zzb2 = zzcxp.zzb(dpVar);
            return (zzb2 != Math.floor(zzb2) || !zzd.equals(Integer.toString((int) zzb2)) || (i = (int) zzb2) < 0 || i >= ((eb) dwVar).value().length()) ? new ds(false) : new ds(true);
        }
        return new ds(Boolean.valueOf(dwVar.zzfY(zzd)));
    }
}
