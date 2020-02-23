package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzbmk extends zzbmg {
    private /* synthetic */ zzbkr zzaOa;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbmk(zzbmh zzbmh, GoogleApiClient googleApiClient, zzbkr zzbkr) {
        super(googleApiClient);
        this.zzaOa = zzbkr;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbom) ((zzbmh) zzb).zzrf()).zza(this.zzaOa, (zzboq) null, (String) null, (zzboo) new zzbqq(this));
    }
}
