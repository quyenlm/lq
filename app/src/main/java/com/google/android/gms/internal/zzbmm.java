package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.List;

final class zzbmm extends zzbmg {
    private /* synthetic */ List zzaOf;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbmm(zzbmh zzbmh, GoogleApiClient googleApiClient, List list) {
        super(googleApiClient);
        this.zzaOf = list;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbom) ((zzbmh) zzb).zzrf()).zza(new zzbku(this.zzaOf), (zzboo) new zzbqq(this));
    }
}
