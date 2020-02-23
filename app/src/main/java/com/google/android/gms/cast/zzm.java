package com.google.android.gms.cast;

import com.google.android.gms.cast.Cast;
import com.google.android.gms.common.api.Status;

final class zzm implements Cast.ApplicationConnectionResult {
    private /* synthetic */ Status zzakB;

    zzm(Cast.zza zza, Status status) {
        this.zzakB = status;
    }

    public final ApplicationMetadata getApplicationMetadata() {
        return null;
    }

    public final String getApplicationStatus() {
        return null;
    }

    public final String getSessionId() {
        return null;
    }

    public final Status getStatus() {
        return this.zzakB;
    }

    public final boolean getWasLaunched() {
        return false;
    }
}
