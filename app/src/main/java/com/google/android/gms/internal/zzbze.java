package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.request.zzaw;

final class zzbze extends zzbvr {
    private /* synthetic */ PendingIntent zzaVX;
    private /* synthetic */ int zzaWg = 0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbze(zzbyz zzbyz, GoogleApiClient googleApiClient, PendingIntent pendingIntent, int i) {
        super(googleApiClient);
        this.zzaVX = pendingIntent;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbwv) ((zzbvo) zzb).zzrf()).zza(new zzaw(this.zzaVX, new zzbzi(this), this.zzaWg));
    }
}
