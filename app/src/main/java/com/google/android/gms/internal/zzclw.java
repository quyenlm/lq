package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.nearby.connection.Payload;

final class zzclw extends zzcmj {
    private /* synthetic */ String zzbxb;
    private /* synthetic */ byte[] zzbxj;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzclw(zzclm zzclm, GoogleApiClient googleApiClient, String str, byte[] bArr) {
        super(googleApiClient, (zzcln) null);
        this.zzbxb = str;
        this.zzbxj = bArr;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzckm) zzb).zza(this, new String[]{this.zzbxb}, Payload.fromBytes(this.zzbxj), true);
    }
}
