package com.google.android.gms.location;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.internal.zzbdy;
import com.google.android.gms.internal.zzbey;
import com.google.android.gms.internal.zzccu;
import com.google.android.gms.internal.zzcdj;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzh extends zzbey<zzcdj, LocationCallback> {
    zzh(FusedLocationProviderClient fusedLocationProviderClient, zzbdy zzbdy) {
        super(zzbdy);
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zzc(Api.zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzcdj) zzb).zzb(zzqG(), (zzccu) null);
    }
}
