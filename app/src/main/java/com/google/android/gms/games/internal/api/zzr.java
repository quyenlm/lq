package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.event.Events;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.internal.zzbaz;

final class zzr extends zzt {
    private /* synthetic */ boolean zzbaP;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzr(zzp zzp, GoogleApiClient googleApiClient, boolean z) {
        super(googleApiClient, (zzq) null);
        this.zzbaP = z;
    }

    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zzd((zzbaz<Events.LoadEventsResult>) this, this.zzbaP);
    }
}
