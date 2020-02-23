package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.request.DataUpdateListenerRegistrationRequest;

final class zzbye extends zzbvd {
    private /* synthetic */ DataUpdateListenerRegistrationRequest zzaVK;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbye(zzbya zzbya, GoogleApiClient googleApiClient, DataUpdateListenerRegistrationRequest dataUpdateListenerRegistrationRequest) {
        super(googleApiClient);
        this.zzaVK = dataUpdateListenerRegistrationRequest;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbwn) ((zzbva) zzb).zzrf()).zza(new DataUpdateListenerRegistrationRequest(this.zzaVK, (IBinder) new zzbzi(this)));
    }
}
