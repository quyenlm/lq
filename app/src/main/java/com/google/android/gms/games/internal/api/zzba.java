package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Players;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.internal.zzbaz;

final class zzba extends zzbf {
    private /* synthetic */ boolean zzbaP;
    private /* synthetic */ int zzbbe;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzba(zzax zzax, GoogleApiClient googleApiClient, int i, boolean z) {
        super(googleApiClient);
        this.zzbbe = i;
        this.zzbaP = z;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zza((zzbaz<Players.LoadPlayersResult>) this, this.zzbbe, false, this.zzbaP);
    }
}
