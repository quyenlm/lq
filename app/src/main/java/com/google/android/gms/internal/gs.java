package com.google.android.gms.internal;

import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.Wallet;

final class gs extends Wallet.zza<BooleanResult> {
    gs(gl glVar, GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    /* access modifiers changed from: protected */
    public final void zza(gu guVar) {
        guVar.zza(IsReadyToPayRequest.newBuilder().build(), (zzbaz<BooleanResult>) this);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return new BooleanResult(status, false);
    }
}
