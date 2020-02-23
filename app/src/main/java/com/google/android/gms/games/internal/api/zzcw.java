package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.internal.GamesClientImpl;

final class zzcw extends zzdl {
    private /* synthetic */ String zzbbw;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcw(zzcu zzcu, GoogleApiClient googleApiClient, String str) {
        super(googleApiClient, (zzcv) null);
        this.zzbbw = str;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zzg(this, this.zzbbw);
    }
}
