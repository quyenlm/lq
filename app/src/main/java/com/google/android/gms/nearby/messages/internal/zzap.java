package com.google.android.gms.nearby.messages.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.zzbdw;
import com.google.android.gms.nearby.messages.SubscribeOptions;

final class zzap extends zzav {
    private /* synthetic */ zzbdw zzbhE;
    private /* synthetic */ zzbdw zzbzl;
    private /* synthetic */ SubscribeOptions zzbzn;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzap(zzak zzak, GoogleApiClient googleApiClient, zzbdw zzbdw, zzbdw zzbdw2, SubscribeOptions subscribeOptions) {
        super(googleApiClient);
        this.zzbhE = zzbdw;
        this.zzbzl = zzbdw2;
        this.zzbzn = subscribeOptions;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzah) zzb).zza(zzzX(), this.zzbhE, this.zzbzl, this.zzbzn, (byte[]) null);
    }
}
