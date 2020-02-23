package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.auth.account.zzd;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

final class zzaqo extends zzbay<Result, zzaqx> {
    private /* synthetic */ boolean val$enabled;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzaqo(zzaqn zzaqn, Api api, GoogleApiClient googleApiClient, boolean z) {
        super((Api<?>) api, googleApiClient);
        this.val$enabled = z;
    }

    public final /* bridge */ /* synthetic */ void setResult(Object obj) {
        super.setResult((Result) obj);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzd) ((zzaqx) zzb).zzrf()).zzO(this.val$enabled);
    }

    /* access modifiers changed from: protected */
    public final Result zzb(Status status) {
        return new zzaqp(this, status);
    }
}
