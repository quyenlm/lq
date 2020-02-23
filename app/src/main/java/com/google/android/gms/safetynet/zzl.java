package com.google.android.gms.safetynet;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.internal.zzbeq;
import com.google.android.gms.internal.zzcry;
import com.google.android.gms.internal.zzcsn;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzl extends zzbeq<zzcsn, Void> {
    zzl(SafetyNetClient safetyNetClient) {
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzcry) ((zzcsn) zzb).zzrf()).zzAj();
    }
}
