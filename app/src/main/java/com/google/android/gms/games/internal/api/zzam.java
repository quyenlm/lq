package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.android.gms.internal.zzbaz;

final class zzam extends zzat {
    private /* synthetic */ String zzbaW;
    private /* synthetic */ long zzbbc;
    private /* synthetic */ String zzbbd;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzam(zzaf zzaf, GoogleApiClient googleApiClient, String str, long j, String str2) {
        super(googleApiClient);
        this.zzbaW = str;
        this.zzbbc = j;
        this.zzbbd = str2;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zza((zzbaz<Leaderboards.SubmitScoreResult>) this, this.zzbaW, this.zzbbc, this.zzbbd);
    }
}
