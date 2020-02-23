package com.google.firebase.appindexing.internal;

import android.os.RemoteException;

final class zzh extends zzk {
    private /* synthetic */ String[] zzbVM;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzh(zzf zzf, String[] strArr) {
        super((zzg) null);
        this.zzbVM = strArr;
    }

    /* access modifiers changed from: protected */
    public final void zza(zzu zzu) throws RemoteException {
        zzu.zza(zzEC(), this.zzbVM);
    }
}
