package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.zzbm;

final class zzbyq extends zzbvj {
    private /* synthetic */ DataSource zzaVT;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbyq(zzbyl zzbyl, GoogleApiClient googleApiClient, DataSource dataSource) {
        super(googleApiClient);
        this.zzaVT = dataSource;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbwr) ((zzbvg) zzb).zzrf()).zza(new zzbm((DataType) null, this.zzaVT, new zzbzi(this)));
    }
}
