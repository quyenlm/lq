package com.google.android.gms.internal;

import com.google.android.gms.nearby.connection.DiscoveredEndpointInfo;
import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;

final class zzckx extends zzclf<EndpointDiscoveryCallback> {
    private /* synthetic */ zzcny zzbwS;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzckx(zzckw zzckw, zzcny zzcny) {
        super();
        this.zzbwS = zzcny;
    }

    public final /* synthetic */ void zzq(Object obj) {
        ((EndpointDiscoveryCallback) obj).onEndpointFound(this.zzbwS.zzzJ(), new DiscoveredEndpointInfo(this.zzbwS.getServiceId(), this.zzbwS.getEndpointName()));
    }
}
