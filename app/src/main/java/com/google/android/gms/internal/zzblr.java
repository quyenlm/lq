package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.DriveId;

final class zzblr extends zzbma {
    private /* synthetic */ String zzaNM;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzblr(zzblo zzblo, GoogleApiClient googleApiClient, String str) {
        super(googleApiClient);
        this.zzaNM = str;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbom) ((zzbmh) zzb).zzrf()).zza(new zzboi(DriveId.zzcO(this.zzaNM), false), (zzboo) new zzbly(this));
    }
}
