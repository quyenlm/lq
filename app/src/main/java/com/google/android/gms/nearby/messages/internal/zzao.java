package com.google.android.gms.nearby.messages.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.nearby.messages.Message;

final class zzao extends zzav {
    private /* synthetic */ Message zzbzk;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzao(zzak zzak, GoogleApiClient googleApiClient, Message message) {
        super(googleApiClient);
        this.zzbzk = message;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzah) zzb).zza(zzzX(), zzaf.zza(this.zzbzk));
    }
}
