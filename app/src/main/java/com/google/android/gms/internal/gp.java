package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wallet.Wallet;

final class gp extends Wallet.zzb {
    private /* synthetic */ int val$requestCode;
    private /* synthetic */ String zzbQB;
    private /* synthetic */ String zzbQC;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    gp(gl glVar, GoogleApiClient googleApiClient, String str, String str2, int i) {
        super(googleApiClient);
        this.zzbQB = str;
        this.zzbQC = str2;
        this.val$requestCode = i;
    }

    /* access modifiers changed from: protected */
    public final void zza(gu guVar) {
        guVar.zzc(this.zzbQB, this.zzbQC, this.val$requestCode);
        setResult(Status.zzaBm);
    }
}
