package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;

abstract class zzblx extends zzbmf<DriveApi.DriveContentsResult> {
    zzblx(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    public final /* synthetic */ Result zzb(Status status) {
        return new zzblw(status, (DriveContents) null);
    }
}
