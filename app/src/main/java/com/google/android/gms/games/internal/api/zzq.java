package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.event.Events;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.internal.zzbaz;

final class zzq extends zzt {
    private /* synthetic */ boolean zzbaP;
    private /* synthetic */ String[] zzbaS;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzq(zzp zzp, GoogleApiClient googleApiClient, boolean z, String[] strArr) {
        super(googleApiClient, (zzq) null);
        this.zzbaP = z;
        this.zzbaS = strArr;
    }

    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zza((zzbaz<Events.LoadEventsResult>) this, this.zzbaP, this.zzbaS);
    }
}
