package com.google.android.gms.games.internal.api;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;

abstract class zzv extends Games.zza<Result> {
    private zzv(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /* synthetic */ zzv(GoogleApiClient googleApiClient, zzq zzq) {
        this(googleApiClient);
    }

    public final Result zzb(Status status) {
        return new zzw(this, status);
    }
}
