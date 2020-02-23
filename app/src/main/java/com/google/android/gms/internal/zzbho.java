package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;

abstract class zzbho<R extends Result> extends zzbay<R, zzbht> {
    public zzbho(GoogleApiClient googleApiClient) {
        super((Api<?>) zzbhb.API, googleApiClient);
    }

    public final /* bridge */ /* synthetic */ void setResult(Object obj) {
        super.setResult((Result) obj);
    }

    /* access modifiers changed from: protected */
    public abstract void zza(Context context, zzbie zzbie) throws RemoteException;

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        zzbht zzbht = (zzbht) zzb;
        zza(zzbht.getContext(), (zzbie) zzbht.zzrf());
    }
}
