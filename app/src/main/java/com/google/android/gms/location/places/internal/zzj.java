package com.google.android.gms.location.places.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.zzd;
import com.google.android.gms.location.places.zzf;

final class zzj extends zzf<zzm> {
    private /* synthetic */ String zzbkk;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzj(zzh zzh, Api api, GoogleApiClient googleApiClient, String str) {
        super(api, googleApiClient);
        this.zzbkk = str;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzm) zzb).zza(new zzd((zzf) this), this.zzbkk);
    }
}
