package com.google.android.gms.internal;

import com.google.android.gms.nearby.connection.Connections;

final class zzcla extends zzclf<Connections.EndpointDiscoveryListener> {
    private /* synthetic */ zzcny zzbwS;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcla(zzckz zzckz, zzcny zzcny) {
        super();
        this.zzbwS = zzcny;
    }

    public final /* synthetic */ void zzq(Object obj) {
        ((Connections.EndpointDiscoveryListener) obj).onEndpointFound(this.zzbwS.zzzJ(), this.zzbwS.getServiceId(), this.zzbwS.getEndpointName());
    }
}
