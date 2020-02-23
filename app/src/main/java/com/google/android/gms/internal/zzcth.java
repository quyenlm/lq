package com.google.android.gms.internal;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzq;

final class zzcth extends Api.zza<zzctu, zzctl> {
    zzcth() {
    }

    public final /* synthetic */ Api.zze zza(Context context, Looper looper, zzq zzq, Object obj, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        zzctl zzctl = (zzctl) obj;
        return new zzctu(context, looper, true, zzq, zzctl == null ? zzctl.zzbCM : zzctl, connectionCallbacks, onConnectionFailedListener);
    }
}
