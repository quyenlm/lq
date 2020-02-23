package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import java.util.ArrayList;

public final class zzczi extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        zzbo.zzaf(true);
        ArrayList arrayList = new ArrayList();
        int length = dpVarArr.length;
        for (int i = 0; i < length; i++) {
            dv dvVar = dpVarArr[i];
            zzbo.zzaf(!(dvVar instanceof dv) || dvVar == dv.zzbLu || dvVar == dv.zzbLt);
            arrayList.add(dvVar);
        }
        return new dw(arrayList);
    }
}
