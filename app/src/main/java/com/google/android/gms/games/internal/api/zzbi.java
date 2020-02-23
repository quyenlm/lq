package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.internal.GamesClientImpl;

final class zzbi extends zzbm {
    private /* synthetic */ String zzbbf;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbi(zzbh zzbh, GoogleApiClient googleApiClient, String str) {
        super(googleApiClient, (zzbi) null);
        this.zzbbf = str;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zzh(this, this.zzbbf);
    }
}
