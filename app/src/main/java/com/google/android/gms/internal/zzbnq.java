package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.List;

final class zzbnq extends zzbmg {
    private /* synthetic */ zzbnn zzaOD;
    private /* synthetic */ List zzaOE;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbnq(zzbnn zzbnn, GoogleApiClient googleApiClient, List list) {
        super(googleApiClient);
        this.zzaOD = zzbnn;
        this.zzaOE = list;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbom) ((zzbmh) zzb).zzrf()).zza(new zzbqo(this.zzaOD.zzaLV, this.zzaOE), (zzboo) new zzbqq(this));
    }
}
