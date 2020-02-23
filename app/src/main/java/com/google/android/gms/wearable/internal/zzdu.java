package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbaz;
import com.google.android.gms.wearable.MessageApi;

final class zzdu extends zzn<Status> {
    private /* synthetic */ MessageApi.MessageListener zzbSU;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzdu(zzds zzds, GoogleApiClient googleApiClient, MessageApi.MessageListener messageListener) {
        super(googleApiClient);
        this.zzbSU = messageListener;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzfw) zzb).zza((zzbaz<Status>) this, this.zzbSU);
    }

    public final /* synthetic */ Result zzb(Status status) {
        return status;
    }
}
