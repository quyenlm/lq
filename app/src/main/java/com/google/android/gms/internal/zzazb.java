package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.cast.CastRemoteDisplay;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

class zzazb extends zzbay<CastRemoteDisplay.CastRemoteDisplaySessionResult, zzazf> {
    final /* synthetic */ zzayw zzayZ;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public zzazb(zzayw zzayw, GoogleApiClient googleApiClient) {
        super((Api<?>) zzayw.zzayW, googleApiClient);
        this.zzayZ = zzayw;
    }

    public final /* bridge */ /* synthetic */ void setResult(Object obj) {
        super.setResult((CastRemoteDisplay.CastRemoteDisplaySessionResult) obj);
    }

    public void zza(zzazf zzazf) throws RemoteException {
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return new zzaze(status);
    }
}
