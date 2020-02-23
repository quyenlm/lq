package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbaz;
import com.google.android.gms.internal.zzbdw;
import com.google.android.gms.wearable.ChannelApi;

final class zzar implements zzc<ChannelApi.ChannelListener> {
    private /* synthetic */ String zzakq;
    private /* synthetic */ IntentFilter[] zzbRX;

    zzar(String str, IntentFilter[] intentFilterArr) {
        this.zzakq = str;
        this.zzbRX = intentFilterArr;
    }

    public final /* synthetic */ void zza(zzfw zzfw, zzbaz zzbaz, Object obj, zzbdw zzbdw) throws RemoteException {
        zzfw.zza((zzbaz<Status>) zzbaz, (ChannelApi.ChannelListener) obj, (zzbdw<ChannelApi.ChannelListener>) zzbdw, this.zzakq, this.zzbRX);
    }
}
