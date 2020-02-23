package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.js.zzy;

final class zzabe implements Runnable {
    private /* synthetic */ zzaaz zzUc;

    zzabe(zzaaz zzaaz) {
        this.zzUc = zzaaz;
    }

    public final void run() {
        if (this.zzUc.zzUb != null) {
            this.zzUc.zzUb.release();
            zzy unused = this.zzUc.zzUb = null;
        }
    }
}
