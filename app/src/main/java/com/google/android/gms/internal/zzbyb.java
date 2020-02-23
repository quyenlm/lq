package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.request.zzj;

final class zzbyb extends zzbvd {
    private /* synthetic */ DataSet zzaVG;
    private /* synthetic */ boolean zzaVH = false;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbyb(zzbya zzbya, GoogleApiClient googleApiClient, DataSet dataSet, boolean z) {
        super(googleApiClient);
        this.zzaVG = dataSet;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbwn) ((zzbva) zzb).zzrf()).zza(new zzj(this.zzaVG, new zzbzi(this), this.zzaVH));
    }
}
