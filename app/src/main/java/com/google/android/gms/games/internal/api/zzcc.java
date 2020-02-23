package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.snapshot.Snapshots;
import com.google.android.gms.internal.zzbaz;

final class zzcc extends zzcl {
    private /* synthetic */ boolean zzbaP;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcc(zzcb zzcb, GoogleApiClient googleApiClient, boolean z) {
        super(googleApiClient, (zzcc) null);
        this.zzbaP = z;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zzf((zzbaz<Snapshots.LoadSnapshotsResult>) this, this.zzbaP);
    }
}
