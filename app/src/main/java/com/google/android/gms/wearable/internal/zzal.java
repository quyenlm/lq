package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

final class zzal extends zzn<Status> {
    private /* synthetic */ zzak zzbSk;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzal(zzak zzak, GoogleApiClient googleApiClient) {
        super(googleApiClient);
        this.zzbSk = zzak;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzdn) ((zzfw) zzb).zzrf()).zzc(new zzfd(this), this.zzbSk.zzakv);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return status;
    }
}
