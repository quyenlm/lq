package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.internal.zzz;
import com.tencent.smtt.sdk.TbsListener;

public final class zzcbo extends zzz<zzcbl> {
    public zzcbo(Context context, Looper looper, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, TbsListener.ErrorCode.THREAD_INIT_ERROR, zzq.zzaA(context), connectionCallbacks, onConnectionFailedListener);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.instantapps.internal.IInstantAppsService");
        return queryLocalInterface instanceof zzcbl ? (zzcbl) queryLocalInterface : new zzcbm(iBinder);
    }

    /* access modifiers changed from: protected */
    @NonNull
    public final String zzdb() {
        return "com.google.android.gms.instantapps.START";
    }

    /* access modifiers changed from: protected */
    @NonNull
    public final String zzdc() {
        return "com.google.android.gms.instantapps.internal.IInstantAppsService";
    }
}
