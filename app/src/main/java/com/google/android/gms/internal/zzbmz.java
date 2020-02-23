package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.MetadataChangeSet;

final class zzbmz extends zzbnf {
    private /* synthetic */ MetadataChangeSet zzaOr;
    private /* synthetic */ zzbmx zzaOv;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbmz(zzbmx zzbmx, GoogleApiClient googleApiClient, MetadataChangeSet metadataChangeSet) {
        super(googleApiClient);
        this.zzaOv = zzbmx;
        this.zzaOr = metadataChangeSet;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        zzbmh zzbmh = (zzbmh) zzb;
        this.zzaOr.zzsW().setContext(zzbmh.getContext());
        ((zzbom) zzbmh.zzrf()).zza(new zzblh(this.zzaOv.getDriveId(), this.zzaOr.zzsW()), (zzboo) new zzbnb(this));
    }
}
