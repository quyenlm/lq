package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzcmd extends zzcmj {
    private /* synthetic */ String zzbxb;
    private /* synthetic */ zzbdw zzbxo;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcmd(zzclm zzclm, GoogleApiClient googleApiClient, String str, zzbdw zzbdw) {
        super(googleApiClient, (zzcln) null);
        this.zzbxb = str;
        this.zzbxo = zzbdw;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzcnd) ((zzckm) zzb).zzrf()).zza(new zzcki(new zzclj(this).asBinder(), (IBinder) null, this.zzbxb, (byte[]) null, new zzclg(this.zzbxo).asBinder()));
    }
}
