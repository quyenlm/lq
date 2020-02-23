package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.android.gms.internal.zzbaz;

final class zzah extends zzan {
    private /* synthetic */ boolean zzbaP;
    private /* synthetic */ String zzbaW;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzah(zzaf zzaf, GoogleApiClient googleApiClient, String str, boolean z) {
        super(googleApiClient, (zzag) null);
        this.zzbaW = str;
        this.zzbaP = z;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zzb((zzbaz<Leaderboards.LeaderboardMetadataResult>) this, this.zzbaW, this.zzbaP);
    }
}
