package com.google.android.gms.instantapps;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.internal.zzcbo;

final class zza extends Api.zza<zzcbo, Api.ApiOptions.NoOptions> {
    zza() {
    }

    public final /* synthetic */ Api.zze zza(Context context, Looper looper, zzq zzq, Object obj, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return new zzcbo(context, looper, connectionCallbacks, onConnectionFailedListener);
    }
}
