package com.google.android.gms.games.internal.api;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;

abstract class zzdl extends Games.zza<TurnBasedMultiplayer.LoadMatchResult> {
    private zzdl(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /* synthetic */ zzdl(GoogleApiClient googleApiClient, zzcv zzcv) {
        this(googleApiClient);
    }

    public final /* synthetic */ Result zzb(Status status) {
        return new zzdm(this, status);
    }
}
