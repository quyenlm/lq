package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

final class zzccc extends zzccm {
    private /* synthetic */ LocationRequest zzbiD;
    private /* synthetic */ LocationListener zzbiE;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzccc(zzccb zzccb, GoogleApiClient googleApiClient, LocationRequest locationRequest, LocationListener locationListener) {
        super(googleApiClient);
        this.zzbiD = locationRequest;
        this.zzbiE = locationListener;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzcdj) zzb).zza(this.zzbiD, (zzbdw<LocationListener>) zzbea.zzb(this.zzbiE, zzceb.zzwd(), LocationListener.class.getSimpleName()), (zzccu) new zzccn(this));
    }
}
