package com.google.android.gms.location.places.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.zzm;

final class zzy extends zzm.zzd<zzaa> {
    private /* synthetic */ PlaceFilter zzbkp;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzy(zzx zzx, Api api, GoogleApiClient googleApiClient, PlaceFilter placeFilter) {
        super(api, googleApiClient);
        this.zzbkp = placeFilter;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzaa) zzb).zza(new zzm((zzm.zzd) this), this.zzbkp);
    }
}
