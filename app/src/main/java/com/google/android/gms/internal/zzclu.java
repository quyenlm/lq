package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzclu extends zzcmj {
    private /* synthetic */ String zzbxb;
    private /* synthetic */ byte[] zzbxg;
    private /* synthetic */ zzbdw zzbxi;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzclu(zzclm zzclm, GoogleApiClient googleApiClient, String str, byte[] bArr, zzbdw zzbdw) {
        super(googleApiClient, (zzcln) null);
        this.zzbxb = str;
        this.zzbxg = bArr;
        this.zzbxi = zzbdw;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzcnd) ((zzckm) zzb).zzrf()).zza(new zzcki(new zzclj(this).asBinder(), new zzclc(this.zzbxi).asBinder(), this.zzbxb, this.zzbxg, (IBinder) null));
    }
}
