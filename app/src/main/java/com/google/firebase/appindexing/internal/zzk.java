package com.google.firebase.appindexing.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbdn;
import com.google.android.gms.internal.zzbeq;
import com.google.android.gms.tasks.TaskCompletionSource;

abstract class zzk extends zzbeq<zzd, Status> {
    /* access modifiers changed from: private */
    public TaskCompletionSource<Status> zzalE;

    private zzk() {
    }

    /* synthetic */ zzk(zzg zzg) {
        this();
    }

    /* access modifiers changed from: protected */
    public final zzbdn zzEC() {
        return new zzl(this);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        this.zzalE = taskCompletionSource;
        zza((zzu) ((zzd) zzb).zzrf());
    }

    /* access modifiers changed from: protected */
    public abstract void zza(zzu zzu) throws RemoteException;
}
