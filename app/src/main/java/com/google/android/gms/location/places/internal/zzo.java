package com.google.android.gms.location.places.internal;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.location.places.PlacesOptions;

public final class zzo extends Api.zza<zzm, PlacesOptions> {
    public final /* synthetic */ Api.zze zza(Context context, Looper looper, zzq zzq, Object obj, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        PlacesOptions placesOptions = (PlacesOptions) obj;
        return new zzm(context, looper, zzq, connectionCallbacks, onConnectionFailedListener, context.getPackageName(), placesOptions == null ? new PlacesOptions.Builder().build() : placesOptions);
    }
}
