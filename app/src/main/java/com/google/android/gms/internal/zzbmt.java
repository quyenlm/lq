package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.zzap;
import com.google.android.gms.drive.DriveFile;

final class zzbmt extends zzblx {
    private /* synthetic */ int zzaNL;
    private /* synthetic */ DriveFile.DownloadProgressListener zzaOm;
    private /* synthetic */ zzbms zzaOn;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbmt(zzbms zzbms, GoogleApiClient googleApiClient, int i, DriveFile.DownloadProgressListener downloadProgressListener) {
        super(googleApiClient);
        this.zzaOn = zzbms;
        this.zzaNL = i;
        this.zzaOm = downloadProgressListener;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        zza(zzap.zzH(((zzbom) ((zzbmh) zzb).zzrf()).zza(new zzbqb(this.zzaOn.getDriveId(), this.zzaNL, 0), (zzboo) new zzbqd(this, this.zzaOm)).zzaOG));
    }
}
