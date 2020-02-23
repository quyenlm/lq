package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Players;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.internal.zzbaz;

final class zzbb extends zzbf {
    private /* synthetic */ int zzbbe;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbb(zzax zzax, GoogleApiClient googleApiClient, int i) {
        super(googleApiClient);
        this.zzbbe = i;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zza((zzbaz<Players.LoadPlayersResult>) this, this.zzbbe, true, false);
    }
}
