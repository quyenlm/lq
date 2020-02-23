package com.google.android.gms.location.places.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.PlaceReport;
import com.google.android.gms.location.places.zzm;

final class zzz extends zzm.zzf<zzaa> {
    private /* synthetic */ PlaceReport zzbkq;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzz(zzx zzx, Api api, GoogleApiClient googleApiClient, PlaceReport placeReport) {
        super(api, googleApiClient);
        this.zzbkq = placeReport;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzaa) zzb).zza(new zzm((zzm.zzf) this), this.zzbkq);
    }
}
