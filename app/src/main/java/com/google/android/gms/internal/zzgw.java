package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import com.google.android.gms.ads.internal.zzbs;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@TargetApi(14)
final class zzgw implements Application.ActivityLifecycleCallbacks {
    @Nullable
    private Activity mActivity;
    private Context mContext;
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    private boolean zzuH = false;
    /* access modifiers changed from: private */
    public boolean zzyq = true;
    /* access modifiers changed from: private */
    public boolean zzyr = false;
    /* access modifiers changed from: private */
    public final List<zzgy> zzys = new ArrayList();
    private final List<zzhl> zzyt = new ArrayList();
    private Runnable zzyu;
    private long zzyv;

    zzgw() {
    }

    private final void setActivity(Activity activity) {
        synchronized (this.mLock) {
            if (!activity.getClass().getName().startsWith("com.google.android.gms.ads")) {
                this.mActivity = activity;
            }
        }
    }

    @Nullable
    public final Activity getActivity() {
        return this.mActivity;
    }

    @Nullable
    public final Context getContext() {
        return this.mContext;
    }

    public final void onActivityCreated(Activity activity, Bundle bundle) {
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onActivityDestroyed(android.app.Activity r6) {
        /*
            r5 = this;
            java.lang.Object r1 = r5.mLock
            monitor-enter(r1)
            android.app.Activity r0 = r5.mActivity     // Catch:{ all -> 0x0040 }
            if (r0 != 0) goto L_0x0009
            monitor-exit(r1)     // Catch:{ all -> 0x0040 }
        L_0x0008:
            return
        L_0x0009:
            android.app.Activity r0 = r5.mActivity     // Catch:{ all -> 0x0040 }
            boolean r0 = r0.equals(r6)     // Catch:{ all -> 0x0040 }
            if (r0 == 0) goto L_0x0014
            r0 = 0
            r5.mActivity = r0     // Catch:{ all -> 0x0040 }
        L_0x0014:
            java.util.List<com.google.android.gms.internal.zzhl> r0 = r5.zzyt     // Catch:{ all -> 0x0040 }
            java.util.Iterator r2 = r0.iterator()     // Catch:{ all -> 0x0040 }
        L_0x001a:
            boolean r0 = r2.hasNext()     // Catch:{ all -> 0x0040 }
            if (r0 == 0) goto L_0x0043
            java.lang.Object r0 = r2.next()     // Catch:{ all -> 0x0040 }
            com.google.android.gms.internal.zzhl r0 = (com.google.android.gms.internal.zzhl) r0     // Catch:{ all -> 0x0040 }
            boolean r0 = r0.zza(r6)     // Catch:{ Exception -> 0x0030 }
            if (r0 == 0) goto L_0x001a
            r2.remove()     // Catch:{ Exception -> 0x0030 }
            goto L_0x001a
        L_0x0030:
            r0 = move-exception
            com.google.android.gms.internal.zzafk r3 = com.google.android.gms.ads.internal.zzbs.zzbD()     // Catch:{ all -> 0x0040 }
            java.lang.String r4 = "AppActivityTracker.ActivityListener.onActivityDestroyed"
            r3.zza((java.lang.Throwable) r0, (java.lang.String) r4)     // Catch:{ all -> 0x0040 }
            java.lang.String r3 = "onActivityStateChangedListener threw exception."
            com.google.android.gms.internal.zzafr.zzb(r3, r0)     // Catch:{ all -> 0x0040 }
            goto L_0x001a
        L_0x0040:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0040 }
            throw r0
        L_0x0043:
            monitor-exit(r1)     // Catch:{ all -> 0x0040 }
            goto L_0x0008
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgw.onActivityDestroyed(android.app.Activity):void");
    }

    public final void onActivityPaused(Activity activity) {
        setActivity(activity);
        synchronized (this.mLock) {
            Iterator<zzhl> it = this.zzyt.iterator();
            while (it.hasNext()) {
                it.next();
            }
        }
        this.zzyr = true;
        if (this.zzyu != null) {
            zzagz.zzZr.removeCallbacks(this.zzyu);
        }
        Handler handler = zzagz.zzZr;
        zzgx zzgx = new zzgx(this);
        this.zzyu = zzgx;
        handler.postDelayed(zzgx, this.zzyv);
    }

    public final void onActivityResumed(Activity activity) {
        boolean z = false;
        setActivity(activity);
        this.zzyr = false;
        if (!this.zzyq) {
            z = true;
        }
        this.zzyq = true;
        if (this.zzyu != null) {
            zzagz.zzZr.removeCallbacks(this.zzyu);
        }
        synchronized (this.mLock) {
            Iterator<zzhl> it = this.zzyt.iterator();
            while (it.hasNext()) {
                it.next();
            }
            if (z) {
                for (zzgy zzf : this.zzys) {
                    try {
                        zzf.zzf(true);
                    } catch (Exception e) {
                        zzafr.zzb("OnForegroundStateChangedListener threw exception.", e);
                    }
                }
            } else {
                zzafr.zzaC("App is still foreground.");
            }
        }
    }

    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public final void onActivityStarted(Activity activity) {
        setActivity(activity);
    }

    public final void onActivityStopped(Activity activity) {
    }

    public final void zza(Application application, Context context) {
        if (!this.zzuH) {
            application.registerActivityLifecycleCallbacks(this);
            if (context instanceof Activity) {
                setActivity((Activity) context);
            }
            this.mContext = context;
            this.zzyv = ((Long) zzbs.zzbL().zzd(zzmo.zzDK)).longValue();
            this.zzuH = true;
        }
    }

    public final void zza(zzgy zzgy) {
        synchronized (this.mLock) {
            this.zzys.add(zzgy);
        }
    }
}
