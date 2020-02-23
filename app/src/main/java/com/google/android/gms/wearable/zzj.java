package com.google.android.gms.wearable;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.internal.zzfw;

final class zzj extends Api.zza<zzfw, Wearable.WearableOptions> {
    zzj() {
    }

    public final /* synthetic */ Api.zze zza(Context context, Looper looper, zzq zzq, Object obj, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        if (((Wearable.WearableOptions) obj) == null) {
            new Wearable.WearableOptions(new Wearable.WearableOptions.Builder(), (zzj) null);
        }
        return new zzfw(context, looper, connectionCallbacks, onConnectionFailedListener, zzq);
    }
}
