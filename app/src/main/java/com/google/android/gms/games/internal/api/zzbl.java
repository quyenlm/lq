package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.quest.Quests;
import com.google.android.gms.internal.zzbaz;

final class zzbl extends zzbq {
    private /* synthetic */ boolean zzbaP;
    private /* synthetic */ String[] zzbbi;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbl(zzbh zzbh, GoogleApiClient googleApiClient, boolean z, String[] strArr) {
        super(googleApiClient, (zzbi) null);
        this.zzbaP = z;
        this.zzbbi = strArr;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zzb((zzbaz<Quests.LoadQuestsResult>) this, this.zzbaP, this.zzbbi);
    }
}
