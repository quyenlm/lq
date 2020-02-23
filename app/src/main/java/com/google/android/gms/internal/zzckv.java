package com.google.android.gms.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.nearby.connection.Connections;

final class zzckv extends zzclf<Connections.ConnectionResponseCallback> {
    private /* synthetic */ zzcns zzbwR;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzckv(zzcku zzcku, zzcns zzcns) {
        super();
        this.zzbwR = zzcns;
    }

    public final /* synthetic */ void zzq(Object obj) {
        ((Connections.ConnectionResponseCallback) obj).onConnectionResponse(this.zzbwR.zzzF(), new Status(this.zzbwR.getStatusCode()), this.zzbwR.zzzI());
    }
}
