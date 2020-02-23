package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.DriveId;

final class zzbml extends zzbmg {
    private /* synthetic */ DriveId zzaOd;
    private /* synthetic */ int zzaOe = 1;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbml(zzbmh zzbmh, GoogleApiClient googleApiClient, DriveId driveId, int i) {
        super(googleApiClient);
        this.zzaOd = driveId;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbom) ((zzbmh) zzb).zzrf()).zza(new zzbqk(this.zzaOd, this.zzaOe), (zzboq) null, (String) null, (zzboo) new zzbqq(this));
    }
}
