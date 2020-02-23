package com.google.android.gms.internal;

import android.animation.ObjectAnimator;
import android.os.Build;
import android.view.View;

final class zzaux implements View.OnClickListener {
    final /* synthetic */ zzauw zzasJ;

    zzaux(zzauw zzauw) {
        this.zzasJ = zzauw;
    }

    public final void onClick(View view) {
        if (Build.VERSION.SDK_INT >= 16) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "alpha", new float[]{0.0f});
            ofFloat.setDuration(400).addListener(new zzauy(this));
            ofFloat.start();
            return;
        }
        this.zzasJ.zznM();
    }
}
