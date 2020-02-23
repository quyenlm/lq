package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzayy extends zzazb {
    private /* synthetic */ zzayw zzayZ;
    private /* synthetic */ String zztC;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzayy(zzayw zzayw, GoogleApiClient googleApiClient, String str) {
        super(zzayw, googleApiClient);
        this.zzayZ = zzayw;
        this.zztC = str;
    }

    public final void zza(zzazf zzazf) throws RemoteException {
        zzazf.zza(new zzazc(this, zzazf), this.zzayZ.zzayY, this.zztC);
    }
}
