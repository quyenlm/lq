package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzq;

public final class zzbup extends zzbun<zzbwh> {
    public static final Api<Api.ApiOptions.NoOptions> API = new Api<>("Fitness.BLE_API", new zzbur(), zzajR);
    private static Api.zzf<zzbup> zzajR = new Api.zzf<>();

    public zzbup(Context context, Looper looper, zzq zzq, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 59, connectionCallbacks, onConnectionFailedListener, zzq);
    }

    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IGoogleFitBleApi");
        return queryLocalInterface instanceof zzbwh ? (zzbwh) queryLocalInterface : new zzbwi(iBinder);
    }

    public final String zzdb() {
        return "com.google.android.gms.fitness.BleApi";
    }

    public final String zzdc() {
        return "com.google.android.gms.fitness.internal.IGoogleFitBleApi";
    }
}
