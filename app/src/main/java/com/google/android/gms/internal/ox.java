package com.google.android.gms.internal;

import java.util.concurrent.ScheduledFuture;

final class ox implements Runnable {
    private /* synthetic */ op zzcbf;

    ox(op opVar) {
        this.zzcbf = opVar;
    }

    public final void run() {
        ScheduledFuture unused = this.zzcbf.zzcbb = null;
        if (this.zzcbf.zzGj()) {
            this.zzcbf.interrupt("connection_idle");
        } else {
            this.zzcbf.zzGi();
        }
    }
}
