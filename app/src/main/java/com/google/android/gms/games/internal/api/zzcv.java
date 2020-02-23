package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchConfig;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;
import com.google.android.gms.internal.zzbaz;

final class zzcv extends zzdh {
    private /* synthetic */ TurnBasedMatchConfig zzbbv;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcv(zzcu zzcu, GoogleApiClient googleApiClient, TurnBasedMatchConfig turnBasedMatchConfig) {
        super(googleApiClient, (zzcv) null);
        this.zzbbv = turnBasedMatchConfig;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zza((zzbaz<TurnBasedMultiplayer.InitiateMatchResult>) this, this.zzbbv);
    }
}
