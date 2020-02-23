package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.snapshot.Snapshots;
import com.google.android.gms.internal.zzbaz;

final class zzcd extends zzcn {
    private /* synthetic */ String zzbbm;
    private /* synthetic */ boolean zzbbn;
    private /* synthetic */ int zzbbo;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcd(zzcb zzcb, GoogleApiClient googleApiClient, String str, boolean z, int i) {
        super(googleApiClient, (zzcc) null);
        this.zzbbm = str;
        this.zzbbn = z;
        this.zzbbo = i;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zza((zzbaz<Snapshots.OpenSnapshotResult>) this, this.zzbbm, this.zzbbn, this.zzbbo);
    }
}
