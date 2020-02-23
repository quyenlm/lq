package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.MetadataChangeSet;

final class zzbnr extends zzbny {
    private /* synthetic */ zzbnn zzaOD;
    private /* synthetic */ MetadataChangeSet zzaOr;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbnr(zzbnn zzbnn, GoogleApiClient googleApiClient, MetadataChangeSet metadataChangeSet) {
        super(zzbnn, googleApiClient, (zzbno) null);
        this.zzaOD = zzbnn;
        this.zzaOr = metadataChangeSet;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        zzbmh zzbmh = (zzbmh) zzb;
        this.zzaOr.zzsW().setContext(zzbmh.getContext());
        ((zzbom) zzbmh.zzrf()).zza(new zzbqx(this.zzaOD.zzaLV, this.zzaOr.zzsW()), (zzboo) new zzbnw(this));
    }
}
