package com.google.android.gms.internal;

import android.support.annotation.WorkerThread;

final class zzcjc extends zzcer {
    private /* synthetic */ zzcja zzbuu;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzcjc(zzcja zzcja, zzcgl zzcgl) {
        super(zzcgl);
        this.zzbuu = zzcja;
    }

    @WorkerThread
    public final void run() {
        this.zzbuu.zzzp();
    }
}
