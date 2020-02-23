package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.js.zzy;

final class zzaba implements Runnable {
    private /* synthetic */ zzaaz zzUc;
    private /* synthetic */ zzafg zzsW;

    zzaba(zzaaz zzaaz, zzafg zzafg) {
        this.zzUc = zzaaz;
        this.zzsW = zzafg;
    }

    public final void run() {
        this.zzUc.zzSm.zza(this.zzsW);
        if (this.zzUc.zzUb != null) {
            this.zzUc.zzUb.release();
            zzy unused = this.zzUc.zzUb = null;
        }
    }
}
