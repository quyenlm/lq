package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

final class zzccj extends zzccm {
    private /* synthetic */ LocationRequest zzbiD;
    private /* synthetic */ PendingIntent zzbiz;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzccj(zzccb zzccb, GoogleApiClient googleApiClient, LocationRequest locationRequest, PendingIntent pendingIntent) {
        super(googleApiClient);
        this.zzbiD = locationRequest;
        this.zzbiz = pendingIntent;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzcdj) zzb).zza(this.zzbiD, this.zzbiz, (zzccu) new zzccn(this));
    }
}
