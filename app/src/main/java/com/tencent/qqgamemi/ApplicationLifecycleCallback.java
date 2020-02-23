package com.tencent.qqgamemi;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class ApplicationLifecycleCallback {
    /* access modifiers changed from: private */
    public static String TAG = "LifecycleCallback";
    /* access modifiers changed from: private */
    public static ActivityLifecycleCb activityCb = null;
    private Context context;
    private boolean isRegister = false;

    ApplicationLifecycleCallback(Context var1) {
        this.context = var1;
    }

    public final void register() {
        if (this.isRegister || activityCb != null || Build.VERSION.SDK_INT < 14) {
            return;
        }
        if (this.context instanceof Activity) {
            activityCb = new ActivityLifecycleCb();
            ((Activity) this.context).getApplication().registerActivityLifecycleCallbacks(activityCb);
            this.isRegister = true;
        } else if (this.context instanceof Service) {
            activityCb = new ActivityLifecycleCb();
            ((Service) this.context).getApplication().registerActivityLifecycleCallbacks(activityCb);
            this.isRegister = true;
        }
    }

    public final void unRegister() {
        this.isRegister = false;
        if (activityCb != null && Build.VERSION.SDK_INT >= 14) {
            if (this.context instanceof Activity) {
                ((Activity) this.context).getApplication().unregisterActivityLifecycleCallbacks(activityCb);
            } else if (this.context instanceof Service) {
                ((Service) this.context).getApplication().unregisterActivityLifecycleCallbacks(activityCb);
            }
            activityCb.detach();
        }
        this.context = null;
    }

    private static final class ActivityLifecycleCb implements Application.ActivityLifecycleCallbacks {
        private Handler handler;
        /* access modifiers changed from: private */
        public boolean isForeground;
        private Runnable onPausedRunnable;
        private Runnable onResumedRunnable;

        private ActivityLifecycleCb() {
            this.isForeground = true;
            this.handler = new Handler(Looper.getMainLooper());
            this.onPausedRunnable = new Runnable() {
                public void run() {
                    if (ApplicationLifecycleCallback.activityCb != null && ActivityLifecycleCb.this.isForeground) {
                        Log.i(ApplicationLifecycleCallback.TAG, "ApplicationLifecycleCallback on onBackground");
                        QmiSdkApi.onBackground();
                        boolean unused = ActivityLifecycleCb.this.isForeground = false;
                    }
                }
            };
            this.onResumedRunnable = new Runnable() {
                public void run() {
                    if (ApplicationLifecycleCallback.activityCb != null && !ActivityLifecycleCb.this.isForeground) {
                        Log.i(ApplicationLifecycleCallback.TAG, "ApplicationLifecycleCallback on onForeground");
                        QmiSdkApi.onFront();
                        boolean unused = ActivityLifecycleCb.this.isForeground = true;
                    }
                }
            };
        }

        public final void onActivityCreated(Activity var1, Bundle var2) {
        }

        public final void onActivityDestroyed(Activity var1) {
        }

        public final void onActivityPaused(Activity var1) {
            Log.v(ApplicationLifecycleCallback.TAG, var1.getComponentName().getClassName() + "  onActivityPaused");
            this.handler.removeCallbacks(this.onResumedRunnable);
            this.handler.post(this.onPausedRunnable);
        }

        public final void onActivityResumed(Activity var1) {
            Log.v(ApplicationLifecycleCallback.TAG, var1.getComponentName().getClassName() + "  onActivityResumed");
            this.handler.removeCallbacks(this.onPausedRunnable);
            this.handler.post(this.onResumedRunnable);
        }

        public final void onActivitySaveInstanceState(Activity var1, Bundle var2) {
        }

        public final void onActivityStarted(Activity var1) {
        }

        public final void onActivityStopped(Activity var1) {
        }

        public final void detach() {
            this.handler.removeCallbacks(this.onResumedRunnable);
            this.handler.removeCallbacks(this.onPausedRunnable);
        }
    }
}
