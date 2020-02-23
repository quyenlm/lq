package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;

public abstract class zzaxg<R extends Result> extends zzaxs<R> {
    protected zzayt zzarw;

    public zzaxg(zzawy zzawy) {
        super(zzawy.zzXj);
    }

    public abstract void execute();

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        execute();
    }
}
