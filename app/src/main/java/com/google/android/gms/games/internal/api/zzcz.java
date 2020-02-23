package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.multiplayer.ParticipantResult;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMultiplayer;
import com.google.android.gms.internal.zzbaz;

final class zzcz extends zzdp {
    private /* synthetic */ ParticipantResult[] zzbbA;
    private /* synthetic */ String zzbbw;
    private /* synthetic */ byte[] zzbby;
    private /* synthetic */ String zzbbz;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcz(zzcu zzcu, GoogleApiClient googleApiClient, String str, byte[] bArr, String str2, ParticipantResult[] participantResultArr) {
        super(googleApiClient, (zzcv) null);
        this.zzbbw = str;
        this.zzbby = bArr;
        this.zzbbz = str2;
        this.zzbbA = participantResultArr;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zza((zzbaz<TurnBasedMultiplayer.UpdateMatchResult>) this, this.zzbbw, this.zzbby, this.zzbbz, this.zzbbA);
    }
}
