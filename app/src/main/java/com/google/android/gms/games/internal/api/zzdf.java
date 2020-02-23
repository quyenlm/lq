package com.google.android.gms.games.internal.api;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;

abstract class zzdf extends Games.zza<TurnBasedMultiplayer.CancelMatchResult> {
    /* access modifiers changed from: private */
    public final String zzIi;

    public zzdf(String str, GoogleApiClient googleApiClient) {
        super(googleApiClient);
        this.zzIi = str;
    }

    public final /* synthetic */ Result zzb(Status status) {
        return new zzdg(this, status);
    }
}
