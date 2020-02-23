package com.google.android.gms.internal;

import com.google.android.gms.nearby.connection.ConnectionInfo;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;

final class zzckp extends zzclf<ConnectionLifecycleCallback> {
    private /* synthetic */ zzcno zzbwM;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzckp(zzcko zzcko, zzcno zzcno) {
        super();
        this.zzbwM = zzcno;
    }

    public final /* synthetic */ void zzq(Object obj) {
        ((ConnectionLifecycleCallback) obj).onConnectionInitiated(this.zzbwM.zzzF(), new ConnectionInfo(this.zzbwM.zzzG(), this.zzbwM.getAuthenticationToken(), this.zzbwM.zzzH()));
    }
}
