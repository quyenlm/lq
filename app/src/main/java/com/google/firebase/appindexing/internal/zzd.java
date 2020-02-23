package com.google.firebase.appindexing.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.internal.zzz;

public final class zzd extends zzz<zzu> {
    static final Api<Api.ApiOptions.NoOptions> API = new Api<>("AppIndexing.API", zzajS, zzajR);
    private static final Api.zzf<zzd> zzajR = new Api.zzf<>();
    private static final Api.zza<zzd, Api.ApiOptions.NoOptions> zzajS = new zze();

    public zzd(Context context, Looper looper, zzq zzq, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 113, zzq, connectionCallbacks, onConnectionFailedListener);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.firebase.appindexing.internal.IAppIndexingService");
        return queryLocalInterface instanceof zzu ? (zzu) queryLocalInterface : new zzv(iBinder);
    }

    /* access modifiers changed from: protected */
    public final String zzdb() {
        return "com.google.android.gms.icing.APP_INDEXING_SERVICE";
    }

    /* access modifiers changed from: protected */
    public final String zzdc() {
        return "com.google.firebase.appindexing.internal.IAppIndexingService";
    }
}
