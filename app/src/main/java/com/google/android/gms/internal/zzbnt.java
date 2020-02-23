package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzbnt extends zzbmg {
    private /* synthetic */ zzbnn zzaOD;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbnt(zzbnn zzbnn, GoogleApiClient googleApiClient) {
        super(googleApiClient);
        this.zzaOD = zzbnn;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbom) ((zzbmh) zzb).zzrf()).zza(new zzbqt(this.zzaOD.zzaLV), (zzboo) new zzbqq(this));
    }
}
