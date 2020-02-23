package com.google.android.gms.internal;

import java.util.concurrent.ScheduledFuture;

final class pr implements Runnable {
    private /* synthetic */ Runnable zzatg;
    private /* synthetic */ pq zzcct;

    pr(pq pqVar, Runnable runnable) {
        this.zzcct = pqVar;
        this.zzatg = runnable;
    }

    public final void run() {
        ScheduledFuture unused = this.zzcct.zzccq = null;
        this.zzatg.run();
    }
}
