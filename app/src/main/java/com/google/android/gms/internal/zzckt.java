package com.google.android.gms.internal;

import com.google.android.gms.nearby.connection.Connections;

final class zzckt extends zzclf<Connections.ConnectionRequestListener> {
    private /* synthetic */ zzcnq zzbwP;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzckt(zzcks zzcks, zzcnq zzcnq) {
        super();
        this.zzbwP = zzcnq;
    }

    public final /* synthetic */ void zzq(Object obj) {
        ((Connections.ConnectionRequestListener) obj).onConnectionRequest(this.zzbwP.zzzF(), this.zzbwP.zzzG(), this.zzbwP.zzzI());
    }
}
