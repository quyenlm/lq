package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

final class zs extends zzbeq<zm, PendingDynamicLinkData> {
    private final String zzcjS;

    zs(String str) {
        this.zzcjS = str;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        zm zmVar = (zm) zzb;
        try {
            ((zv) zmVar.zzrf()).zza((zt) new zr(taskCompletionSource), this.zzcjS);
        } catch (RemoteException e) {
        }
    }
}
