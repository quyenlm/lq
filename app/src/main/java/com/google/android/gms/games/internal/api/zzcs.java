package com.google.android.gms.games.internal.api;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.stats.Stats;

abstract class zzcs extends Games.zza<Stats.LoadPlayerStatsResult> {
    private zzcs(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /* synthetic */ zzcs(GoogleApiClient googleApiClient, zzcr zzcr) {
        this(googleApiClient);
    }

    public final /* synthetic */ Result zzb(Status status) {
        return new zzct(this, status);
    }
}
