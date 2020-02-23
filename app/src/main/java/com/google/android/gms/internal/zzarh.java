package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.auth.api.credentials.CredentialRequest;
import com.google.android.gms.auth.api.credentials.CredentialRequestResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

final class zzarh extends zzarn<CredentialRequestResult> {
    private /* synthetic */ CredentialRequest zzaly;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzarh(zzarg zzarg, GoogleApiClient googleApiClient, CredentialRequest credentialRequest) {
        super(googleApiClient);
        this.zzaly = credentialRequest;
    }

    /* access modifiers changed from: protected */
    public final void zza(Context context, zzart zzart) throws RemoteException {
        zzart.zza((zzarr) new zzari(this), this.zzaly);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return zzarf.zze(status);
    }
}
