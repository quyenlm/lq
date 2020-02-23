package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResult;

final class zzbyg extends zzbvb<DataReadResult> {
    private /* synthetic */ DataReadRequest zzaVM;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbyg(zzbya zzbya, GoogleApiClient googleApiClient, DataReadRequest dataReadRequest) {
        super(googleApiClient);
        this.zzaVM = dataReadRequest;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbwn) ((zzbva) zzb).zzrf()).zza(new DataReadRequest(this.zzaVM, (zzbvv) new zzbyj(this, (zzbyb) null)));
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return DataReadResult.zza(status, this.zzaVM.getDataTypes(), this.zzaVM.getDataSources());
    }
}
