package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.wearable.ChannelApi;

final class zzag extends zzn<Status> {
    private final String zzakv;
    private ChannelApi.ChannelListener zzbSg;

    zzag(GoogleApiClient googleApiClient, ChannelApi.ChannelListener channelListener, String str) {
        super(googleApiClient);
        this.zzbSg = (ChannelApi.ChannelListener) zzbo.zzu(channelListener);
        this.zzakv = str;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzfw) zzb).zza(this, this.zzbSg, this.zzakv);
        this.zzbSg = null;
    }

    public final /* synthetic */ Result zzb(Status status) {
        this.zzbSg = null;
        return status;
    }
}
