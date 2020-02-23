package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;
import com.google.android.gms.internal.zzbaz;

final class zzde extends zzdn {
    private /* synthetic */ int zzbbB;
    private /* synthetic */ int[] zzbbC;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzde(zzcu zzcu, GoogleApiClient googleApiClient, int i, int[] iArr) {
        super(googleApiClient, (zzcv) null);
        this.zzbbB = i;
        this.zzbbC = iArr;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zza((zzbaz<TurnBasedMultiplayer.LoadMatchesResult>) this, this.zzbbB, this.zzbbC);
    }
}
