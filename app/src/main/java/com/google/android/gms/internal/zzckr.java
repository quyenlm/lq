package com.google.android.gms.internal;

import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;

final class zzckr extends zzclf<ConnectionLifecycleCallback> {
    private /* synthetic */ zzcnw zzbwO;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzckr(zzcko zzcko, zzcnw zzcnw) {
        super();
        this.zzbwO = zzcnw;
    }

    public final /* synthetic */ void zzq(Object obj) {
        ((ConnectionLifecycleCallback) obj).onDisconnected(this.zzbwO.zzzF());
    }
}
