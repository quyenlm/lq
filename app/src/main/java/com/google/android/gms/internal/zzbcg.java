package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.internal.zzj;
import java.util.Iterator;
import java.util.Map;

final class zzbcg extends zzbcn {
    final /* synthetic */ zzbcd zzaDp;
    private final Map<Api.zze, zzbcf> zzaDr;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public zzbcg(zzbcd zzbcd, Map<Api.zze, zzbcf> map) {
        super(zzbcd, (zzbce) null);
        this.zzaDp = zzbcd;
        this.zzaDr = map;
    }

    @WorkerThread
    public final void zzpV() {
        boolean z;
        boolean z2;
        int i = 0;
        Iterator<Api.zze> it = this.zzaDr.keySet().iterator();
        boolean z3 = true;
        boolean z4 = false;
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            Api.zze next = it.next();
            if (!next.zzpe()) {
                z2 = false;
            } else if (!this.zzaDr.get(next).zzaCj) {
                z4 = true;
                z = true;
                break;
            } else {
                z2 = z3;
                z4 = true;
            }
            z3 = z2;
        }
        if (z4) {
            i = this.zzaDp.zzaCF.isGooglePlayServicesAvailable(this.zzaDp.mContext);
        }
        if (i == 0 || (!z && !z3)) {
            if (this.zzaDp.zzaDj) {
                this.zzaDp.zzaDh.connect();
            }
            for (Api.zze next2 : this.zzaDr.keySet()) {
                zzj zzj = this.zzaDr.get(next2);
                if (!next2.zzpe() || i == 0) {
                    next2.zza(zzj);
                } else {
                    this.zzaDp.zzaCZ.zza((zzbcy) new zzbci(this, this.zzaDp, zzj));
                }
            }
            return;
        }
        this.zzaDp.zzaCZ.zza((zzbcy) new zzbch(this, this.zzaDp, new ConnectionResult(i, (PendingIntent) null)));
    }
}
