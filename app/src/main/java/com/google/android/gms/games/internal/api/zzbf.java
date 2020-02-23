package com.google.android.gms.games.internal.api;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Players;

abstract class zzbf extends Games.zza<Players.LoadPlayersResult> {
    zzbf(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    public final /* synthetic */ Result zzb(Status status) {
        return new zzbg(this, status);
    }
}
