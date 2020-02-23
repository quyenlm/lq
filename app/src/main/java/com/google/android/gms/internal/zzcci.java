package com.google.android.gms.internal;

import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;

final class zzcci extends zzccm {
    private /* synthetic */ LocationRequest zzbiD;
    private /* synthetic */ LocationCallback zzbiF;
    private /* synthetic */ Looper zzbiI;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcci(zzccb zzccb, GoogleApiClient googleApiClient, LocationRequest locationRequest, LocationCallback locationCallback, Looper looper) {
        super(googleApiClient);
        this.zzbiD = locationRequest;
        this.zzbiF = locationCallback;
        this.zzbiI = looper;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzcdj) zzb).zza(zzcdn.zza(this.zzbiD), (zzbdw<LocationCallback>) zzbea.zzb(this.zzbiF, zzceb.zzb(this.zzbiI), LocationCallback.class.getSimpleName()), (zzccu) new zzccn(this));
    }
}
