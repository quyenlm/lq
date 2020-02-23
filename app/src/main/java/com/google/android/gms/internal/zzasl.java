package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

abstract class zzasl extends zzbeq<zzash, Void> {
    private TaskCompletionSource<Void> zzalE;

    private zzasl() {
    }

    /* synthetic */ zzasl(zzasj zzasj) {
        this();
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        this.zzalE = taskCompletionSource;
        zza((zzasd) ((zzash) zzb).zzrf());
    }

    /* access modifiers changed from: protected */
    public abstract void zza(zzasd zzasd) throws RemoteException;

    /* access modifiers changed from: protected */
    public final void zzg(Status status) {
        zzber.zza(status, null, this.zzalE);
    }
}
