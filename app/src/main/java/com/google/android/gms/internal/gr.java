package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wallet.Wallet;

final class gr extends Wallet.zzb {
    private /* synthetic */ int val$requestCode;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    gr(gl glVar, GoogleApiClient googleApiClient, int i) {
        super(googleApiClient);
        this.val$requestCode = i;
    }

    /* access modifiers changed from: protected */
    public final void zza(gu guVar) {
        guVar.zzbQ(this.val$requestCode);
        setResult(Status.zzaBm);
    }
}
