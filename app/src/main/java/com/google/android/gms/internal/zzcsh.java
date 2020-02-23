package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.zzcsa;

final class zzcsh extends zzcsa.zze {
    private /* synthetic */ String zzbBU;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcsh(zzcsa zzcsa, GoogleApiClient googleApiClient, String str) {
        super(googleApiClient);
        this.zzbBU = str;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzcry) ((zzcsn) zzb).zzrf()).zza(this.zzbBW, this.zzbBU);
    }
}
