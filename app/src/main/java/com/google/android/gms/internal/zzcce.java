package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

final class zzcce extends zzccm {
    private /* synthetic */ boolean zzbiG;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcce(zzccb zzccb, GoogleApiClient googleApiClient, boolean z) {
        super(googleApiClient);
        this.zzbiG = z;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzcdj) zzb).zzai(this.zzbiG);
        setResult(Status.zzaBm);
    }
}
