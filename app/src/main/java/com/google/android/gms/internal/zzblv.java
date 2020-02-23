package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.DriveContents;

final class zzblv extends zzbkt {
    private final zzbaz<DriveApi.DriveContentsResult> zzaIz;

    public zzblv(zzbaz<DriveApi.DriveContentsResult> zzbaz) {
        this.zzaIz = zzbaz;
    }

    public final void onError(Status status) throws RemoteException {
        this.zzaIz.setResult(new zzblw(status, (DriveContents) null));
    }

    public final void zza(zzboz zzboz) throws RemoteException {
        this.zzaIz.setResult(new zzblw(Status.zzaBm, new zzbmn(zzboz.zzaOg)));
    }
}
