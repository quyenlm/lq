package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.CapabilityInfo;

final class zzp extends zzn<CapabilityApi.GetCapabilityResult> {
    private /* synthetic */ String zzbRV;
    private /* synthetic */ int zzbRW;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzp(zzo zzo, GoogleApiClient googleApiClient, String str, int i) {
        super(googleApiClient);
        this.zzbRV = str;
        this.zzbRW = i;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzdn) ((zzfw) zzb).zzrf()).zza((zzdi) new zzfh(this), this.zzbRV, this.zzbRW);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return new zzy(status, (CapabilityInfo) null);
    }
}
