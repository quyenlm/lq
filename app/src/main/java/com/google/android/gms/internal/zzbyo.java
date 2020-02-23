package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.data.Subscription;
import com.google.android.gms.fitness.request.zzbi;

final class zzbyo extends zzbvj {
    private /* synthetic */ Subscription zzaVS;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbyo(zzbyl zzbyl, GoogleApiClient googleApiClient, Subscription subscription) {
        super(googleApiClient);
        this.zzaVS = subscription;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbwr) ((zzbvg) zzb).zzrf()).zza(new zzbi(this.zzaVS, false, new zzbzi(this)));
    }
}
