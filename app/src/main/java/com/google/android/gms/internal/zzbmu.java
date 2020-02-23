package com.google.android.gms.internal;

import com.google.android.gms.drive.DriveFile;

final class zzbmu implements DriveFile.DownloadProgressListener {
    private final zzbdw<DriveFile.DownloadProgressListener> zzaOo;

    public zzbmu(zzbdw<DriveFile.DownloadProgressListener> zzbdw) {
        this.zzaOo = zzbdw;
    }

    public final void onProgress(long j, long j2) {
        this.zzaOo.zza(new zzbmv(this, j, j2));
    }
}
