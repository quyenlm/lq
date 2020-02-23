package com.tencent.component.app;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import java.util.ArrayList;

public class BaseApplication extends Application {
    private final ArrayList<ActivityLifecycleCallbacks> mActivityLifecycleCallbacks = new ArrayList<>();
    private final ArrayList<FragmentLifecycleCallbacks> mFragmentLifecycleCallbacks = new ArrayList<>();

    public interface ActivityLifecycleCallbacks {
        void onActivityCreated(Activity activity, Bundle bundle);

        void onActivityDestroyed(Activity activity);

        void onActivityPaused(Activity activity);

        void onActivityResult(Activity activity, int i, int i2, Intent intent);

        void onActivityResumed(Activity activity);

        void onActivitySaveInstanceState(Activity activity, Bundle bundle);

        void onActivityStarted(Activity activity);

        void onActivityStopped(Activity activity);

        void onActivityUserLeaveHint(Activity activity);
    }

    public interface FragmentLifecycleCallbacks {
        void onActivityCreated(Fragment fragment, Bundle bundle);

        void onActivityResult(Fragment fragment, int i, int i2, Intent intent);

        void onFragmentAttached(Fragment fragment, Activity activity);

        void onFragmentCreated(Fragment fragment, Bundle bundle);

        void onFragmentDestroyed(Fragment fragment);

        void onFragmentDetached(Fragment fragment);

        void onFragmentPaused(Fragment fragment);

        void onFragmentResumed(Fragment fragment);

        void onFragmentSaveInstanceState(Fragment fragment, Bundle bundle);

        void onFragmentStarted(Fragment fragment);

        void onFragmentStopped(Fragment fragment);
    }

    public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        synchronized (this.mActivityLifecycleCallbacks) {
            this.mActivityLifecycleCallbacks.add(callback);
        }
    }

    public void unregisterActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        synchronized (this.mActivityLifecycleCallbacks) {
            this.mActivityLifecycleCallbacks.remove(callback);
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchActivityCreatedInner(Activity activity, Bundle savedInstanceState) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityCreated(activity, savedInstanceState);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchActivityStartedInner(Activity activity) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityStarted(activity);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchActivityResumedInner(Activity activity) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityResumed(activity);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchActivityPausedInner(Activity activity) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityPaused(activity);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchActivityStoppedInner(Activity activity) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityStopped(activity);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchActivitySaveInstanceStateInner(Activity activity, Bundle outState) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivitySaveInstanceState(activity, outState);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchActivityDestroyedInner(Activity activity) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityDestroyed(activity);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchActivityUserLeaveHintInner(Activity activity) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityUserLeaveHint(activity);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchActivityResultInner(Activity activity, int requestCode, int resultCode, Intent data) {
        Object[] callbacks = collectActivityLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((ActivityLifecycleCallbacks) obj).onActivityResult(activity, requestCode, resultCode, data);
            }
        }
    }

    private Object[] collectActivityLifecycleCallbacks() {
        Object[] callbacks = null;
        synchronized (this.mActivityLifecycleCallbacks) {
            if (this.mActivityLifecycleCallbacks.size() > 0) {
                callbacks = this.mActivityLifecycleCallbacks.toArray();
            }
        }
        return callbacks;
    }

    public void registerFragmentLifecycleCallbacks(FragmentLifecycleCallbacks callback) {
        synchronized (this.mFragmentLifecycleCallbacks) {
            this.mFragmentLifecycleCallbacks.add(callback);
        }
    }

    public void unregisterFragmentLifecycleCallbacks(FragmentLifecycleCallbacks callback) {
        synchronized (this.mFragmentLifecycleCallbacks) {
            this.mFragmentLifecycleCallbacks.remove(callback);
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchFragmentAttachedInner(Fragment fragment, Activity activity) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((FragmentLifecycleCallbacks) obj).onFragmentAttached(fragment, activity);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchFragmentCreatedInner(Fragment fragment, Bundle savedInstanceState) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((FragmentLifecycleCallbacks) obj).onFragmentCreated(fragment, savedInstanceState);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchFragmentStartedInner(Fragment fragment) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((FragmentLifecycleCallbacks) obj).onFragmentStarted(fragment);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchFragmentResumedInner(Fragment fragment) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((FragmentLifecycleCallbacks) obj).onFragmentResumed(fragment);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchFragmentPausedInner(Fragment fragment) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((FragmentLifecycleCallbacks) obj).onFragmentPaused(fragment);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchFragmentStoppedInner(Fragment fragment) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((FragmentLifecycleCallbacks) obj).onFragmentStopped(fragment);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchFragmentSaveInstanceStateInner(Fragment fragment, Bundle outState) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((FragmentLifecycleCallbacks) obj).onFragmentSaveInstanceState(fragment, outState);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchFragmentDestroyedInner(Fragment fragment) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((FragmentLifecycleCallbacks) obj).onFragmentDestroyed(fragment);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchFragmentDetachedInner(Fragment fragment) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((FragmentLifecycleCallbacks) obj).onFragmentDetached(fragment);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchFragmentOnActivityResultInner(Fragment fragment, int requestCode, int resultCode, Intent data) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((FragmentLifecycleCallbacks) obj).onActivityResult(fragment, requestCode, resultCode, data);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void dispatchFragmentActivityCreatedInner(Fragment fragment, Bundle bundle) {
        Object[] callbacks = collectFragmentLifecycleCallbacks();
        if (callbacks != null) {
            for (Object obj : callbacks) {
                ((FragmentLifecycleCallbacks) obj).onActivityCreated(fragment, bundle);
            }
        }
    }

    private Object[] collectFragmentLifecycleCallbacks() {
        Object[] callbacks = null;
        synchronized (this.mFragmentLifecycleCallbacks) {
            if (this.mFragmentLifecycleCallbacks.size() > 0) {
                callbacks = this.mFragmentLifecycleCallbacks.toArray();
            }
        }
        return callbacks;
    }

    public void onCreate() {
        super.onCreate();
    }
}
