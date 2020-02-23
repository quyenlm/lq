package com.google.android.gms.internal;

import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbo;
import java.util.ArrayList;
import java.util.List;

public final class zzcxw extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        zzbo.zzu(dpVarArr);
        zzbo.zzaf(dpVarArr.length == 1 || dpVarArr.length == 2);
        zzbo.zzaf(dpVarArr[0] instanceof dw);
        List<dp> zzDs = dpVarArr[0].zzDs();
        dv dvVar = dpVarArr.length < 2 ? dv.zzbLu : dpVarArr[1];
        String zzd = dvVar == dv.zzbLu ? "," : zzcxp.zzd(dvVar);
        ArrayList arrayList = new ArrayList();
        for (dp dpVar : zzDs) {
            if (dpVar == dv.zzbLt || dpVar == dv.zzbLu) {
                arrayList.add("");
            } else {
                arrayList.add(zzcxp.zzd(dpVar));
            }
        }
        return new eb(TextUtils.join(zzd, arrayList));
    }
}
