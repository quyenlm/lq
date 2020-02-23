package com.google.android.gms.games.internal.api;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.internal.GamesClientImpl;
import com.google.android.gms.games.snapshot.SnapshotContents;
import com.google.android.gms.games.snapshot.SnapshotMetadataChange;
import com.google.android.gms.games.snapshot.Snapshots;
import com.google.android.gms.internal.zzbaz;

final class zzcg extends zzcn {
    private /* synthetic */ SnapshotMetadataChange zzbbq;
    private /* synthetic */ String zzbbs;
    private /* synthetic */ String zzbbt;
    private /* synthetic */ SnapshotContents zzbbu;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcg(zzcb zzcb, GoogleApiClient googleApiClient, String str, String str2, SnapshotMetadataChange snapshotMetadataChange, SnapshotContents snapshotContents) {
        super(googleApiClient, (zzcc) null);
        this.zzbbs = str;
        this.zzbbt = str2;
        this.zzbbq = snapshotMetadataChange;
        this.zzbbu = snapshotContents;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((GamesClientImpl) zzb).zza((zzbaz<Snapshots.OpenSnapshotResult>) this, this.zzbbs, this.zzbbt, this.zzbbq, this.zzbbu);
    }
}
