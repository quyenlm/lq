package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.zzbm;

final class zzbyp extends zzbvj {
    private /* synthetic */ DataType zzaVN;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbyp(zzbyl zzbyl, GoogleApiClient googleApiClient, DataType dataType) {
        super(googleApiClient);
        this.zzaVN = dataType;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbwr) ((zzbvg) zzb).zzrf()).zza(new zzbm(this.zzaVN, (DataSource) null, new zzbzi(this)));
    }
}
