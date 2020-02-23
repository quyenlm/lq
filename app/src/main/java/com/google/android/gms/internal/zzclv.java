package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzclv extends zzcmj {
    private /* synthetic */ String zzbxb;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzclv(zzclm zzclm, GoogleApiClient googleApiClient, String str) {
        super(googleApiClient, (zzcln) null);
        this.zzbxb = str;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzckm) zzb).zzj(this, this.zzbxb);
    }
}
