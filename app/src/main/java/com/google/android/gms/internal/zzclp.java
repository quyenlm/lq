package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzclp extends zzcmj {
    private /* synthetic */ String zzbxb;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzclp(zzclm zzclm, GoogleApiClient googleApiClient, String str) {
        super(googleApiClient, (zzcln) null);
        this.zzbxb = str;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzcnd) ((zzckm) zzb).zzrf()).zza(new zzcmk(this.zzbxb));
    }
}
