package com.tencent.tpshell;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ForegroundCallbacks implements Application.ActivityLifecycleCallbacks {
    private static Runnable check;
    /* access modifiers changed from: private */
    public boolean foreground;
    private Handler handler;
    /* access modifiers changed from: private */
    public List<Listener> listeners;
    /* access modifiers changed from: private */
    public boolean paused;

    public interface Listener {
        void onBecameBackground();

        void onBecameForeground();
    }

    private ForegroundCallbacks() {
        this.foreground = false;
        this.paused = true;
        this.handler = new Handler();
        this.listeners = new CopyOnWriteArrayList();
    }

    private static class SingletonInstance {
        /* access modifiers changed from: private */
        public static final ForegroundCallbacks instance = new ForegroundCallbacks();

        private SingletonInstance() {
        }
    }

    public static ForegroundCallbacks getInstance() {
        return SingletonInstance.instance;
    }

    public boolean isForeground() {
        return this.foreground;
    }

    public boolean isBackground() {
        return !this.foreground;
    }

    public void addListener(Listener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        this.listeners.remove(listener);
    }

    public void onActivityResumed(Activity activity) {
        boolean z = false;
        this.paused = false;
        if (!this.foreground) {
            z = true;
        }
        this.foreground = true;
        if (check != null) {
            this.handler.removeCallbacks(check);
        }
        if (z) {
            for (Listener onBecameForeground : this.listeners) {
                try {
                    onBecameForeground.onBecameForeground();
                } catch (Throwable th) {
                }
            }
        }
    }

    public void onActivityPaused(Activity activity) {
        this.paused = true;
        if (check != null) {
            this.handler.removeCallbacks(check);
        }
        Handler handler2 = this.handler;
        AnonymousClass1 r1 = new Runnable() {
            public void run() {
                if (ForegroundCallbacks.this.foreground && ForegroundCallbacks.this.paused) {
                    boolean unused = ForegroundCallbacks.this.foreground = false;
                    for (Listener onBecameBackground : ForegroundCallbacks.this.listeners) {
                        try {
                            onBecameBackground.onBecameBackground();
                        } catch (Throwable th) {
                        }
                    }
                }
            }
        };
        check = r1;
        handler2.postDelayed(r1, 500);
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public void onActivityStarted(Activity activity) {
    }

    public void onActivityStopped(Activity activity) {
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public void onActivityDestroyed(Activity activity) {
    }
}
