package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzbno extends zzbny {
    private /* synthetic */ boolean zzaOC = false;
    private /* synthetic */ zzbnn zzaOD;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbno(zzbnn zzbnn, GoogleApiClient googleApiClient, boolean z) {
        super(zzbnn, googleApiClient, (zzbno) null);
        this.zzaOD = zzbnn;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbom) ((zzbmh) zzb).zzrf()).zza(new zzboi(this.zzaOD.zzaLV, this.zzaOC), (zzboo) new zzbnw(this));
    }
}
