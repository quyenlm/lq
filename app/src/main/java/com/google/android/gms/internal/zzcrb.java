package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.internal.zzh;

final class zzcrb extends zzcrg {
    private /* synthetic */ int zzbAO;
    private /* synthetic */ String zzbAP;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcrb(zzcra zzcra, GoogleApiClient googleApiClient, int i, String str) {
        super(googleApiClient, (zzcrb) null);
        this.zzbAO = i;
        this.zzbAP = str;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        zza(((zzh) zzb).zza(this, this.zzbAO, this.zzbAP));
    }
}
