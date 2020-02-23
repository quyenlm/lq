package com.google.android.gms.internal;

import com.google.android.gms.nearby.connection.PayloadCallback;

final class zzcli extends zzclf<PayloadCallback> {
    private /* synthetic */ zzcoe zzbwX;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcli(zzclg zzclg, zzcoe zzcoe) {
        super();
        this.zzbwX = zzcoe;
    }

    public final /* synthetic */ void zzq(Object obj) {
        ((PayloadCallback) obj).onPayloadTransferUpdate(this.zzbwX.zzzF(), this.zzbwX.zzzM());
    }
}
