package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;

final class zzcck extends zzccm {
    private /* synthetic */ LocationListener zzbiE;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcck(zzccb zzccb, GoogleApiClient googleApiClient, LocationListener locationListener) {
        super(googleApiClient);
        this.zzbiE = locationListener;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzcdj) zzb).zza((zzbdy<LocationListener>) zzbea.zza(this.zzbiE, LocationListener.class.getSimpleName()), (zzccu) new zzccn(this));
    }
}
