package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;

abstract class zzbnd extends zzbmf<DriveFolder.DriveFileResult> {
    zzbnd(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    public final /* synthetic */ Result zzb(Status status) {
        return new zzbnc(status, (DriveFile) null);
    }
}
