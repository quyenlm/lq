package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbaz;
import com.google.android.gms.internal.zzbdw;
import com.google.android.gms.wearable.DataApi;

final class zzbq implements zzc<DataApi.DataListener> {
    private /* synthetic */ IntentFilter[] zzbRX;

    zzbq(IntentFilter[] intentFilterArr) {
        this.zzbRX = intentFilterArr;
    }

    public final /* synthetic */ void zza(zzfw zzfw, zzbaz zzbaz, Object obj, zzbdw zzbdw) throws RemoteException {
        zzfw.zza((zzbaz<Status>) zzbaz, (DataApi.DataListener) obj, (zzbdw<DataApi.DataListener>) zzbdw, this.zzbRX);
    }
}
