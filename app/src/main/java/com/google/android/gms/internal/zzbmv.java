package com.google.android.gms.internal;

import com.google.android.gms.drive.DriveFile;

final class zzbmv implements zzbdz<DriveFile.DownloadProgressListener> {
    private /* synthetic */ long zzaOp;
    private /* synthetic */ long zzaOq;

    zzbmv(zzbmu zzbmu, long j, long j2) {
        this.zzaOp = j;
        this.zzaOq = j2;
    }

    public final void zzpT() {
    }

    public final /* synthetic */ void zzq(Object obj) {
        ((DriveFile.DownloadProgressListener) obj).onProgress(this.zzaOp, this.zzaOq);
    }
}
