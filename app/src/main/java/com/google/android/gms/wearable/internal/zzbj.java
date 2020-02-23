package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbaz;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.PutDataRequest;

final class zzbj extends zzn<DataApi.DataItemResult> {
    private /* synthetic */ PutDataRequest zzbSw;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbj(zzbi zzbi, GoogleApiClient googleApiClient, PutDataRequest putDataRequest) {
        super(googleApiClient);
        this.zzbSw = putDataRequest;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzfw) zzb).zza((zzbaz<DataApi.DataItemResult>) this, this.zzbSw);
    }

    public final /* synthetic */ Result zzb(Status status) {
        return new zzbs(status, (DataItem) null);
    }
}
