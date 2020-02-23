package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.request.zzbk;

final class zzbxp extends zzbus {
    private /* synthetic */ String zzaVA;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbxp(zzbxk zzbxk, GoogleApiClient googleApiClient, String str) {
        super(googleApiClient);
        this.zzaVA = str;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbwh) ((zzbup) zzb).zzrf()).zza(new zzbk(this.zzaVA, new zzbzi(this)));
    }
}
