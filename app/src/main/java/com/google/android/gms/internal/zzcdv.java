package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;

final class zzcdv extends LocationServices.zza<LocationSettingsResult> {
    private /* synthetic */ LocationSettingsRequest zzbjg;
    private /* synthetic */ String zzbjh = null;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcdv(zzcdu zzcdu, GoogleApiClient googleApiClient, LocationSettingsRequest locationSettingsRequest, String str) {
        super(googleApiClient);
        this.zzbjg = locationSettingsRequest;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzcdj) zzb).zza(this.zzbjg, (zzbaz<LocationSettingsResult>) this, this.zzbjh);
    }

    public final /* synthetic */ Result zzb(Status status) {
        return new LocationSettingsResult(status);
    }
}
