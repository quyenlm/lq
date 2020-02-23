package com.google.android.gms.internal;

import android.view.Choreographer;

final class hq implements Choreographer.FrameCallback {
    private /* synthetic */ hp zzbUv;

    hq(hp hpVar) {
        this.zzbUv = hpVar;
    }

    public final void doFrame(long j) {
        this.zzbUv.doFrame(j);
    }
}
