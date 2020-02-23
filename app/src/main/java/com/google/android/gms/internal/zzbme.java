package com.google.android.gms.internal;

import android.annotation.SuppressLint;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

@SuppressLint({"MissingRemoteException"})
final class zzbme extends zzbmg {
    zzbme(GoogleApiClient googleApiClient, Status status) {
        super(googleApiClient);
        setResult(status);
    }

    /* access modifiers changed from: protected */
    public final /* bridge */ /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
    }
}
