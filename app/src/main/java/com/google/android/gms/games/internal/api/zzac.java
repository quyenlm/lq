package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.multiplayer.Invitations;
import com.google.android.gms.internal.zzbaz;

final class zzac extends zzad {
    private /* synthetic */ int zzbaV;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzac(zzab zzab, GoogleApiClient googleApiClient, int i) {
        super(googleApiClient, (zzac) null);
        this.zzbaV = i;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zza((zzbaz<Invitations.LoadInvitationsResult>) this, this.zzbaV);
    }
}
