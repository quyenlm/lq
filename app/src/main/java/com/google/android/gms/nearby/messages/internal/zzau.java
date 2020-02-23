package com.google.android.gms.nearby.messages.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.zzbdw;

final class zzau extends zzav {
    private /* synthetic */ zzbdw zzbzj;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzau(zzak zzak, GoogleApiClient googleApiClient, zzbdw zzbdw) {
        super(googleApiClient);
        this.zzbzj = zzbdw;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzah) zzb).zzb(zzzX(), this.zzbzj);
    }
}
