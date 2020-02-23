package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.DriveFile;

final class zzbmo extends zzblx {
    private /* synthetic */ zzbmn zzaOj;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbmo(zzbmn zzbmn, GoogleApiClient googleApiClient) {
        super(googleApiClient);
        this.zzaOj = zzbmn;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbom) ((zzbmh) zzb).zzrf()).zza(new zzbqb(this.zzaOj.getDriveId(), DriveFile.MODE_WRITE_ONLY, this.zzaOj.zzaOg.getRequestId()), (zzboo) new zzbqd(this, (DriveFile.DownloadProgressListener) null));
    }
}
