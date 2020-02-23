package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.query.Query;

final class zzblp extends zzbmc {
    private /* synthetic */ Query zzaNK;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzblp(zzblo zzblo, GoogleApiClient googleApiClient, Query query) {
        super(googleApiClient);
        this.zzaNK = query;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbom) ((zzbmh) zzb).zzrf()).zza(new zzbqi(this.zzaNK), (zzboo) new zzbmd(this));
    }
}
