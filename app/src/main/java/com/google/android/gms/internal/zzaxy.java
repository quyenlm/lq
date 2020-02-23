package com.google.android.gms.internal;

import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.cast.Cast;
import com.google.android.gms.common.api.Status;

final class zzaxy implements Cast.ApplicationConnectionResult {
    private final String mSessionId;
    private final Status mStatus;
    private final ApplicationMetadata zzaye;
    private final String zzayf;
    private final boolean zzayg;

    public zzaxy(Status status) {
        this(status, (ApplicationMetadata) null, (String) null, (String) null, false);
    }

    public zzaxy(Status status, ApplicationMetadata applicationMetadata, String str, String str2, boolean z) {
        this.mStatus = status;
        this.zzaye = applicationMetadata;
        this.zzayf = str;
        this.mSessionId = str2;
        this.zzayg = z;
    }

    public final ApplicationMetadata getApplicationMetadata() {
        return this.zzaye;
    }

    public final String getApplicationStatus() {
        return this.zzayf;
    }

    public final String getSessionId() {
        return this.mSessionId;
    }

    public final Status getStatus() {
        return this.mStatus;
    }

    public final boolean getWasLaunched() {
        return this.zzayg;
    }
}
