package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.MessageApi;

public final class zzdw implements MessageApi.SendMessageResult {
    private final Status mStatus;
    private final int zzaLT;

    public zzdw(Status status, int i) {
        this.mStatus = status;
        this.zzaLT = i;
    }

    public final int getRequestId() {
        return this.zzaLT;
    }

    public final Status getStatus() {
        return this.mStatus;
    }
}
