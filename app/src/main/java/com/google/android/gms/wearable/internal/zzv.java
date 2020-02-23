package com.google.android.gms.wearable.internal;

import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.CapabilityInfo;

final class zzv implements CapabilityApi.CapabilityListener {
    private CapabilityApi.CapabilityListener zzbRY;
    private String zzbRZ;

    zzv(CapabilityApi.CapabilityListener capabilityListener, String str) {
        this.zzbRY = capabilityListener;
        this.zzbRZ = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        zzv zzv = (zzv) obj;
        if (this.zzbRY.equals(zzv.zzbRY)) {
            return this.zzbRZ.equals(zzv.zzbRZ);
        }
        return false;
    }

    public final int hashCode() {
        return (this.zzbRY.hashCode() * 31) + this.zzbRZ.hashCode();
    }

    public final void onCapabilityChanged(CapabilityInfo capabilityInfo) {
        this.zzbRY.onCapabilityChanged(capabilityInfo);
    }
}
