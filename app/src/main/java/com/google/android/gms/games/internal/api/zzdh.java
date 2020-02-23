package com.google.android.gms.games.internal.api;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;

abstract class zzdh extends Games.zza<TurnBasedMultiplayer.InitiateMatchResult> {
    private zzdh(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /* synthetic */ zzdh(GoogleApiClient googleApiClient, zzcv zzcv) {
        this(googleApiClient);
    }

    public final /* synthetic */ Result zzb(Status status) {
        return new zzdi(this, status);
    }
}
