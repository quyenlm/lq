package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.stats.Stats;
import com.google.android.gms.internal.zzbaz;

final class zzcr extends zzcs {
    private /* synthetic */ boolean zzbaP;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcr(zzcq zzcq, GoogleApiClient googleApiClient, boolean z) {
        super(googleApiClient, (zzcr) null);
        this.zzbaP = z;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zze((zzbaz<Stats.LoadPlayerStatsResult>) this, this.zzbaP);
    }
}
