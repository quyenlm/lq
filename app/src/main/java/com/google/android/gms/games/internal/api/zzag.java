package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.android.gms.internal.zzbaz;

final class zzag extends zzan {
    private /* synthetic */ boolean zzbaP;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzag(zzaf zzaf, GoogleApiClient googleApiClient, boolean z) {
        super(googleApiClient, (zzag) null);
        this.zzbaP = z;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zzb((zzbaz<Leaderboards.LeaderboardMetadataResult>) this, this.zzbaP);
    }
}
