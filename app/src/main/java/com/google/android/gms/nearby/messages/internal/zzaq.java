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
import com.google.android.gms.internal.zzcpx;
import com.google.android.gms.nearby.messages.SubscribeOptions;

final class zzaq extends zzav {
    private /* synthetic */ PendingIntent zzaVL;
    private /* synthetic */ zzbdw zzbzl;
    private /* synthetic */ SubscribeOptions zzbzn;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzaq(zzak zzak, GoogleApiClient googleApiClient, PendingIntent pendingIntent, zzbdw zzbdw, SubscribeOptions subscribeOptions) {
        super(googleApiClient);
        this.zzaVL = pendingIntent;
        this.zzbzl = zzbdw;
        this.zzbzn = subscribeOptions;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        zzah zzah = (zzah) zzb;
        zzbdw<zzbaz<Status>> zzzX = zzzX();
        PendingIntent pendingIntent = this.zzaVL;
        zzbdw zzbdw = this.zzbzl;
        SubscribeOptions subscribeOptions = this.zzbzn;
        ((zzs) zzah.zzrf()).zza(new SubscribeRequest((IBinder) null, subscribeOptions.getStrategy(), new zzcpq(zzzX), subscribeOptions.getFilter(), pendingIntent, (byte[]) null, zzbdw == null ? null : new zzcpx(zzbdw), subscribeOptions.zzbyA, subscribeOptions.zzbyB));
    }
}
