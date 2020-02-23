package com.google.firebase.appindexing.internal;

import android.os.RemoteException;
import com.google.android.gms.internal.zzapp;
import com.google.android.gms.internal.zzapw;

final class zzr extends zzt {
    private /* synthetic */ zza[] zzbVW;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzr(zzq zzq, zza[] zzaArr) {
        super((zzr) null);
        this.zzbVW = zzaArr;
    }

    /* access modifiers changed from: protected */
    public final void zza(zzapp zzapp) throws RemoteException {
        zzapp.zza(new zzapw.zzd(this), this.zzbVW);
    }
}
