package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.internal.zzh;

final class zzcrd extends zzcrg {
    zzcrd(zzcra zzcra, GoogleApiClient googleApiClient) {
        super(googleApiClient, (zzcrb) null);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzh) zzb).zzj(this);
    }
}
