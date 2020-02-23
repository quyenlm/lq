package com.google.android.gms.nearby.messages.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.zzbdw;
import com.google.android.gms.nearby.messages.MessageListener;

final class zzar extends zzav {
    private /* synthetic */ zzbdw zzbhE;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzar(zzak zzak, GoogleApiClient googleApiClient, zzbdw zzbdw) {
        super(googleApiClient);
        this.zzbhE = zzbdw;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzah) zzb).zza(zzzX(), (zzbdw<MessageListener>) this.zzbhE);
    }
}
