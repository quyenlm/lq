package com.google.android.gms.internal;

import android.net.Uri;
import com.google.android.gms.common.internal.zzbo;
import java.util.Map;

public final class bx extends zzcxq {
    private final zzcux zzbKi;

    public bx(zzcux zzcux) {
        this.zzbKi = zzcux;
    }

    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        boolean z = false;
        zzbo.zzaf(true);
        zzbo.zzaf(dpVarArr.length > 0);
        dp<?> dpVar = dpVarArr[0];
        zzbo.zzaf(!(dpVar instanceof dv));
        dv dvVar = dpVarArr.length > 1 ? dpVarArr[1] : dv.zzbLu;
        zzbo.zzaf(dvVar == dv.zzbLu || (dvVar instanceof dw));
        dv dvVar2 = dpVarArr.length > 2 ? dpVarArr[2] : dv.zzbLu;
        if (dvVar2 == dv.zzbLu || !(dvVar2 instanceof dv)) {
            z = true;
        }
        zzbo.zzaf(z);
        Uri.Builder buildUpon = Uri.parse(zzcxp.zzd(dpVar)).buildUpon();
        if (dvVar != dv.zzbLu) {
            for (dp dpVar2 : ((dw) dvVar).zzDs()) {
                zzbo.zzaf(dpVar2 instanceof dz);
                for (Map.Entry entry : ((dz) dpVar2).zzDt().entrySet()) {
                    buildUpon.appendQueryParameter(((String) entry.getKey()).toString(), zzcxp.zzd(ed.zza(zzcwa, (dp) entry.getValue())));
                }
            }
        }
        String uri = buildUpon.build().toString();
        if (dvVar2 == dv.zzbLu) {
            this.zzbKi.zzfh(uri);
            String valueOf = String.valueOf(uri);
            zzcvk.v(valueOf.length() != 0 ? "SendPixel: url = ".concat(valueOf) : new String("SendPixel: url = "));
        } else {
            String zzd = zzcxp.zzd(dvVar2);
            this.zzbKi.zzW(uri, zzd);
            zzcvk.v(new StringBuilder(String.valueOf(uri).length() + 30 + String.valueOf(zzd).length()).append("SendPixel: url = ").append(uri).append(", uniqueId = ").append(zzd).toString());
        }
        return dv.zzbLu;
    }
}
