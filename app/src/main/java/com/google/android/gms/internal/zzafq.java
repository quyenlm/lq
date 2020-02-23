package com.google.android.gms.internal;

final class zzafq implements Runnable {
    private /* synthetic */ zzafp zzYX;

    zzafq(zzafp zzafp) {
        this.zzYX = zzafp;
    }

    public final void run() {
        Thread unused = this.zzYX.zzYV = Thread.currentThread();
        this.zzYX.zzbd();
    }
}
