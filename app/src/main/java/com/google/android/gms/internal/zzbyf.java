package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.request.zzv;

final class zzbyf extends zzbvd {
    private /* synthetic */ PendingIntent zzaVL;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbyf(zzbya zzbya, GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        super(googleApiClient);
        this.zzaVL = pendingIntent;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbwn) ((zzbva) zzb).zzrf()).zza(new zzv(this.zzaVL, new zzbzi(this)));
    }
}
