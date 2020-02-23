package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.nearby.connection.DiscoveryOptions;

final class zzcma extends zzcmj {
    private /* synthetic */ String zzbxe;
    private /* synthetic */ zzbdw zzbxm;
    private /* synthetic */ DiscoveryOptions zzbxn;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcma(zzclm zzclm, GoogleApiClient googleApiClient, String str, zzbdw zzbdw, DiscoveryOptions discoveryOptions) {
        super(googleApiClient, (zzcln) null);
        this.zzbxe = str;
        this.zzbxm = zzbdw;
        this.zzbxn = discoveryOptions;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        String str = this.zzbxe;
        zzbdw zzbdw = this.zzbxm;
        ((zzcnd) ((zzckm) zzb).zzrf()).zza(new zzcoz(new zzclj(this).asBinder(), (IBinder) null, str, 0, this.zzbxn, new zzckw(zzbdw).asBinder()));
    }
}
