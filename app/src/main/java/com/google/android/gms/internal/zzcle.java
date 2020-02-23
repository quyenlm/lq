package com.google.android.gms.internal;

import com.google.android.gms.nearby.connection.Connections;

final class zzcle extends zzclf<Connections.MessageListener> {
    private /* synthetic */ zzcnw zzbwO;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcle(zzclc zzclc, zzcnw zzcnw) {
        super();
        this.zzbwO = zzcnw;
    }

    public final /* synthetic */ void zzq(Object obj) {
        ((Connections.MessageListener) obj).onDisconnected(this.zzbwO.zzzF());
    }
}
