package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzq;

public final class zzbvg extends zzbun<zzbwr> {
    public static final Api<Api.ApiOptions.NoOptions> API = new Api<>("Fitness.RECORDING_API", new zzbvi(), zzajR);
    private static Api.zzf<zzbvg> zzajR = new Api.zzf<>();

    public zzbvg(Context context, Looper looper, zzq zzq, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 56, connectionCallbacks, onConnectionFailedListener, zzq);
    }

    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IGoogleFitRecordingApi");
        return queryLocalInterface instanceof zzbwr ? (zzbwr) queryLocalInterface : new zzbws(iBinder);
    }

    public final String zzdb() {
        return "com.google.android.gms.fitness.RecordingApi";
    }

    public final String zzdc() {
        return "com.google.android.gms.fitness.internal.IGoogleFitRecordingApi";
    }
}
