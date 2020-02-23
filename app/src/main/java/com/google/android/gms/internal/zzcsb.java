package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.zzcsa;

final class zzcsb extends zzcsa.zzb {
    private /* synthetic */ byte[] zzbBO;
    private /* synthetic */ String zzbBP;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcsb(GoogleApiClient googleApiClient, byte[] bArr, String str) {
        super(googleApiClient);
        this.zzbBO = bArr;
        this.zzbBP = str;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzcsn) zzb).zzb(this.zzbBW, this.zzbBO, this.zzbBP);
    }
}
