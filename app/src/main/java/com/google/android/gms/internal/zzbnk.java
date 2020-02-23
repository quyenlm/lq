package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DrivePreferencesApi;
import com.google.android.gms.drive.FileUploadPreferences;

final class zzbnk extends zzbkt {
    private final zzbaz<DrivePreferencesApi.FileUploadPreferencesResult> zzaIz;
    private /* synthetic */ zzbnh zzaOz;

    private zzbnk(zzbnh zzbnh, zzbaz<DrivePreferencesApi.FileUploadPreferencesResult> zzbaz) {
        this.zzaOz = zzbnh;
        this.zzaIz = zzbaz;
    }

    /* synthetic */ zzbnk(zzbnh zzbnh, zzbaz zzbaz, zzbni zzbni) {
        this(zzbnh, zzbaz);
    }

    public final void onError(Status status) throws RemoteException {
        this.zzaIz.setResult(new zzbnl(this.zzaOz, status, (FileUploadPreferences) null, (zzbni) null));
    }

    public final void zza(zzbpb zzbpb) throws RemoteException {
        this.zzaIz.setResult(new zzbnl(this.zzaOz, Status.zzaBm, zzbpb.zzaOW, (zzbni) null));
    }
}
