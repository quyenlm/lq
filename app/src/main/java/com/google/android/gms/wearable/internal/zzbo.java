package com.google.android.gms.wearable.internal;

import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbaz;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi;

final class zzbo extends zzn<DataApi.GetFdForAssetResult> {
    private /* synthetic */ Asset zzbSy;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbo(zzbi zzbi, GoogleApiClient googleApiClient, Asset asset) {
        super(googleApiClient);
        this.zzbSy = asset;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzfw) zzb).zza((zzbaz<DataApi.GetFdForAssetResult>) this, this.zzbSy);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return new zzbu(status, (ParcelFileDescriptor) null);
    }
}
