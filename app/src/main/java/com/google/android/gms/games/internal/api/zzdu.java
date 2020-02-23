package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.video.Videos;
import com.google.android.gms.internal.zzbaz;

final class zzdu extends zzdv {
    private /* synthetic */ int zzbbE;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzdu(zzdr zzdr, GoogleApiClient googleApiClient, int i) {
        super(googleApiClient, (zzds) null);
        this.zzbbE = i;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zzb((zzbaz<Videos.CaptureAvailableResult>) this, this.zzbbE);
    }
}
