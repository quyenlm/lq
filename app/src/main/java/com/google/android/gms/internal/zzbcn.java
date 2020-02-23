package com.google.android.gms.internal;

import android.support.annotation.WorkerThread;

abstract class zzbcn implements Runnable {
    private /* synthetic */ zzbcd zzaDp;

    private zzbcn(zzbcd zzbcd) {
        this.zzaDp = zzbcd;
    }

    /* synthetic */ zzbcn(zzbcd zzbcd, zzbce zzbce) {
        this(zzbcd);
    }

    @WorkerThread
    public void run() {
        this.zzaDp.zzaCv.lock();
        try {
            if (!Thread.interrupted()) {
                zzpV();
                this.zzaDp.zzaCv.unlock();
            }
        } catch (RuntimeException e) {
            this.zzaDp.zzaCZ.zza(e);
        } finally {
            this.zzaDp.zzaCv.unlock();
        }
    }

    /* access modifiers changed from: protected */
    @WorkerThread
    public abstract void zzpV();
}
