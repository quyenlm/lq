package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.nearby.connection.DiscoveryOptions;
import com.google.android.gms.nearby.connection.Strategy;

final class zzcls extends zzcmj {
    private /* synthetic */ long zzbxc;
    private /* synthetic */ String zzbxe;
    private /* synthetic */ zzbdw zzbxf;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcls(zzclm zzclm, GoogleApiClient googleApiClient, String str, long j, zzbdw zzbdw) {
        super(googleApiClient, (zzcln) null);
        this.zzbxe = str;
        this.zzbxc = j;
        this.zzbxf = zzbdw;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        String str = this.zzbxe;
        long j = this.zzbxc;
        zzbdw zzbdw = this.zzbxf;
        ((zzcnd) ((zzckm) zzb).zzrf()).zza(new zzcoz(new zzclj(this).asBinder(), (IBinder) null, str, j, new DiscoveryOptions(Strategy.P2P_CLUSTER), new zzckz(zzbdw).asBinder()));
    }
}
