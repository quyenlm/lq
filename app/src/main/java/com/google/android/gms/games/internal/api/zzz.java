package com.google.android.gms.games.internal.api;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesMetadata;

abstract class zzz extends Games.zza<GamesMetadata.LoadGamesResult> {
    private zzz(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /* synthetic */ zzz(GoogleApiClient googleApiClient, zzy zzy) {
        this(googleApiClient);
    }

    public final /* synthetic */ Result zzb(Status status) {
        return new zzaa(this, status);
    }
}
