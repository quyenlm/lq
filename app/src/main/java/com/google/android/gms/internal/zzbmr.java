package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzbmr extends zzbmg {
    private /* synthetic */ zzbmn zzaOj;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbmr(zzbmn zzbmn, GoogleApiClient googleApiClient) {
        super(googleApiClient);
        this.zzaOj = zzbmn;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbom) ((zzbmh) zzb).zzrf()).zza(new zzbky(this.zzaOj.zzaOg.getRequestId(), false), (zzboo) new zzbqq(this));
    }
}
