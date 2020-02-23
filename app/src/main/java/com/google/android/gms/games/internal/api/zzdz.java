package com.google.android.gms.games.internal.api;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.video.Videos;

abstract class zzdz extends Games.zza<Videos.CaptureStateResult> {
    private zzdz(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /* synthetic */ zzdz(GoogleApiClient googleApiClient, zzds zzds) {
        this(googleApiClient);
    }

    public final /* synthetic */ Result zzb(Status status) {
        return new zzea(this, status);
    }
}
