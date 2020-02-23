package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzq;

public final class zzbvo extends zzbun<zzbwv> {
    public static final Api<Api.ApiOptions.NoOptions> API = new Api<>("Fitness.SESSIONS_API", new zzbvq(), zzajR);
    private static Api.zzf<zzbvo> zzajR = new Api.zzf<>();

    public zzbvo(Context context, Looper looper, zzq zzq, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 58, connectionCallbacks, onConnectionFailedListener, zzq);
    }

    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IGoogleFitSessionsApi");
        return queryLocalInterface instanceof zzbwv ? (zzbwv) queryLocalInterface : new zzbww(iBinder);
    }

    public final String zzdb() {
        return "com.google.android.gms.fitness.SessionsApi";
    }

    public final String zzdc() {
        return "com.google.android.gms.fitness.internal.IGoogleFitSessionsApi";
    }
}
