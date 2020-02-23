package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

final class zzcbu extends zzcbw {
    private /* synthetic */ long zzbiy;
    private /* synthetic */ PendingIntent zzbiz;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcbu(zzcbt zzcbt, GoogleApiClient googleApiClient, long j, PendingIntent pendingIntent) {
        super(googleApiClient);
        this.zzbiy = j;
        this.zzbiz = pendingIntent;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzcdj) zzb).zza(this.zzbiy, this.zzbiz);
        setResult(Status.zzaBm);
    }
}
