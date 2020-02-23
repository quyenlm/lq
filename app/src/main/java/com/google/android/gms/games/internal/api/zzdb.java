package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;
import com.google.android.gms.internal.zzbaz;

final class zzdb extends zzdj {
    private /* synthetic */ String zzbbw;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzdb(zzcu zzcu, GoogleApiClient googleApiClient, String str) {
        super(googleApiClient, (zzcv) null);
        this.zzbbw = str;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zze((zzbaz<TurnBasedMultiplayer.LeaveMatchResult>) this, this.zzbbw);
    }
}
