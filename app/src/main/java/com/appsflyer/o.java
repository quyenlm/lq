package com.appsflyer;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import java.lang.ref.WeakReference;
import java.util.concurrent.RejectedExecutionException;

@RequiresApi(api = 14)
final class o implements Application.ActivityLifecycleCallbacks {

    /* renamed from: ˏ  reason: contains not printable characters */
    private static o f149;
    /* access modifiers changed from: private */

    /* renamed from: ˊ  reason: contains not printable characters */
    public d f150 = null;
    /* access modifiers changed from: private */

    /* renamed from: ˋ  reason: contains not printable characters */
    public boolean f151 = false;
    /* access modifiers changed from: private */

    /* renamed from: ॱ  reason: contains not printable characters */
    public boolean f152 = true;

    interface d {
        /* renamed from: ˊ  reason: contains not printable characters */
        void m110(Activity activity);

        /* renamed from: ˊ  reason: contains not printable characters */
        void m111(WeakReference<Context> weakReference);
    }

    o() {
    }

    /* renamed from: ˊ  reason: contains not printable characters */
    static o m103() {
        if (f149 == null) {
            f149 = new o();
        }
        return f149;
    }

    /* renamed from: ˎ  reason: contains not printable characters */
    public static o m106() {
        if (f149 != null) {
            return f149;
        }
        throw new IllegalStateException("Foreground is not initialised - invoke at least once with parameter init/get");
    }

    /* renamed from: ˎ  reason: contains not printable characters */
    public final void m109(Application application, d dVar) {
        this.f150 = dVar;
        if (Build.VERSION.SDK_INT >= 14) {
            application.registerActivityLifecycleCallbacks(f149);
        }
    }

    public final void onActivityResumed(Activity activity) {
        boolean z = false;
        this.f152 = false;
        if (!this.f151) {
            z = true;
        }
        this.f151 = true;
        if (z) {
            try {
                this.f150.m110(activity);
            } catch (Exception e2) {
                AFLogger.afErrorLog("Listener threw exception! ", e2);
            }
        }
    }

    public final void onActivityPaused(Activity activity) {
        this.f152 = true;
        try {
            new e(new WeakReference(activity.getApplicationContext())).executeOnExecutor(AFExecutor.getInstance().getThreadPoolExecutor(), new Void[0]);
        } catch (RejectedExecutionException e2) {
            AFLogger.afErrorLog("backgroundTask.executeOnExecutor failed with RejectedExecutionException Exception", e2);
        } catch (Throwable th) {
            AFLogger.afErrorLog("backgroundTask.executeOnExecutor failed with Exception", th);
        }
    }

    class e extends AsyncTask<Void, Void, Void> {

        /* renamed from: ˋ  reason: contains not printable characters */
        private WeakReference<Context> f153;

        /* access modifiers changed from: protected */
        public final /* synthetic */ Object doInBackground(Object[] objArr) {
            return m112();
        }

        public e(WeakReference<Context> weakReference) {
            this.f153 = weakReference;
        }

        /* renamed from: ॱ  reason: contains not printable characters */
        private Void m112() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                AFLogger.afErrorLog("Sleeping attempt failed (essential for background state verification)\n", e);
            }
            if (o.this.f151 && o.this.f152) {
                boolean unused = o.this.f151 = false;
                try {
                    o.this.f150.m111(this.f153);
                } catch (Exception e2) {
                    AFLogger.afErrorLog("Listener threw exception! ", e2);
                    cancel(true);
                }
            }
            this.f153.clear();
            return null;
        }
    }

    public final void onActivityCreated(Activity activity, Bundle bundle) {
        AFDeepLinkManager.getInstance().collectIntentsFromActivities(activity.getIntent());
    }

    public final void onActivityStarted(Activity activity) {
    }

    public final void onActivityStopped(Activity activity) {
    }

    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public final void onActivityDestroyed(Activity activity) {
    }
}
