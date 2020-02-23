package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.GeofencingRequest;

final class zzccr extends zzcct {
    private /* synthetic */ PendingIntent zzaVL;
    private /* synthetic */ GeofencingRequest zzbiK;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzccr(zzccq zzccq, GoogleApiClient googleApiClient, GeofencingRequest geofencingRequest, PendingIntent pendingIntent) {
        super(googleApiClient);
        this.zzbiK = geofencingRequest;
        this.zzaVL = pendingIntent;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzcdj) zzb).zza(this.zzbiK, this.zzaVL, (zzbaz<Status>) this);
    }
}
