package com.google.android.gms.internal;

final class zzcwf implements Runnable {
    private /* synthetic */ zzcwd zzbJa;

    zzcwf(zzcwd zzcwd) {
        this.zzbJa = zzcwd;
    }

    public final void run() {
        boolean unused = this.zzbJa.zzbIV = false;
        this.zzbJa.zzbIT.dispatch();
    }
}
