package com.google.android.gms.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback;
import com.google.android.gms.nearby.connection.ConnectionResolution;

final class zzckq extends zzclf<ConnectionLifecycleCallback> {
    private /* synthetic */ zzcnu zzbwN;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzckq(zzcko zzcko, zzcnu zzcnu) {
        super();
        this.zzbwN = zzcnu;
    }

    public final /* synthetic */ void zzq(Object obj) {
        ((ConnectionLifecycleCallback) obj).onConnectionResult(this.zzbwN.zzzF(), new ConnectionResolution(new Status(this.zzbwN.getStatusCode())));
    }
}
