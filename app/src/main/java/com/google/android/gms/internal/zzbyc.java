package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.request.DataDeleteRequest;

final class zzbyc extends zzbvd {
    private /* synthetic */ DataDeleteRequest zzaVI;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbyc(zzbya zzbya, GoogleApiClient googleApiClient, DataDeleteRequest dataDeleteRequest) {
        super(googleApiClient);
        this.zzaVI = dataDeleteRequest;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbwn) ((zzbva) zzb).zzrf()).zza(new DataDeleteRequest(this.zzaVI, (zzbxg) new zzbzi(this)));
    }
}
