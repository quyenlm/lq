package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.zzf;
import com.google.android.gms.fitness.result.DailyTotalResult;

final class zzbyh extends zzbvb<DailyTotalResult> {
    private /* synthetic */ DataType zzaVN;
    private /* synthetic */ boolean zzaVO;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbyh(zzbya zzbya, GoogleApiClient googleApiClient, DataType dataType, boolean z) {
        super(googleApiClient);
        this.zzaVN = dataType;
        this.zzaVO = z;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbwn) ((zzbva) zzb).zzrf()).zza(new zzf(new zzbyi(this), this.zzaVN, this.zzaVO));
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return DailyTotalResult.zza(status, this.zzaVN);
    }
}
