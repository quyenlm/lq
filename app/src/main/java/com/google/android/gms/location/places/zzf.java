package com.google.android.gms.location.places;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.location.places.zzm;

public abstract class zzf<A extends Api.zze> extends zzm.zzb<PlacePhotoMetadataResult, A> {
    public zzf(Api api, GoogleApiClient googleApiClient) {
        super(api, googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return new PlacePhotoMetadataResult(status, (DataHolder) null);
    }
}
