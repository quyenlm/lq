package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;
import com.google.android.gms.internal.zzbaz;

final class zzdd extends zzdf {
    private /* synthetic */ String zzbbw;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzdd(zzcu zzcu, String str, GoogleApiClient googleApiClient, String str2) {
        super(str, googleApiClient);
        this.zzbbw = str2;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zzf((zzbaz<TurnBasedMultiplayer.CancelMatchResult>) this, this.zzbbw);
    }
}
