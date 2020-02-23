package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.request.Requests;
import com.google.android.gms.internal.zzbaz;

final class zzbu extends zzbz {
    private /* synthetic */ String[] zzbbj;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbu(zzbt zzbt, GoogleApiClient googleApiClient, String[] strArr) {
        super(googleApiClient, (zzbu) null);
        this.zzbbj = strArr;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zza((zzbaz<Requests.UpdateRequestsResult>) this, this.zzbbj);
    }
}
