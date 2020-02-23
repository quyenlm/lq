package com.google.android.gms.identity.intents;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.internal.zzcbe;

final class zzb extends Address.zza {
    private /* synthetic */ int val$requestCode;
    private /* synthetic */ UserAddressRequest zzbgA;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzb(GoogleApiClient googleApiClient, UserAddressRequest userAddressRequest, int i) {
        super(googleApiClient);
        this.zzbgA = userAddressRequest;
        this.val$requestCode = i;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzcbe) zzb).zza(this.zzbgA, this.val$requestCode);
        setResult(Status.zzaBm);
    }
}
