package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.zzai;
import com.google.android.gms.fitness.result.ListSubscriptionsResult;

final class zzbyn extends zzbvh<ListSubscriptionsResult> {
    private /* synthetic */ DataType zzaVN;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbyn(zzbyl zzbyl, GoogleApiClient googleApiClient, DataType dataType) {
        super(googleApiClient);
        this.zzaVN = dataType;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbwr) ((zzbvg) zzb).zzrf()).zza(new zzai(this.zzaVN, new zzbyr(this, (zzbym) null)));
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return ListSubscriptionsResult.zzD(status);
    }
}
