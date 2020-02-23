package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.internal.zzh;

final class zzcrf extends zzcrg {
    private /* synthetic */ String[] zzbAR;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcrf(zzcra zzcra, GoogleApiClient googleApiClient, String[] strArr) {
        super(googleApiClient, (zzcrb) null);
        this.zzbAR = strArr;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzh) zzb).zzc(this, this.zzbAR);
    }
}
