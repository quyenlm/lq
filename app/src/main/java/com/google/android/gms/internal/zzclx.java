package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.nearby.connection.Payload;
import java.util.List;

final class zzclx extends zzcmj {
    private /* synthetic */ List zzbwZ;
    private /* synthetic */ byte[] zzbxj;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzclx(zzclm zzclm, GoogleApiClient googleApiClient, List list, byte[] bArr) {
        super(googleApiClient, (zzcln) null);
        this.zzbwZ = list;
        this.zzbxj = bArr;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzckm) zzb).zza(this, (String[]) this.zzbwZ.toArray(new String[0]), Payload.fromBytes(this.zzbxj), true);
    }
}
