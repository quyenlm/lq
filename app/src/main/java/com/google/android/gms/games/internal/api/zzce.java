package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.snapshot.Snapshot;
import com.google.android.gms.games.snapshot.SnapshotMetadataChange;
import com.google.android.gms.games.snapshot.Snapshots;
import com.google.android.gms.internal.zzbaz;

final class zzce extends zzch {
    private /* synthetic */ Snapshot zzbbp;
    private /* synthetic */ SnapshotMetadataChange zzbbq;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzce(zzcb zzcb, GoogleApiClient googleApiClient, Snapshot snapshot, SnapshotMetadataChange snapshotMetadataChange) {
        super(googleApiClient, (zzcc) null);
        this.zzbbp = snapshot;
        this.zzbbq = snapshotMetadataChange;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zza((zzbaz<Snapshots.CommitSnapshotResult>) this, this.zzbbp, this.zzbbq);
    }
}
