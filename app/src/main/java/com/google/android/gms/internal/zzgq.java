package com.google.android.gms.internal;

import android.app.Activity;
import android.app.Application;

final class zzgq implements zzgr {
    private /* synthetic */ Activity val$activity;

    zzgq(zzgj zzgj, Activity activity) {
        this.val$activity = activity;
    }

    public final void zza(Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        activityLifecycleCallbacks.onActivityDestroyed(this.val$activity);
    }
}
