package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

final class zq extends zzbeq<zm, ShortDynamicLink> {
    private final Bundle zzcjY;

    zq(Bundle bundle) {
        this.zzcjY = bundle;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        zm zmVar = (zm) zzb;
        try {
            ((zv) zmVar.zzrf()).zza((zt) new zp(taskCompletionSource), this.zzcjY);
        } catch (RemoteException e) {
        }
    }
}
