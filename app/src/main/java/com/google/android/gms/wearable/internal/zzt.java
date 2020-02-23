package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbaz;
import com.google.android.gms.internal.zzbdw;
import com.google.android.gms.wearable.CapabilityApi;

final class zzt implements zzc<CapabilityApi.CapabilityListener> {
    private /* synthetic */ IntentFilter[] zzbRX;

    zzt(IntentFilter[] intentFilterArr) {
        this.zzbRX = intentFilterArr;
    }

    public final /* synthetic */ void zza(zzfw zzfw, zzbaz zzbaz, Object obj, zzbdw zzbdw) throws RemoteException {
        zzfw.zza((zzbaz<Status>) zzbaz, (CapabilityApi.CapabilityListener) obj, (zzbdw<CapabilityApi.CapabilityListener>) zzbdw, this.zzbRX);
    }
}
