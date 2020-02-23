package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;

public final class zzbms extends zzbnn implements DriveFile {
    public zzbms(DriveId driveId) {
        super(driveId);
    }

    public final PendingResult<DriveApi.DriveContentsResult> open(GoogleApiClient googleApiClient, int i, DriveFile.DownloadProgressListener downloadProgressListener) {
        if (i == 268435456 || i == 536870912 || i == 805306368) {
            return googleApiClient.zzd(new zzbmt(this, googleApiClient, i, downloadProgressListener == null ? null : new zzbmu(googleApiClient.zzp(downloadProgressListener))));
        }
        throw new IllegalArgumentException("Invalid mode provided.");
    }
}
