package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

final class zzark extends zzarn<Status> {
    private /* synthetic */ Credential zzalA;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzark(zzarg zzarg, GoogleApiClient googleApiClient, Credential credential) {
        super(googleApiClient);
        this.zzalA = credential;
    }

    /* access modifiers changed from: protected */
    public final void zza(Context context, zzart zzart) throws RemoteException {
        zzart.zza((zzarr) new zzarm(this), new zzarp(this.zzalA));
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return status;
    }
}
