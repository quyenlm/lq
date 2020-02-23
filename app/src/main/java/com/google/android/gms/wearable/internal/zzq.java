package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.CapabilityInfo;
import java.util.Map;

final class zzq extends zzn<CapabilityApi.GetAllCapabilitiesResult> {
    private /* synthetic */ int zzbRW;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzq(zzo zzo, GoogleApiClient googleApiClient, int i) {
        super(googleApiClient);
        this.zzbRW = i;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzdn) ((zzfw) zzb).zzrf()).zza((zzdi) new zzfg(this), this.zzbRW);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return new zzx(status, (Map<String, CapabilityInfo>) null);
    }
}
