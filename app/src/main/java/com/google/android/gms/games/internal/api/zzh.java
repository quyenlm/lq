package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.achievement.Achievements;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.internal.zzbaz;

final class zzh extends zzm {
    private /* synthetic */ String val$id;
    private /* synthetic */ int zzbaQ;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzh(zza zza, String str, GoogleApiClient googleApiClient, String str2, int i) {
        super(str, googleApiClient);
        this.val$id = str2;
        this.zzbaQ = i;
    }

    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zza((zzbaz<Achievements.UpdateAchievementResult>) this, this.val$id, this.zzbaQ);
    }
}
