package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.request.SessionInsertRequest;

final class zzbzc extends zzbvr {
    private /* synthetic */ SessionInsertRequest zzaWe;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbzc(zzbyz zzbyz, GoogleApiClient googleApiClient, SessionInsertRequest sessionInsertRequest) {
        super(googleApiClient);
        this.zzaWe = sessionInsertRequest;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbwv) ((zzbvo) zzb).zzrf()).zza(new SessionInsertRequest(this.zzaWe, (zzbxg) new zzbzi(this)));
    }
}
