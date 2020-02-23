package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzbmi extends zzbmg {
    private /* synthetic */ zzbkr zzaOa;
    private /* synthetic */ zzboc zzaOb;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbmi(zzbmh zzbmh, GoogleApiClient googleApiClient, zzbkr zzbkr, zzboc zzboc) {
        super(googleApiClient);
        this.zzaOa = zzbkr;
        this.zzaOb = zzboc;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbom) ((zzbmh) zzb).zzrf()).zza(this.zzaOa, (zzboq) this.zzaOb, (String) null, (zzboo) new zzbqq(this));
    }
}
