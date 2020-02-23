package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.zzp;

final class zzbmp extends zzbmg {
    private /* synthetic */ zzbmn zzaOj;
    private /* synthetic */ MetadataChangeSet zzaOk;
    private /* synthetic */ zzp zzaOl;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbmp(zzbmn zzbmn, GoogleApiClient googleApiClient, MetadataChangeSet metadataChangeSet, zzp zzp) {
        super(googleApiClient);
        this.zzaOj = zzbmn;
        this.zzaOk = metadataChangeSet;
        this.zzaOl = zzp;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        zzbmh zzbmh = (zzbmh) zzb;
        this.zzaOk.zzsW().setContext(zzbmh.getContext());
        ((zzbom) zzbmh.zzrf()).zza(new zzbkw(this.zzaOj.zzaOg.getDriveId(), this.zzaOk.zzsW(), this.zzaOj.zzaOg.getRequestId(), this.zzaOj.zzaOg.zzsK(), this.zzaOl), (zzboo) new zzbqq(this));
    }
}
