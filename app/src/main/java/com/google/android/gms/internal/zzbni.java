package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzbni extends zzbnm {
    private /* synthetic */ zzbnh zzaOz;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbni(zzbnh zzbnh, GoogleApiClient googleApiClient) {
        super(zzbnh, googleApiClient);
        this.zzaOz = zzbnh;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbom) ((zzbmh) zzb).zzrf()).zzb(new zzbnk(this.zzaOz, this, (zzbni) null));
    }
}
