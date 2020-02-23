package com.google.android.gms.internal;

import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.Wallet;

final class gt extends Wallet.zza<BooleanResult> {
    private /* synthetic */ IsReadyToPayRequest zzbQE;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    gt(gl glVar, GoogleApiClient googleApiClient, IsReadyToPayRequest isReadyToPayRequest) {
        super(googleApiClient);
        this.zzbQE = isReadyToPayRequest;
    }

    /* access modifiers changed from: protected */
    public final void zza(gu guVar) {
        guVar.zza(this.zzbQE, (zzbaz<BooleanResult>) this);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return new BooleanResult(status, false);
    }
}
