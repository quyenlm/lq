package com.google.android.gms.location;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.internal.zzbdw;
import com.google.android.gms.internal.zzbee;
import com.google.android.gms.internal.zzccu;
import com.google.android.gms.internal.zzcdj;
import com.google.android.gms.internal.zzcdn;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzg extends zzbee<zzcdj, LocationCallback> {
    private /* synthetic */ zzcdn zzbhD;
    private /* synthetic */ zzbdw zzbhE;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzg(FusedLocationProviderClient fusedLocationProviderClient, zzbdw zzbdw, zzcdn zzcdn, zzbdw zzbdw2) {
        super(zzbdw);
        this.zzbhD = zzcdn;
        this.zzbhE = zzbdw2;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zzb(Api.zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        ((zzcdj) zzb).zza(this.zzbhD, (zzbdw<LocationCallback>) this.zzbhE, (zzccu) new FusedLocationProviderClient.zza(taskCompletionSource));
    }
}
