package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.awareness.fence.FenceUpdateRequest;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

final class zzbiq extends zzbjz {
    private /* synthetic */ FenceUpdateRequest zzaKQ;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbiq(zzbip zzbip, GoogleApiClient googleApiClient, FenceUpdateRequest fenceUpdateRequest) {
        super(googleApiClient);
        this.zzaKQ = fenceUpdateRequest;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbka) zzb).zza((zzbaz<Status>) this, (zzbjj) this.zzaKQ);
    }
}
