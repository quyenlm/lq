package com.google.android.gms.internal;

import com.google.android.gms.common.ConnectionResult;

final class zzbch extends zzbcy {
    private /* synthetic */ ConnectionResult zzaDs;
    private /* synthetic */ zzbcg zzaDt;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbch(zzbcg zzbcg, zzbcw zzbcw, ConnectionResult connectionResult) {
        super(zzbcw);
        this.zzaDt = zzbcg;
        this.zzaDs = connectionResult;
    }

    public final void zzpV() {
        this.zzaDt.zzaDp.zze(this.zzaDs);
    }
}
