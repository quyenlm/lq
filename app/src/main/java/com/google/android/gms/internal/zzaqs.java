package com.google.android.gms.internal;

import android.accounts.Account;
import android.os.RemoteException;
import com.google.android.gms.auth.account.zza;
import com.google.android.gms.auth.account.zzd;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

final class zzaqs extends zzbay<Result, zzaqx> {
    private /* synthetic */ Account zzako;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzaqs(zzaqn zzaqn, Api api, GoogleApiClient googleApiClient, Account account) {
        super((Api<?>) api, googleApiClient);
        this.zzako = account;
    }

    public final /* bridge */ /* synthetic */ void setResult(Object obj) {
        super.setResult((Result) obj);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzd) ((zzaqx) zzb).zzrf()).zza((zza) new zzaqt(this), this.zzako);
    }

    /* access modifiers changed from: protected */
    public final Result zzb(Status status) {
        return new zzaqw(status);
    }
}
