package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.internal.zzh;
import java.util.Collection;

final class zzcre extends zzcrg {
    private /* synthetic */ Collection zzbAQ;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcre(zzcra zzcra, GoogleApiClient googleApiClient, Collection collection) {
        super(googleApiClient, (zzcrb) null);
        this.zzbAQ = collection;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzh) zzb).zza(this, this.zzbAQ);
    }
}
