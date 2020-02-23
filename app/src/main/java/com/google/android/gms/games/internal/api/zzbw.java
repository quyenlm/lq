package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.request.Requests;
import com.google.android.gms.internal.zzbaz;

final class zzbw extends zzbx {
    private /* synthetic */ int zzbaV;
    private /* synthetic */ int zzbbk;
    private /* synthetic */ int zzbbl;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbw(zzbt zzbt, GoogleApiClient googleApiClient, int i, int i2, int i3) {
        super(googleApiClient, (zzbu) null);
        this.zzbbk = i;
        this.zzbbl = i2;
        this.zzbaV = i3;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zza((zzbaz<Requests.LoadRequestsResult>) this, this.zzbbk, this.zzbbl, this.zzbaV);
    }
}
