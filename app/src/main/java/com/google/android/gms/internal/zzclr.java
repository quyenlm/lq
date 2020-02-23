package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.nearby.connection.AdvertisingOptions;
import com.google.android.gms.nearby.connection.Strategy;

final class zzclr extends zzcmh {
    private /* synthetic */ String val$name;
    private /* synthetic */ long zzbxc;
    private /* synthetic */ zzbdw zzbxd;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzclr(zzclm zzclm, GoogleApiClient googleApiClient, String str, long j, zzbdw zzbdw) {
        super(googleApiClient, (zzcln) null);
        this.val$name = str;
        this.zzbxc = j;
        this.zzbxd = zzbdw;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        String str = this.val$name;
        long j = this.zzbxc;
        zzbdw zzbdw = this.zzbxd;
        ((zzcnd) ((zzckm) zzb).zzrf()).zza(new zzcox(new zzcll(this).asBinder(), new zzcks(zzbdw).asBinder(), str, "__LEGACY_SERVICE_ID__", j, new AdvertisingOptions(Strategy.P2P_CLUSTER), (IBinder) null));
    }
}
