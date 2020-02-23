package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.request.zzba;
import com.google.android.gms.fitness.result.SessionStopResult;
import java.util.Collections;

final class zzbzb extends zzbvp<SessionStopResult> {
    private /* synthetic */ String val$name = null;
    private /* synthetic */ String zzaWd;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbzb(zzbyz zzbyz, GoogleApiClient googleApiClient, String str, String str2) {
        super(googleApiClient);
        this.zzaWd = str2;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbwv) ((zzbvo) zzb).zzrf()).zza(new zzba(this.val$name, this.zzaWd, new zzbzh(this, (zzbza) null)));
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return new SessionStopResult(status, Collections.emptyList());
    }
}
