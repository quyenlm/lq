package com.google.android.gms.wearable.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.CapabilityApi;

public final class zzu implements CapabilityApi.AddLocalCapabilityResult, CapabilityApi.RemoveLocalCapabilityResult {
    private final Status mStatus;

    public zzu(Status status) {
        this.mStatus = status;
    }

    public final Status getStatus() {
        return this.mStatus;
    }
}
