package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.request.BleScanCallback;
import com.google.android.gms.fitness.request.zzbg;

final class zzbxm extends zzbus {
    private /* synthetic */ BleScanCallback zzaVz;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbxm(zzbxk zzbxk, GoogleApiClient googleApiClient, BleScanCallback bleScanCallback) {
        super(googleApiClient);
        this.zzaVz = bleScanCallback;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbwh) ((zzbup) zzb).zzrf()).zza(new zzbg(this.zzaVz, (zzbxg) new zzbzi(this)));
    }
}
