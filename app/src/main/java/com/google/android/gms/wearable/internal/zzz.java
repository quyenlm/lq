package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbaz;
import com.google.android.gms.wearable.CapabilityApi;

final class zzz extends zzn<Status> {
    private CapabilityApi.CapabilityListener zzbRY;

    private zzz(GoogleApiClient googleApiClient, CapabilityApi.CapabilityListener capabilityListener) {
        super(googleApiClient);
        this.zzbRY = capabilityListener;
    }

    /* synthetic */ zzz(GoogleApiClient googleApiClient, CapabilityApi.CapabilityListener capabilityListener, zzp zzp) {
        this(googleApiClient, capabilityListener);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzfw) zzb).zza((zzbaz<Status>) this, this.zzbRY);
        this.zzbRY = null;
    }

    public final /* synthetic */ Result zzb(Status status) {
        this.zzbRY = null;
        return status;
    }
}
