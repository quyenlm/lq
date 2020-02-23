package com.google.android.gms.internal;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.awareness.AwarenessOptions;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzq;

final class zzbim extends Api.zza<zzbka, AwarenessOptions> {
    zzbim() {
    }

    public final /* synthetic */ Api.zze zza(Context context, Looper looper, zzq zzq, Object obj, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return new zzbka(context, looper, zzq, (AwarenessOptions) obj, connectionCallbacks, onConnectionFailedListener);
    }
}
