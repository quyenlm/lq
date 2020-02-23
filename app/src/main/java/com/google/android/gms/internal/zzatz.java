package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.ArrayList;

final class zzatz extends zzbjx {
    private /* synthetic */ int zzaon = 10003;
    private /* synthetic */ ArrayList zzaoo;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzatz(GoogleApiClient googleApiClient, int i, ArrayList arrayList) {
        super(googleApiClient);
        this.zzaoo = arrayList;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzbka) zzb).zza((zzbaz<zzaud>) this, new zzaub(this.zzaon, this.zzaoo));
    }
}
