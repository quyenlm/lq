package com.google.android.gms.internal;

import android.app.PendingIntent;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzj;

final class zzbci extends zzbcy {
    private /* synthetic */ zzj zzaDu;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbci(zzbcg zzbcg, zzbcw zzbcw, zzj zzj) {
        super(zzbcw);
        this.zzaDu = zzj;
    }

    public final void zzpV() {
        this.zzaDu.zzf(new ConnectionResult(16, (PendingIntent) null));
    }
}
