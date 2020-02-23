package com.google.android.gms.internal;

import android.app.Activity;
import android.app.Application;

final class zzgm implements zzgr {
    private /* synthetic */ Activity val$activity;

    zzgm(zzgj zzgj, Activity activity) {
        this.val$activity = activity;
    }

    public final void zza(Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        activityLifecycleCallbacks.onActivityResumed(this.val$activity);
    }
}
