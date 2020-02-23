package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;
import com.google.android.gms.internal.zzbaz;

final class zzdc extends zzdj {
    private /* synthetic */ String zzbbw;
    private /* synthetic */ String zzbbz;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzdc(zzcu zzcu, GoogleApiClient googleApiClient, String str, String str2) {
        super(googleApiClient, (zzcv) null);
        this.zzbbw = str;
        this.zzbbz = str2;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zza((zzbaz<TurnBasedMultiplayer.LeaveMatchResult>) this, this.zzbbw, this.zzbbz);
    }
}
