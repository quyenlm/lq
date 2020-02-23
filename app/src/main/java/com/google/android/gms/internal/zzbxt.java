package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.request.DataTypeCreateRequest;
import com.google.android.gms.fitness.result.DataTypeResult;

final class zzbxt extends zzbuu<DataTypeResult> {
    private /* synthetic */ DataTypeCreateRequest zzaVC;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbxt(zzbxs zzbxs, GoogleApiClient googleApiClient, DataTypeCreateRequest dataTypeCreateRequest) {
        super(googleApiClient);
        this.zzaVC = dataTypeCreateRequest;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbwj) ((zzbut) zzb).zzrf()).zza(new DataTypeCreateRequest(this.zzaVC, (zzbwb) new zzbxw(this, (zzbxt) null)));
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return DataTypeResult.zzC(status);
    }
}
