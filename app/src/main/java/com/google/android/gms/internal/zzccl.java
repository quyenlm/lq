package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzccl extends zzccm {
    private /* synthetic */ PendingIntent zzbiz;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzccl(zzccb zzccb, GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        super(googleApiClient);
        this.zzbiz = pendingIntent;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzcdj) zzb).zza(this.zzbiz, (zzccu) new zzccn(this));
    }
}
