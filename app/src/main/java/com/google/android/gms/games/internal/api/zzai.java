package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.android.gms.internal.zzbaz;

final class zzai extends zzap {
    private /* synthetic */ String zzbaW;
    private /* synthetic */ int zzbaX;
    private /* synthetic */ int zzbaY;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzai(zzaf zzaf, GoogleApiClient googleApiClient, String str, int i, int i2) {
        super(googleApiClient, (zzag) null);
        this.zzbaW = str;
        this.zzbaX = i;
        this.zzbaY = i2;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zza((zzbaz<Leaderboards.LoadPlayerScoreResult>) this, (String) null, this.zzbaW, this.zzbaX, this.zzbaY);
    }
}
