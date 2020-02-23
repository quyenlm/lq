package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzq;

public final class zzbva extends zzbun<zzbwn> {
    public static final Api<Api.ApiOptions.NoOptions> API = new Api<>("Fitness.API", new zzbvc(), zzajR);
    private static Api.zzf<zzbva> zzajR = new Api.zzf<>();

    public zzbva(Context context, Looper looper, zzq zzq, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 57, connectionCallbacks, onConnectionFailedListener, zzq);
    }

    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IGoogleFitHistoryApi");
        return queryLocalInterface instanceof zzbwn ? (zzbwn) queryLocalInterface : new zzbwo(iBinder);
    }

    public final String zzdb() {
        return "com.google.android.gms.fitness.HistoryApi";
    }

    public final String zzdc() {
        return "com.google.android.gms.fitness.internal.IGoogleFitHistoryApi";
    }
}
