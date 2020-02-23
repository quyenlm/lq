package com.google.android.gms.internal;

import com.google.android.gms.nearby.connection.EndpointDiscoveryCallback;

final class zzcky extends zzclf<EndpointDiscoveryCallback> {
    private /* synthetic */ zzcoa zzbwT;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcky(zzckw zzckw, zzcoa zzcoa) {
        super();
        this.zzbwT = zzcoa;
    }

    public final /* synthetic */ void zzq(Object obj) {
        ((EndpointDiscoveryCallback) obj).onEndpointLost(this.zzbwT.zzzJ());
    }
}
