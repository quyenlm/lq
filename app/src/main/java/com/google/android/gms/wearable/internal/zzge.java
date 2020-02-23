package com.google.android.gms.wearable.internal;

import com.google.android.gms.internal.zzbdz;
import com.google.android.gms.wearable.NodeApi;

final class zzge implements zzbdz<NodeApi.NodeListener> {
    private /* synthetic */ zzeg zzbRC;

    zzge(zzeg zzeg) {
        this.zzbRC = zzeg;
    }

    public final void zzpT() {
    }

    public final /* synthetic */ void zzq(Object obj) {
        ((NodeApi.NodeListener) obj).onPeerDisconnected(this.zzbRC);
    }
}
