package com.google.android.gms.location.places.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.zzm;
import java.util.Arrays;
import java.util.List;

final class zzk extends zzm.zzc<zzm> {
    private /* synthetic */ String[] zzbkl;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzk(zzh zzh, Api api, GoogleApiClient googleApiClient, String[] strArr) {
        super(api, googleApiClient);
        this.zzbkl = strArr;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzm) zzb).zza(new zzm((zzm.zzc) this), (List<String>) Arrays.asList(this.zzbkl));
    }
}
