package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.request.StartBleScanRequest;

final class zzbxl extends zzbus {
    private /* synthetic */ StartBleScanRequest zzaVy;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbxl(zzbxk zzbxk, GoogleApiClient googleApiClient, StartBleScanRequest startBleScanRequest) {
        super(googleApiClient);
        this.zzaVy = startBleScanRequest;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbwh) ((zzbup) zzb).zzrf()).zza(new StartBleScanRequest(this.zzaVy, (zzbxg) new zzbzi(this)));
    }
}
