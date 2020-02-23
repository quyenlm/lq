package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wallet.NotifyTransactionStatusRequest;
import com.google.android.gms.wallet.Wallet;

final class gq extends Wallet.zzb {
    private /* synthetic */ NotifyTransactionStatusRequest zzbQD;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    gq(gl glVar, GoogleApiClient googleApiClient, NotifyTransactionStatusRequest notifyTransactionStatusRequest) {
        super(googleApiClient);
        this.zzbQD = notifyTransactionStatusRequest;
    }

    /* access modifiers changed from: protected */
    public final void zza(gu guVar) {
        guVar.zza(this.zzbQD);
        setResult(Status.zzaBm);
    }
}
