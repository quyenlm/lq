package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;

public final class zzczo extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        dp<?> zzbG;
        boolean z = true;
        zzbo.zzaf(true);
        zzbo.zzaf(dpVarArr.length == 2);
        dw dwVar = dpVarArr[0];
        dp<?> dpVar = dpVarArr[1];
        zzbo.zzaf((dwVar instanceof eb) || !ed.zzl(dwVar));
        zzbo.zzaf(!ed.zzm(dwVar));
        if (ed.zzm(dpVar)) {
            z = false;
        }
        zzbo.zzaf(z);
        String zzd = zzcxp.zzd(dpVar);
        if (dwVar instanceof dw) {
            dw dwVar2 = dwVar;
            if ("length".equals(zzd)) {
                return new dt(Double.valueOf((double) dwVar2.zzDs().size()));
            }
            double zzb = zzcxp.zzb(dpVar);
            if (zzb == Math.floor(zzb) && zzd.equals(Integer.toString((int) zzb)) && (zzbG = dwVar2.zzbG((int) zzb)) != dv.zzbLu) {
                return zzbG;
            }
        } else if (dwVar instanceof eb) {
            eb ebVar = (eb) dwVar;
            if ("length".equals(zzd)) {
                return new dt(Double.valueOf((double) ebVar.value().length()));
            }
            double zzb2 = zzcxp.zzb(dpVar);
            return (zzb2 != Math.floor(zzb2) || !zzd.equals(Integer.toString((int) zzb2))) ? dv.zzbLu : ebVar.zzbI((int) zzb2);
        }
        return dwVar.zzfZ(zzd);
    }
}
