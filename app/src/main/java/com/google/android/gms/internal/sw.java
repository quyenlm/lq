package com.google.android.gms.internal;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

final class sw implements Callable<List<? extends vk>> {
    private /* synthetic */ long zzcdX;
    private /* synthetic */ boolean zzceN;
    private /* synthetic */ so zzceR;
    private /* synthetic */ boolean zzcfb;
    private /* synthetic */ ys zzcfc;

    sw(so soVar, boolean z, long j, boolean z2, ys ysVar) {
        this.zzceR = soVar;
        this.zzceN = z;
        this.zzcdX = j;
        this.zzcfb = z2;
        this.zzcfc = ysVar;
    }

    public final /* synthetic */ Object call() throws Exception {
        uv uvVar;
        if (this.zzceN) {
            this.zzceR.zzceF.zzal(this.zzcdX);
        }
        tm zzav = this.zzceR.zzceH.zzav(this.zzcdX);
        boolean zzaw = this.zzceR.zzceH.zzaw(this.zzcdX);
        if (zzav.isVisible() && !this.zzcfb) {
            Map<String, Object> zza = se.zza(this.zzcfc);
            if (zzav.zzHw()) {
                this.zzceR.zzceF.zzk(zzav.zzFq(), se.zza(zzav.zzHu(), zza));
            } else {
                this.zzceR.zzceF.zzc(zzav.zzFq(), se.zza(zzav.zzHv(), zza));
            }
        }
        if (!zzaw) {
            return Collections.emptyList();
        }
        uv zzHR = uv.zzHR();
        if (!zzav.zzHw()) {
            Iterator<Map.Entry<qr, xm>> it = zzav.zzHv().iterator();
            while (true) {
                uvVar = zzHR;
                if (!it.hasNext()) {
                    break;
                }
                zzHR = uvVar.zzb((qr) it.next().getKey(), true);
            }
        } else {
            uvVar = zzHR.zzb(qr.zzGZ(), true);
        }
        return this.zzceR.zza((tx) new tu(zzav.zzFq(), uvVar, this.zzcfb));
    }
}
