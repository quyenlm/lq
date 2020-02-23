package com.google.android.gms.internal;

import java.util.Map;

final class ut implements uy<Map<vq, un>, Void> {
    private /* synthetic */ uo zzcgr;

    ut(uo uoVar) {
        this.zzcgr = uoVar;
    }

    public final /* synthetic */ Object zza(qr qrVar, Object obj, Object obj2) {
        for (Map.Entry value : ((Map) obj).entrySet()) {
            un unVar = (un) value.getValue();
            if (!unVar.complete) {
                this.zzcgr.zza(unVar.zzHO());
            }
        }
        return null;
    }
}
