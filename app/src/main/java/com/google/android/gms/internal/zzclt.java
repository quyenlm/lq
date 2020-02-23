package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzclt extends zzcmj {
    private /* synthetic */ String val$name;
    private /* synthetic */ String zzbxb;
    private /* synthetic */ byte[] zzbxg;
    private /* synthetic */ zzbdw zzbxh;
    private /* synthetic */ zzbdw zzbxi;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzclt(zzclm zzclm, GoogleApiClient googleApiClient, String str, String str2, byte[] bArr, zzbdw zzbdw, zzbdw zzbdw2) {
        super(googleApiClient, (zzcln) null);
        this.val$name = str;
        this.zzbxb = str2;
        this.zzbxg = bArr;
        this.zzbxh = zzbdw;
        this.zzbxi = zzbdw2;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        String str = this.val$name;
        String str2 = this.zzbxb;
        byte[] bArr = this.zzbxg;
        zzbdw zzbdw = this.zzbxh;
        ((zzcnd) ((zzckm) zzb).zzrf()).zza(new zzcot(new zzclj(this).asBinder(), new zzclc(this.zzbxi).asBinder(), new zzcku(zzbdw).asBinder(), str, str2, bArr, (IBinder) null));
    }
}
