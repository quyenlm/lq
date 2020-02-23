package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationCallback;

final class zzccd extends zzccm {
    private /* synthetic */ LocationCallback zzbiF;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzccd(zzccb zzccb, GoogleApiClient googleApiClient, LocationCallback locationCallback) {
        super(googleApiClient);
        this.zzbiF = locationCallback;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzcdj) zzb).zzb(zzbea.zza(this.zzbiF, LocationCallback.class.getSimpleName()), new zzccn(this));
    }
}
