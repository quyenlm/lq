package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wallet.FullWalletRequest;
import com.google.android.gms.wallet.Wallet;

final class go extends Wallet.zzb {
    private /* synthetic */ int val$requestCode;
    private /* synthetic */ FullWalletRequest zzbQA;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    go(gl glVar, GoogleApiClient googleApiClient, FullWalletRequest fullWalletRequest, int i) {
        super(googleApiClient);
        this.zzbQA = fullWalletRequest;
        this.val$requestCode = i;
    }

    /* access modifiers changed from: protected */
    public final void zza(gu guVar) {
        guVar.zza(this.zzbQA, this.val$requestCode);
        setResult(Status.zzaBm);
    }
}
