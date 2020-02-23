package com.google.android.gms.internal;

import android.accounts.Account;
import android.os.RemoteException;
import com.google.android.gms.auth.account.WorkAccountApi;
import com.google.android.gms.auth.account.zza;
import com.google.android.gms.auth.account.zzd;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

final class zzaqq extends zzbay<WorkAccountApi.AddAccountResult, zzaqx> {
    private /* synthetic */ String zzakq;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzaqq(zzaqn zzaqn, Api api, GoogleApiClient googleApiClient, String str) {
        super((Api<?>) api, googleApiClient);
        this.zzakq = str;
    }

    public final /* bridge */ /* synthetic */ void setResult(Object obj) {
        super.setResult((WorkAccountApi.AddAccountResult) obj);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzd) ((zzaqx) zzb).zzrf()).zza((zza) new zzaqr(this), this.zzakq);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return new zzaqv(status, (Account) null);
    }
}
