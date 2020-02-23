package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Players;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.internal.zzbaz;

final class zzbe extends zzbf {
    private /* synthetic */ boolean zzbaP;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbe(zzax zzax, GoogleApiClient googleApiClient, boolean z) {
        super(googleApiClient);
        this.zzbaP = z;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zza((zzbaz<Players.LoadPlayersResult>) this, this.zzbaP);
    }
}
