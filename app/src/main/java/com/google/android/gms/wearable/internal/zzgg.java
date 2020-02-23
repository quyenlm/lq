package com.google.android.gms.wearable.internal;

import com.google.android.gms.internal.zzbdz;
import com.google.android.gms.wearable.CapabilityApi;

final class zzgg implements zzbdz<CapabilityApi.CapabilityListener> {
    private /* synthetic */ zzaa zzbTB;

    zzgg(zzaa zzaa) {
        this.zzbTB = zzaa;
    }

    public final void zzpT() {
    }

    public final /* synthetic */ void zzq(Object obj) {
        ((CapabilityApi.CapabilityListener) obj).onCapabilityChanged(this.zzbTB);
    }
}
