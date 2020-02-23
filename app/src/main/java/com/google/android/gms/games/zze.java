package com.google.android.gms.games;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.internal.GamesClientImpl;

final class zze extends Games.zzd {
    zze(GoogleApiClient googleApiClient) {
        super(googleApiClient, (zzb) null);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zzg(this);
    }
}
