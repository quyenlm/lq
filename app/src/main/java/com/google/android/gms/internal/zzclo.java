package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.nearby.connection.Payload;
import java.util.List;

final class zzclo extends zzcmj {
    private /* synthetic */ List zzbwZ;
    private /* synthetic */ Payload zzbxa;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzclo(zzclm zzclm, GoogleApiClient googleApiClient, List list, Payload payload) {
        super(googleApiClient, (zzcln) null);
        this.zzbwZ = list;
        this.zzbxa = payload;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzckm) zzb).zza(this, (String[]) this.zzbwZ.toArray(new String[0]), this.zzbxa, false);
    }
}
