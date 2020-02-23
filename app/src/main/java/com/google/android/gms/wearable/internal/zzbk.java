package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataItem;

final class zzbk extends zzn<DataApi.DataItemResult> {
    private /* synthetic */ Uri zzbzR;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbk(zzbi zzbi, GoogleApiClient googleApiClient, Uri uri) {
        super(googleApiClient);
        this.zzbzR = uri;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzdn) ((zzfw) zzb).zzrf()).zza((zzdi) new zzfl(this), this.zzbzR);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return new zzbs(status, (DataItem) null);
    }
}
