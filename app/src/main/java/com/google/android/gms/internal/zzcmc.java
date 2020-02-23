package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzcmc extends zzcmj {
    private /* synthetic */ String val$name;
    private /* synthetic */ String zzbxb;
    private /* synthetic */ zzbdw zzbxk;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcmc(zzclm zzclm, GoogleApiClient googleApiClient, String str, String str2, zzbdw zzbdw) {
        super(googleApiClient, (zzcln) null);
        this.val$name = str;
        this.zzbxb = str2;
        this.zzbxk = zzbdw;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzcnd) ((zzckm) zzb).zzrf()).zza(new zzcot(new zzclj(this).asBinder(), (IBinder) null, (IBinder) null, this.val$name, this.zzbxb, (byte[]) null, new zzcko(this.zzbxk).asBinder()));
    }
}
