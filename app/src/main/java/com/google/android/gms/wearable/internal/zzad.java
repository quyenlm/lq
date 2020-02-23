package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.Channel;
import com.google.android.gms.wearable.ChannelApi;

final class zzad extends zzn<ChannelApi.OpenChannelResult> {
    private /* synthetic */ String zzKS;
    private /* synthetic */ String zzbSe;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzad(zzac zzac, GoogleApiClient googleApiClient, String str, String str2) {
        super(googleApiClient);
        this.zzbSe = str;
        this.zzKS = str2;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzdn) ((zzfw) zzb).zzrf()).zza((zzdi) new zzfq(this), this.zzbSe, this.zzKS);
    }

    public final /* synthetic */ Result zzb(Status status) {
        return new zzaf(status, (Channel) null);
    }
}
