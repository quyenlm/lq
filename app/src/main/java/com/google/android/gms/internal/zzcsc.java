package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.zzcsa;
import java.util.List;

final class zzcsc extends zzcsa.zzf {
    private /* synthetic */ String zzbBP;
    private /* synthetic */ List zzbBQ;
    private /* synthetic */ String zzbBR;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcsc(zzcsa zzcsa, GoogleApiClient googleApiClient, List list, String str, String str2) {
        super(googleApiClient);
        this.zzbBQ = list;
        this.zzbBR = str;
        this.zzbBP = str2;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzcsn) zzb).zza(this.zzbBW, this.zzbBQ, 2, this.zzbBR, this.zzbBP);
    }
}
