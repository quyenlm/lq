package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.hardware.display.VirtualDisplay;
import com.google.android.gms.cast.CastRemoteDisplay;
import com.google.android.gms.cast.CastRemoteDisplayApi;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;

public final class zzayw implements CastRemoteDisplayApi {
    /* access modifiers changed from: private */
    public static final zzayo zzapq = new zzayo("CastRemoteDisplayApiImpl");
    /* access modifiers changed from: private */
    public Api<?> zzayW;
    /* access modifiers changed from: private */
    public VirtualDisplay zzayX;
    /* access modifiers changed from: private */
    public final zzazl zzayY = new zzayx(this);

    public zzayw(Api api) {
        this.zzayW = api;
    }

    /* access modifiers changed from: private */
    @TargetApi(19)
    public final void zzoP() {
        if (this.zzayX != null) {
            if (this.zzayX.getDisplay() != null) {
                zzapq.zzb(new StringBuilder(38).append("releasing virtual display: ").append(this.zzayX.getDisplay().getDisplayId()).toString(), new Object[0]);
            }
            this.zzayX.release();
            this.zzayX = null;
        }
    }

    public final PendingResult<CastRemoteDisplay.CastRemoteDisplaySessionResult> startRemoteDisplay(GoogleApiClient googleApiClient, String str) {
        zzapq.zzb("startRemoteDisplay", new Object[0]);
        return googleApiClient.zze(new zzayy(this, googleApiClient, str));
    }

    public final PendingResult<CastRemoteDisplay.CastRemoteDisplaySessionResult> stopRemoteDisplay(GoogleApiClient googleApiClient) {
        zzapq.zzb("stopRemoteDisplay", new Object[0]);
        return googleApiClient.zze(new zzayz(this, googleApiClient));
    }
}
