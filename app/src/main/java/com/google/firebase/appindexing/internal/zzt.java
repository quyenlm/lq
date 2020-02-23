package com.google.firebase.appindexing.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.zzapp;
import com.google.android.gms.internal.zzapu;
import com.google.android.gms.internal.zzbaz;
import com.google.android.gms.internal.zzbeq;
import com.google.android.gms.tasks.TaskCompletionSource;

abstract class zzt extends zzbeq<zzapu, Void> implements zzbaz<Status> {
    private TaskCompletionSource<Void> zzalE;

    private zzt() {
    }

    /* synthetic */ zzt(zzr zzr) {
        this();
    }

    public final /* synthetic */ void setResult(Object obj) {
        Status status = (Status) obj;
        if (status.isSuccess()) {
            this.zzalE.setResult(null);
        } else {
            this.zzalE.setException(zzaa.zzb(status, "User Action indexing error, please try again."));
        }
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        this.zzalE = taskCompletionSource;
        zza((zzapp) ((zzapu) zzb).zzrf());
    }

    /* access modifiers changed from: protected */
    public abstract void zza(zzapp zzapp) throws RemoteException;

    public final void zzr(Status status) {
        zzbo.zzb(!status.isSuccess(), (Object) "Failed result must not be success.");
        this.zzalE.setException(zzaa.zzb(status, status.getStatusMessage()));
    }
}
