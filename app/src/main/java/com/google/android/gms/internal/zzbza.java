package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.request.zzay;

final class zzbza extends zzbvr {
    private /* synthetic */ Session zzaWc;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbza(zzbyz zzbyz, GoogleApiClient googleApiClient, Session session) {
        super(googleApiClient);
        this.zzaWc = session;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbwv) ((zzbvo) zzb).zzrf()).zza(new zzay(this.zzaWc, new zzbzi(this)));
    }
}
