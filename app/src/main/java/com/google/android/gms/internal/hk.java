package com.google.android.gms.internal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v4.util.SimpleArrayMap;

public class hk extends AnimatorListenerAdapter {
    private SimpleArrayMap<Animator, Boolean> zzbUm = new SimpleArrayMap<>();

    public void onAnimationCancel(Animator animator) {
        this.zzbUm.put(animator, true);
    }

    public void onAnimationStart(Animator animator) {
        this.zzbUm.put(animator, false);
    }

    /* access modifiers changed from: protected */
    public final boolean zzb(Animator animator) {
        return this.zzbUm.containsKey(animator) && this.zzbUm.get(animator).booleanValue();
    }
}
