package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.snapshot.SnapshotMetadata;

final class zzcf extends zzcj {
    private /* synthetic */ SnapshotMetadata zzbbr;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcf(zzcb zzcb, GoogleApiClient googleApiClient, SnapshotMetadata snapshotMetadata) {
        super(googleApiClient, (zzcc) null);
        this.zzbbr = snapshotMetadata;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zzi(this, this.zzbbr.getSnapshotId());
    }
}
