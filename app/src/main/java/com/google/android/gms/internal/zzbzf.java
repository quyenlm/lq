package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.request.zzbc;

final class zzbzf extends zzbvr {
    private /* synthetic */ PendingIntent zzaVX;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbzf(zzbyz zzbyz, GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        super(googleApiClient);
        this.zzaVX = pendingIntent;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbwv) ((zzbvo) zzb).zzrf()).zza(new zzbc(this.zzaVX, new zzbzi(this)));
    }
}
