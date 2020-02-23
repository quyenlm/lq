package com.google.android.gms.games.internal.api;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.video.Videos;

abstract class zzdx extends Games.zza<Videos.CaptureCapabilitiesResult> {
    private zzdx(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /* synthetic */ zzdx(GoogleApiClient googleApiClient, zzds zzds) {
        this(googleApiClient);
    }

    public final /* synthetic */ Result zzb(Status status) {
        return new zzdy(this, status);
    }
}
