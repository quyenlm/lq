package com.google.android.gms.internal;

import android.location.Location;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

final class zzccf extends zzccm {
    private /* synthetic */ Location zzbiH;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzccf(zzccb zzccb, GoogleApiClient googleApiClient, Location location) {
        super(googleApiClient);
        this.zzbiH = location;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzcdj) zzb).zzc(this.zzbiH);
        setResult(Status.zzaBm);
    }
}
