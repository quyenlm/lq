package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wallet.MaskedWalletRequest;
import com.google.android.gms.wallet.Wallet;

final class gn extends Wallet.zzb {
    private /* synthetic */ int val$requestCode;
    private /* synthetic */ MaskedWalletRequest zzbQz;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    gn(gl glVar, GoogleApiClient googleApiClient, MaskedWalletRequest maskedWalletRequest, int i) {
        super(googleApiClient);
        this.zzbQz = maskedWalletRequest;
        this.val$requestCode = i;
    }

    /* access modifiers changed from: protected */
    public final void zza(gu guVar) {
        guVar.zza(this.zzbQz, this.val$requestCode);
        setResult(Status.zzaBm);
    }
}
