package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbaz;

final class zzap extends zzn<Status> {
    private /* synthetic */ zzak zzbSk;
    private /* synthetic */ boolean zzbSl;
    private /* synthetic */ Uri zzbzR;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzap(zzak zzak, GoogleApiClient googleApiClient, Uri uri, boolean z) {
        super(googleApiClient);
        this.zzbSk = zzak;
        this.zzbzR = uri;
        this.zzbSl = z;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzfw) zzb).zza((zzbaz<Status>) this, this.zzbSk.zzakv, this.zzbzR, this.zzbSl);
    }

    public final /* synthetic */ Result zzb(Status status) {
        return status;
    }
}
