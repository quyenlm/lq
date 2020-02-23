package com.google.android.gms.games;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.internal.zzbaz;

final class zzd extends Games.zzc {
    private /* synthetic */ String zzaYt;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzd(GoogleApiClient googleApiClient, String str) {
        super(googleApiClient, (zzb) null);
        this.zzaYt = str;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zzb(this.zzaYt, (zzbaz<Games.GetServerAuthCodeResult>) this);
    }
}
