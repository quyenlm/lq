package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.quest.Quests;
import com.google.android.gms.internal.zzbaz;

final class zzbk extends zzbq {
    private /* synthetic */ boolean zzbaP;
    private /* synthetic */ int zzbaV;
    private /* synthetic */ int[] zzbbh;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbk(zzbh zzbh, GoogleApiClient googleApiClient, int[] iArr, int i, boolean z) {
        super(googleApiClient, (zzbi) null);
        this.zzbbh = iArr;
        this.zzbaV = i;
        this.zzbaP = z;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zza((zzbaz<Quests.LoadQuestsResult>) this, this.zzbbh, this.zzbaV, this.zzbaP);
    }
}
