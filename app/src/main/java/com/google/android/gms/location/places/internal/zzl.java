package com.google.android.gms.location.places.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.zzm;
import com.google.android.gms.maps.model.LatLngBounds;

final class zzl extends zzm.zza<zzm> {
    private /* synthetic */ String val$query;
    private /* synthetic */ LatLngBounds zzbkm;
    private /* synthetic */ AutocompleteFilter zzbkn;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzl(zzh zzh, Api api, GoogleApiClient googleApiClient, String str, LatLngBounds latLngBounds, AutocompleteFilter autocompleteFilter) {
        super(api, googleApiClient);
        this.val$query = str;
        this.zzbkm = latLngBounds;
        this.zzbkn = autocompleteFilter;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzm) zzb).zza(new zzm((zzm.zza) this), this.val$query, this.zzbkm, this.zzbkn);
    }
}
