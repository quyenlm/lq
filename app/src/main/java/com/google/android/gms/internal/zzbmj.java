package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzbmj extends zzbmg {
    private /* synthetic */ zzboc zzaOb;
    private /* synthetic */ zzbqk zzaOc;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbmj(zzbmh zzbmh, GoogleApiClient googleApiClient, zzbqk zzbqk, zzboc zzboc) {
        super(googleApiClient);
        this.zzaOc = zzbqk;
        this.zzaOb = zzboc;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbom) ((zzbmh) zzb).zzrf()).zza(this.zzaOc, (zzboq) this.zzaOb, (String) null, (zzboo) new zzbqq(this));
    }
}
