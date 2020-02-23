package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.List;

public final class vo {
    /* access modifiers changed from: private */
    public final wl zzbZE;
    private final qk zzccO;

    public vo(qd qdVar) {
        this.zzccO = qdVar.zzGT();
        this.zzbZE = qdVar.zzgP("EventRaiser");
    }

    public final void zzV(List<? extends vk> list) {
        if (this.zzbZE.zzIH()) {
            this.zzbZE.zzb(new StringBuilder(28).append("Raising ").append(list.size()).append(" event(s)").toString(), (Throwable) null, new Object[0]);
        }
        this.zzccO.zzo(new vp(this, new ArrayList(list)));
    }
}
