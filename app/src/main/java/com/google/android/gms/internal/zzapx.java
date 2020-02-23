package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzapw;

final class zzapx extends zzapw.zzc<Status> {
    private /* synthetic */ zzapl[] zzajM;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzapx(zzapw zzapw, GoogleApiClient googleApiClient, zzapl[] zzaplArr) {
        super(googleApiClient);
        this.zzajM = zzaplArr;
    }

    /* access modifiers changed from: protected */
    public final void zza(zzapp zzapp) throws RemoteException {
        zzapp.zza(new zzapw.zzd(this), (String) null, this.zzajM);
    }
}
