package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.nearby.connection.Payload;

final class zzcmf extends zzcmj {
    private /* synthetic */ Payload zzbxa;
    private /* synthetic */ String zzbxb;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcmf(zzclm zzclm, GoogleApiClient googleApiClient, String str, Payload payload) {
        super(googleApiClient, (zzcln) null);
        this.zzbxb = str;
        this.zzbxa = payload;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzckm) zzb).zza(this, new String[]{this.zzbxb}, this.zzbxa, false);
    }
}
