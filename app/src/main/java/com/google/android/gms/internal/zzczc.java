package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbo;

public final class zzczc extends zzcxq {
    /* access modifiers changed from: protected */
    public final dp<?> zza(zzcwa zzcwa, dp<?>... dpVarArr) {
        boolean z = true;
        zzbo.zzaf(true);
        zzbo.zzaf(dpVarArr.length == 1);
        zzbo.zzaf(!(dpVarArr[0] instanceof ea));
        if (ed.zzm(dpVarArr[0])) {
            z = false;
        }
        zzbo.zzaf(z);
        dv dvVar = dpVarArr[0];
        String str = "object";
        if (dvVar == dv.zzbLu) {
            str = "undefined";
        } else if (dvVar instanceof ds) {
            str = "boolean";
        } else if (dvVar instanceof dt) {
            str = "number";
        } else if (dvVar instanceof eb) {
            str = "string";
        } else if (dvVar instanceof du) {
            str = "function";
        }
        return new eb(str);
    }
}
