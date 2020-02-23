package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.auth.api.proxy.ProxyApi;
import com.google.android.gms.auth.api.zzd;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

abstract class zzasm extends zzbay<ProxyApi.ProxyResult, zzary> {
    public zzasm(GoogleApiClient googleApiClient) {
        super((Api<?>) zzd.API, googleApiClient);
    }

    public final /* bridge */ /* synthetic */ void setResult(Object obj) {
        super.setResult((ProxyApi.ProxyResult) obj);
    }

    /* access modifiers changed from: protected */
    public abstract void zza(Context context, zzasb zzasb) throws RemoteException;

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        zzary zzary = (zzary) zzb;
        zza(zzary.getContext(), (zzasb) zzary.zzrf());
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return new zzasq(status);
    }
}
