package com.google.android.gms.internal;

import android.view.View;

final class zzakd implements View.OnAttachStateChangeListener {
    private /* synthetic */ zzaet zzabH;
    private /* synthetic */ zzakb zzabJ;

    zzakd(zzakb zzakb, zzaet zzaet) {
        this.zzabJ = zzakb;
        this.zzabH = zzaet;
    }

    public final void onViewAttachedToWindow(View view) {
        this.zzabJ.zza(view, this.zzabH, 10);
    }

    public final void onViewDetachedFromWindow(View view) {
    }
}
