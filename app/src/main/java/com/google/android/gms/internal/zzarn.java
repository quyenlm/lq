package com.google.android.gms.internal;

import android.content.Context;
import android.os.DeadObjectException;
import android.os.RemoteException;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;

abstract class zzarn<R extends Result> extends zzbay<R, zzaro> {
    zzarn(GoogleApiClient googleApiClient) {
        super((Api<?>) Auth.CREDENTIALS_API, googleApiClient);
    }

    public final /* bridge */ /* synthetic */ void setResult(Object obj) {
        super.setResult((Result) obj);
    }

    /* access modifiers changed from: protected */
    public abstract void zza(Context context, zzart zzart) throws DeadObjectException, RemoteException;

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        zzaro zzaro = (zzaro) zzb;
        zza(zzaro.getContext(), (zzart) zzaro.zzrf());
    }
}
