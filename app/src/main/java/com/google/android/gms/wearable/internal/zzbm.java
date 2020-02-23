package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.wearable.DataItemBuffer;

final class zzbm extends zzn<DataItemBuffer> {
    private /* synthetic */ int zzbSx;
    private /* synthetic */ Uri zzbzR;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbm(zzbi zzbi, GoogleApiClient googleApiClient, Uri uri, int i) {
        super(googleApiClient);
        this.zzbzR = uri;
        this.zzbSx = i;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzdn) ((zzfw) zzb).zzrf()).zza((zzdi) new zzfm(this), this.zzbzR, this.zzbSx);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return new DataItemBuffer(DataHolder.zzau(status.getStatusCode()));
    }
}
