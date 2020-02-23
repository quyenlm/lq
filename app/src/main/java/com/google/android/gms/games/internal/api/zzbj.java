package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.quest.Quests;
import com.google.android.gms.internal.zzbaz;

final class zzbj extends zzbo {
    private /* synthetic */ String zzbbf;
    private /* synthetic */ String zzbbg;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbj(zzbh zzbh, GoogleApiClient googleApiClient, String str, String str2) {
        super(googleApiClient, (zzbi) null);
        this.zzbbf = str;
        this.zzbbg = str2;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zzb((zzbaz<Quests.ClaimMilestoneResult>) this, this.zzbbf, this.zzbbg);
    }
}
