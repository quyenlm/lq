package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

final class zzam extends zzn<Status> {
    private /* synthetic */ int zzKc;
    private /* synthetic */ zzak zzbSk;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzam(zzak zzak, GoogleApiClient googleApiClient, int i) {
        super(googleApiClient);
        this.zzbSk = zzak;
        this.zzKc = i;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzdn) ((zzfw) zzb).zzrf()).zzb((zzdi) new zzfe(this), this.zzbSk.zzakv, this.zzKc);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return status;
    }
}
