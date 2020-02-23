package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.internal.GamesClientImpl;

final class zzs extends zzv {
    private /* synthetic */ String zzbaT;
    private /* synthetic */ int zzbaU;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzs(zzp zzp, GoogleApiClient googleApiClient, String str, int i) {
        super(googleApiClient, (zzq) null);
        this.zzbaT = str;
        this.zzbaU = i;
    }

    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zzn(this.zzbaT, this.zzbaU);
    }
}
