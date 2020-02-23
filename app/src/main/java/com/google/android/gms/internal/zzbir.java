package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.awareness.fence.FenceQueryRequest;
import com.google.android.gms.awareness.fence.FenceQueryResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

final class zzbir extends zzbjv {
    private /* synthetic */ FenceQueryRequest zzaKR;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbir(zzbip zzbip, GoogleApiClient googleApiClient, FenceQueryRequest fenceQueryRequest) {
        super(googleApiClient);
        this.zzaKR = fenceQueryRequest;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbka) zzb).zza((zzbaz<FenceQueryResult>) this, (zzbja) this.zzaKR);
    }
}
