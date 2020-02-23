package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzq;
import com.tencent.smtt.sdk.TbsListener;

public final class zzbux extends zzbun<zzbwl> {
    public static final Api<Api.ApiOptions.NoOptions> API = new Api<>("Fitness.GOALS_API", new zzbuz(), zzajR);
    private static Api.zzf<zzbux> zzajR = new Api.zzf<>();

    public zzbux(Context context, Looper looper, zzq zzq, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, TbsListener.ErrorCode.DOWNLOAD_THROWABLE, connectionCallbacks, onConnectionFailedListener, zzq);
    }

    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IGoogleFitGoalsApi");
        return queryLocalInterface instanceof zzbwl ? (zzbwl) queryLocalInterface : new zzbwm(iBinder);
    }

    public final String zzdb() {
        return "com.google.android.gms.fitness.GoalsApi";
    }

    public final String zzdc() {
        return "com.google.android.gms.fitness.internal.IGoogleFitGoalsApi";
    }
}
