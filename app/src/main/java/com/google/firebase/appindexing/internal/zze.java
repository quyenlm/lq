package com.google.firebase.appindexing.internal;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzq;

final class zze extends Api.zza<zzd, Api.ApiOptions.NoOptions> {
    zze() {
    }

    public final /* synthetic */ Api.zze zza(Context context, Looper looper, zzq zzq, Object obj, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return new zzd(context, looper, zzq, connectionCallbacks, onConnectionFailedListener);
    }
}
