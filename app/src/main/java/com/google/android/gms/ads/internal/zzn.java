package com.google.android.gms.ads.internal;

import android.view.MotionEvent;
import android.view.View;

final class zzn implements View.OnTouchListener {
    private /* synthetic */ zzl zzte;
    private /* synthetic */ zzw zztf;

    zzn(zzl zzl, zzw zzw) {
        this.zzte = zzl;
        this.zztf = zzw;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        this.zztf.recordClick();
        if (this.zzte.zztc == null) {
            return false;
        }
        this.zzte.zztc.zzha();
        return false;
    }
}
