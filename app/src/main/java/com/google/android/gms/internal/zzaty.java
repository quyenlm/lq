package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.ArrayList;

final class zzaty extends zzbjx {
    private /* synthetic */ int zzaon;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzaty(GoogleApiClient googleApiClient, int i) {
        super(googleApiClient);
        this.zzaon = i;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbka) zzb).zza((zzbaz<zzaud>) this, new zzaub(this.zzaon, (ArrayList<zzasv>) null));
    }
}
