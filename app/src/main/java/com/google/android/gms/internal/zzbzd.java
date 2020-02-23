package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.request.SessionReadRequest;
import com.google.android.gms.fitness.result.SessionReadResult;

final class zzbzd extends zzbvp<SessionReadResult> {
    private /* synthetic */ SessionReadRequest zzaWf;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbzd(zzbyz zzbyz, GoogleApiClient googleApiClient, SessionReadRequest sessionReadRequest) {
        super(googleApiClient);
        this.zzaWf = sessionReadRequest;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbwv) ((zzbvo) zzb).zzrf()).zza(new SessionReadRequest(this.zzaWf, (zzbxa) new zzbzg(this, (zzbza) null)));
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return SessionReadResult.zzE(status);
    }
}
