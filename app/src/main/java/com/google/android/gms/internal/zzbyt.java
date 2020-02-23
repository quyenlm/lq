package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.result.DataSourcesResult;
import java.util.Collections;

final class zzbyt extends zzbvl<DataSourcesResult> {
    private /* synthetic */ DataSourcesRequest zzaVU;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbyt(zzbys zzbys, GoogleApiClient googleApiClient, DataSourcesRequest dataSourcesRequest) {
        super(googleApiClient);
        this.zzaVU = dataSourcesRequest;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbwt) ((zzbvk) zzb).zzrf()).zza(new DataSourcesRequest(this.zzaVU, (zzbvy) new zzbuo(this)));
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return new DataSourcesResult(Collections.emptyList(), status);
    }
}
