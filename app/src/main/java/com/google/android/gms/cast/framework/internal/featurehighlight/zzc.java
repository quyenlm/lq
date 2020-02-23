package com.google.android.gms.cast.framework.internal.featurehighlight;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

final class zzc extends GestureDetector.SimpleOnGestureListener {
    private /* synthetic */ View zzatd;
    private /* synthetic */ boolean zzate = true;
    private /* synthetic */ zzh zzatf;

    zzc(zza zza, View view, boolean z, zzh zzh) {
        this.zzatd = view;
        this.zzatf = zzh;
    }

    public final boolean onSingleTapUp(MotionEvent motionEvent) {
        if (this.zzatd.getParent() != null) {
            this.zzatd.performClick();
        }
        if (!this.zzate) {
            return true;
        }
        this.zzatf.zznL();
        return true;
    }
}
