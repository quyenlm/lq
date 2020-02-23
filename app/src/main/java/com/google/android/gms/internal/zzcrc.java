package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.internal.zzh;

final class zzcrc extends zzcrg {
    private /* synthetic */ String zzbAP;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcrc(zzcra zzcra, GoogleApiClient googleApiClient, String str) {
        super(googleApiClient, (zzcrb) null);
        this.zzbAP = str;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        zza(((zzh) zzb).zza(this, 0, this.zzbAP));
    }
}
