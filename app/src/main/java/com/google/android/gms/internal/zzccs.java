package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.zzaa;

final class zzccs extends zzcct {
    private /* synthetic */ zzaa zzbiL;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzccs(zzccq zzccq, GoogleApiClient googleApiClient, zzaa zzaa) {
        super(googleApiClient);
        this.zzbiL = zzaa;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzcdj) zzb).zza(this.zzbiL, (zzbaz<Status>) this);
    }
}
