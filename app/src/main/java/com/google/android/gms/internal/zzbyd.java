package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.request.DataUpdateRequest;

final class zzbyd extends zzbvd {
    private /* synthetic */ DataUpdateRequest zzaVJ;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbyd(zzbya zzbya, GoogleApiClient googleApiClient, DataUpdateRequest dataUpdateRequest) {
        super(googleApiClient);
        this.zzaVJ = dataUpdateRequest;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbwn) ((zzbva) zzb).zzrf()).zza(new DataUpdateRequest(this.zzaVJ, (IBinder) new zzbzi(this)));
    }
}
