package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.DriveApi;
import com.google.android.gms.drive.MetadataBuffer;

final class zzbnv extends zzbkt {
    private final zzbaz<DriveApi.MetadataBufferResult> zzaIz;

    public zzbnv(zzbaz<DriveApi.MetadataBufferResult> zzbaz) {
        this.zzaIz = zzbaz;
    }

    public final void onError(Status status) throws RemoteException {
        this.zzaIz.setResult(new zzbmb(status, (MetadataBuffer) null, false));
    }

    public final void zza(zzbpn zzbpn) throws RemoteException {
        this.zzaIz.setResult(new zzbmb(Status.zzaBm, new MetadataBuffer(zzbpn.zzaPj), false));
    }
}
