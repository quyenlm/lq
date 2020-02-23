package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.nearby.connection.AdvertisingOptions;

final class zzcly extends zzcmh {
    private /* synthetic */ String val$name;
    private /* synthetic */ String zzbxe;
    private /* synthetic */ zzbdw zzbxk;
    private /* synthetic */ AdvertisingOptions zzbxl;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcly(zzclm zzclm, GoogleApiClient googleApiClient, String str, String str2, zzbdw zzbdw, AdvertisingOptions advertisingOptions) {
        super(googleApiClient, (zzcln) null);
        this.val$name = str;
        this.zzbxe = str2;
        this.zzbxk = zzbdw;
        this.zzbxl = advertisingOptions;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        String str = this.val$name;
        String str2 = this.zzbxe;
        zzbdw zzbdw = this.zzbxk;
        ((zzcnd) ((zzckm) zzb).zzrf()).zza(new zzcox(new zzcll(this).asBinder(), (IBinder) null, str, str2, 0, this.zzbxl, new zzcko(zzbdw).asBinder()));
    }
}
