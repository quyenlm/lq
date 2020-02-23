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
import com.google.android.gms.wearable.DataItemAsset;

final class zzbp extends zzn<DataApi.GetFdForAssetResult> {
    private /* synthetic */ DataItemAsset zzbSz;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbp(zzbi zzbi, GoogleApiClient googleApiClient, DataItemAsset dataItemAsset) {
        super(googleApiClient);
        this.zzbSz = dataItemAsset;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzfw) zzb).zza((zzbaz<DataApi.GetFdForAssetResult>) this, Asset.createFromRef(this.zzbSz.getId()));
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return new zzbu(status, (ParcelFileDescriptor) null);
    }
}
