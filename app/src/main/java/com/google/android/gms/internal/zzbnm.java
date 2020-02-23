package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DrivePreferencesApi;
import com.google.android.gms.drive.FileUploadPreferences;

abstract class zzbnm extends zzbmf<DrivePreferencesApi.FileUploadPreferencesResult> {
    private /* synthetic */ zzbnh zzaOz;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public zzbnm(zzbnh zzbnh, GoogleApiClient googleApiClient) {
        super(googleApiClient);
        this.zzaOz = zzbnh;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return new zzbnl(this.zzaOz, status, (FileUploadPreferences) null, (zzbni) null);
    }
}
