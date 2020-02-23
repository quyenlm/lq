package com.google.android.gms.internal;

import com.google.android.gms.nearby.connection.Connections;

final class zzclb extends zzclf<Connections.EndpointDiscoveryListener> {
    private /* synthetic */ zzcoa zzbwT;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzclb(zzckz zzckz, zzcoa zzcoa) {
        super();
        this.zzbwT = zzcoa;
    }

    public final /* synthetic */ void zzq(Object obj) {
        ((Connections.EndpointDiscoveryListener) obj).onEndpointLost(this.zzbwT.zzzJ());
    }
}
