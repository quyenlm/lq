package com.google.android.gms.nearby.messages.internal;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbaz;
import com.google.android.gms.internal.zzbdw;
import com.google.android.gms.internal.zzcpq;

final class zzas extends zzav {
    private /* synthetic */ PendingIntent zzaVL;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzas(zzak zzak, GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
        super(googleApiClient);
        this.zzaVL = pendingIntent;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        zzbdw<zzbaz<Status>> zzzX = zzzX();
        ((zzs) ((zzah) zzb).zzrf()).zza(new zzbe((IBinder) null, new zzcpq(zzzX), this.zzaVL));
    }
}
