package com.google.android.gms.cast;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.internal.zzaxx;

final class zze extends Api.zza<zzaxx, Cast.CastOptions> {
    zze() {
    }

    public final /* synthetic */ Api.zze zza(Context context, Looper looper, zzq zzq, Object obj, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        Cast.CastOptions castOptions = (Cast.CastOptions) obj;
        zzbo.zzb(castOptions, (Object) "Setting the API options is required.");
        return new zzaxx(context, looper, zzq, castOptions.zzaoU, (long) castOptions.zzaoW, castOptions.zzaoV, castOptions.extras, connectionCallbacks, onConnectionFailedListener);
    }
}
