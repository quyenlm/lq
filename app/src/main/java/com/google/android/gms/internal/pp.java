package com.google.android.gms.internal;

import java.io.EOFException;

final class pp implements Runnable {
    private /* synthetic */ pl zzcbS;
    private /* synthetic */ yj zzcbU;

    pp(pl plVar, yj yjVar) {
        this.zzcbS = plVar;
        this.zzcbU = yjVar;
    }

    public final void run() {
        if (this.zzcbU.getCause() == null || !(this.zzcbU.getCause() instanceof EOFException)) {
            this.zzcbS.zzcbQ.zzbZE.zzb("WebSocket error.", this.zzcbU, new Object[0]);
        } else {
            this.zzcbS.zzcbQ.zzbZE.zzb("WebSocket reached EOF.", (Throwable) null, new Object[0]);
        }
        this.zzcbS.zzcbQ.zzGw();
    }
}
