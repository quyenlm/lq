package com.google.android.gms.auth.api.signin.internal;

import android.os.RemoteException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

final class zzf extends zzl<GoogleSignInResult> {
    final /* synthetic */ zzy zzamm;
    final /* synthetic */ GoogleSignInOptions zzamn;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzf(GoogleApiClient googleApiClient, zzy zzy, GoogleSignInOptions googleSignInOptions) {
        super(googleApiClient);
        this.zzamm = zzy;
        this.zzamn = googleSignInOptions;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzt) ((zzd) zzb).zzrf()).zza(new zzg(this), this.zzamn);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return new GoogleSignInResult((GoogleSignInAccount) null, status);
    }
}
