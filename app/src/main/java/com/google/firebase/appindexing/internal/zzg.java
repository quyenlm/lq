package com.google.firebase.appindexing.internal;

import android.os.RemoteException;

final class zzg extends zzk {
    private /* synthetic */ Thing[] zzbVL;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzg(zzf zzf, Thing[] thingArr) {
        super((zzg) null);
        this.zzbVL = thingArr;
    }

    /* access modifiers changed from: protected */
    public final void zza(zzu zzu) throws RemoteException {
        zzu.zza(zzEC(), this.zzbVL);
    }
}
