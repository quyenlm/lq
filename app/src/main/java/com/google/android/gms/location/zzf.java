package com.google.android.gms.location;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.internal.zzbeq;
import com.google.android.gms.internal.zzcdj;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzf extends zzbeq<zzcdj, LocationAvailability> {
    zzf(FusedLocationProviderClient fusedLocationProviderClient) {
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        taskCompletionSource.setResult(((zzcdj) zzb).zzvQ());
    }
}
