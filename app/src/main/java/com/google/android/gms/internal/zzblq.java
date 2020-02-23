package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.DriveFile;

final class zzblq extends zzblx {
    private /* synthetic */ int zzaNL = DriveFile.MODE_WRITE_ONLY;

    zzblq(zzblo zzblo, GoogleApiClient googleApiClient, int i) {
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbom) ((zzbmh) zzb).zzrf()).zza(new zzbla(this.zzaNL), (zzboo) new zzblv(this));
    }
}
