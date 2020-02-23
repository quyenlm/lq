package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbaz;

final class zzaq extends zzn<Status> {
    private /* synthetic */ zzak zzbSk;
    private /* synthetic */ long zzbSm;
    private /* synthetic */ long zzbSn;
    private /* synthetic */ Uri zzbzR;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzaq(zzak zzak, GoogleApiClient googleApiClient, Uri uri, long j, long j2) {
        super(googleApiClient);
        this.zzbSk = zzak;
        this.zzbzR = uri;
        this.zzbSm = j;
        this.zzbSn = j2;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzfw) zzb).zza((zzbaz<Status>) this, this.zzbSk.zzakv, this.zzbzR, this.zzbSm, this.zzbSn);
    }

    public final /* synthetic */ Result zzb(Status status) {
        return status;
    }
}
