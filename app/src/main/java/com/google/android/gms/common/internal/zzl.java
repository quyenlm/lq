package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;

public final class zzl implements ServiceConnection {
    private /* synthetic */ zzd zzaHe;
    private final int zzaHh;

    public zzl(zzd zzd, int i) {
        this.zzaHe = zzd;
        this.zzaHh = i;
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        zzaw zzax;
        if (iBinder == null) {
            this.zzaHe.zzaz(16);
            return;
        }
        synchronized (this.zzaHe.zzaGO) {
            zzd zzd = this.zzaHe;
            if (iBinder == null) {
                zzax = null;
            } else {
                IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                zzax = (queryLocalInterface == null || !(queryLocalInterface instanceof zzaw)) ? new zzax(iBinder) : (zzaw) queryLocalInterface;
            }
            zzaw unused = zzd.zzaGP = zzax;
        }
        this.zzaHe.zza(0, (Bundle) null, this.zzaHh);
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        synchronized (this.zzaHe.zzaGO) {
            zzaw unused = this.zzaHe.zzaGP = null;
        }
        this.zzaHe.mHandler.sendMessage(this.zzaHe.mHandler.obtainMessage(6, this.zzaHh, 1));
    }
}
