package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzbnj extends zzbmg {
    private /* synthetic */ zzbog zzaOA;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbnj(zzbnh zzbnh, GoogleApiClient googleApiClient, zzbog zzbog) {
        super(googleApiClient);
        this.zzaOA = zzbog;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbom) ((zzbmh) zzb).zzrf()).zza(new zzbqm(this.zzaOA), (zzboo) new zzbqq(this));
    }
}
