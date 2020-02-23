package com.google.android.gms.wearable.internal;

import com.google.android.gms.internal.zzbdz;
import com.google.android.gms.wearable.NodeApi;

final class zzgd implements zzbdz<NodeApi.NodeListener> {
    private /* synthetic */ zzeg zzbRC;

    zzgd(zzeg zzeg) {
        this.zzbRC = zzeg;
    }

    public final void zzpT() {
    }

    public final /* synthetic */ void zzq(Object obj) {
        ((NodeApi.NodeListener) obj).onPeerConnected(this.zzbRC);
    }
}
