package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.MessageApi;

final class zzdt extends zzn<MessageApi.SendMessageResult> {
    private /* synthetic */ byte[] zzbKQ;
    private /* synthetic */ String zzbST;
    private /* synthetic */ String zzbSe;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzdt(zzds zzds, GoogleApiClient googleApiClient, String str, String str2, byte[] bArr) {
        super(googleApiClient);
        this.zzbSe = str;
        this.zzbST = str2;
        this.zzbKQ = bArr;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzdn) ((zzfw) zzb).zzrf()).zza(new zzfu(this), this.zzbSe, this.zzbST, this.zzbKQ);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Result zzb(Status status) {
        return new zzdw(status, -1);
    }
}
