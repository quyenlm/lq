package com.google.android.gms.games.internal.api;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.leaderboard.Leaderboards;

abstract class zzan extends Games.zza<Leaderboards.LeaderboardMetadataResult> {
    private zzan(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /* synthetic */ zzan(GoogleApiClient googleApiClient, zzag zzag) {
        this(googleApiClient);
    }

    public final /* synthetic */ Result zzb(Status status) {
        return new zzao(this, status);
    }
}
