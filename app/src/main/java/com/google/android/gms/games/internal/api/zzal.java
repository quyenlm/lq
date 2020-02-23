package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.leaderboard.LeaderboardScoreBuffer;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.android.gms.internal.zzbaz;

final class zzal extends zzar {
    private /* synthetic */ int zzbaZ;
    private /* synthetic */ LeaderboardScoreBuffer zzbba;
    private /* synthetic */ int zzbbb;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzal(zzaf zzaf, GoogleApiClient googleApiClient, LeaderboardScoreBuffer leaderboardScoreBuffer, int i, int i2) {
        super(googleApiClient, (zzag) null);
        this.zzbba = leaderboardScoreBuffer;
        this.zzbaZ = i;
        this.zzbbb = i2;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zza((zzbaz<Leaderboards.LoadScoresResult>) this, this.zzbba, this.zzbaZ, this.zzbbb);
    }
}
