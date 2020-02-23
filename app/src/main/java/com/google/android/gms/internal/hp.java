package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.view.Choreographer;

public abstract class hp {
    private Runnable zzbUt;
    private Choreographer.FrameCallback zzbUu;

    public abstract void doFrame(long j);

    /* access modifiers changed from: package-private */
    @TargetApi(16)
    public final Choreographer.FrameCallback zzEe() {
        if (this.zzbUu == null) {
            this.zzbUu = new hq(this);
        }
        return this.zzbUu;
    }

    /* access modifiers changed from: package-private */
    public final Runnable zzEf() {
        if (this.zzbUt == null) {
            this.zzbUt = new hr(this);
        }
        return this.zzbUt;
    }
}
