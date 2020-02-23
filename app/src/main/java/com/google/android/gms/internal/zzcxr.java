package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;
import java.util.ArrayList;

public final class zzcxr extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        zzbo.zzu(dpVarArr);
        zzbo.zzaf(dpVarArr.length > 0);
        zzbo.zzaf(dpVarArr[0] instanceof dw);
        ArrayList arrayList = new ArrayList();
        for (dp add : dpVarArr[0].zzDs()) {
            arrayList.add(add);
        }
        for (int i = 1; i < dpVarArr.length; i++) {
            if (dpVarArr[i] instanceof dw) {
                for (dp add2 : dpVarArr[i].zzDs()) {
                    arrayList.add(add2);
                }
            } else {
                arrayList.add(dpVarArr[i]);
            }
        }
        return new dw(arrayList);
    }
}
