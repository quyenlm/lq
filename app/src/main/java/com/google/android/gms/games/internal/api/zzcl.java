package com.google.android.gms.games.internal.api;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.snapshot.Snapshots;

abstract class zzcl extends Games.zza<Snapshots.LoadSnapshotsResult> {
    private zzcl(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /* synthetic */ zzcl(GoogleApiClient googleApiClient, zzcc zzcc) {
        this(googleApiClient);
    }

    public final /* synthetic */ Result zzb(Status status) {
        return new zzcm(this, status);
    }
}
