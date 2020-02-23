package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import com.google.android.gms.ads.internal.zzbs;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;

@zzzn
@TargetApi(14)
public final class zzge implements Application.ActivityLifecycleCallbacks, View.OnAttachStateChangeListener, ViewTreeObserver.OnGlobalLayoutListener, ViewTreeObserver.OnScrollChangedListener {
    private static final long zzxy = ((Long) zzbs.zzbL().zzd(zzmo.zzEr)).longValue();
    private final Context mApplicationContext;
    private final WindowManager zzwR;
    private final PowerManager zzwS;
    private final KeyguardManager zzwT;
    private zzair zzwx = new zzair(zzxy);
    private WeakReference<ViewTreeObserver> zzxA;
    private WeakReference<View> zzxB;
    private zzgj zzxC;
    private int zzxD = -1;
    private HashSet<zzgi> zzxE = new HashSet<>();
    private DisplayMetrics zzxF;
    private boolean zzxa = false;
    @Nullable
    private BroadcastReceiver zzxb;
    private Application zzxz;

    public zzge(Context context, View view) {
        this.mApplicationContext = context.getApplicationContext();
        this.zzwR = (WindowManager) context.getSystemService("window");
        this.zzwS = (PowerManager) this.mApplicationContext.getSystemService("power");
        this.zzwT = (KeyguardManager) context.getSystemService("keyguard");
        if (this.mApplicationContext instanceof Application) {
            this.zzxz = (Application) this.mApplicationContext;
            this.zzxC = new zzgj((Application) this.mApplicationContext, this);
        }
        this.zzxF = context.getResources().getDisplayMetrics();
        View view2 = this.zzxB != null ? (View) this.zzxB.get() : null;
        if (view2 != null) {
            view2.removeOnAttachStateChangeListener(this);
            zze(view2);
        }
        this.zzxB = new WeakReference<>(view);
        if (view != null) {
            if (zzbs.zzbB().isAttachedToWindow(view)) {
                zzd(view);
            }
            view.addOnAttachStateChangeListener(this);
        }
    }

    private final Rect zza(Rect rect) {
        return new Rect(zzi(rect.left), zzi(rect.top), zzi(rect.right), zzi(rect.bottom));
    }

    private final void zza(Activity activity, int i) {
        Window window;
        if (this.zzxB != null && (window = activity.getWindow()) != null) {
            View peekDecorView = window.peekDecorView();
            View view = (View) this.zzxB.get();
            if (view != null && peekDecorView != null && view.getRootView() == peekDecorView.getRootView()) {
                this.zzxD = i;
            }
        }
    }

    private final void zzcA() {
        zzbs.zzbz();
        zzagz.zzZr.post(new zzgf(this));
    }

    private final void zzd(View view) {
        ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            this.zzxA = new WeakReference<>(viewTreeObserver);
            viewTreeObserver.addOnScrollChangedListener(this);
            viewTreeObserver.addOnGlobalLayoutListener(this);
        }
        if (this.zzxb == null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            intentFilter.addAction("android.intent.action.USER_PRESENT");
            this.zzxb = new zzgg(this);
            this.mApplicationContext.registerReceiver(this.zzxb, intentFilter);
        }
        if (this.zzxz != null) {
            try {
                this.zzxz.registerActivityLifecycleCallbacks(this.zzxC);
            } catch (Exception e) {
                zzafr.zzb("Error registering activity lifecycle callbacks.", e);
            }
        }
    }

    private final void zze(View view) {
        try {
            if (this.zzxA != null) {
                ViewTreeObserver viewTreeObserver = (ViewTreeObserver) this.zzxA.get();
                if (viewTreeObserver != null && viewTreeObserver.isAlive()) {
                    viewTreeObserver.removeOnScrollChangedListener(this);
                    viewTreeObserver.removeGlobalOnLayoutListener(this);
                }
                this.zzxA = null;
            }
        } catch (Exception e) {
            zzafr.zzb("Error while unregistering listeners from the last ViewTreeObserver.", e);
        }
        try {
            ViewTreeObserver viewTreeObserver2 = view.getViewTreeObserver();
            if (viewTreeObserver2.isAlive()) {
                viewTreeObserver2.removeOnScrollChangedListener(this);
                viewTreeObserver2.removeGlobalOnLayoutListener(this);
            }
        } catch (Exception e2) {
            zzafr.zzb("Error while unregistering listeners from the ViewTreeObserver.", e2);
        }
        if (this.zzxb != null) {
            try {
                this.mApplicationContext.unregisterReceiver(this.zzxb);
            } catch (IllegalStateException e3) {
                zzafr.zzb("Failed trying to unregister the receiver", e3);
            } catch (Exception e4) {
                zzbs.zzbD().zza((Throwable) e4, "ActiveViewUnit.stopScreenStatusMonitoring");
            }
            this.zzxb = null;
        }
        if (this.zzxz != null) {
            try {
                this.zzxz.unregisterActivityLifecycleCallbacks(this.zzxC);
            } catch (Exception e5) {
                zzafr.zzb("Error registering activity lifecycle callbacks.", e5);
            }
        }
    }

    /* access modifiers changed from: private */
    public final void zzh(int i) {
        if (this.zzxE.size() != 0 && this.zzxB != null) {
            View view = (View) this.zzxB.get();
            boolean z = i == 1;
            boolean z2 = view == null;
            Rect rect = new Rect();
            Rect rect2 = new Rect();
            boolean z3 = false;
            Rect rect3 = new Rect();
            boolean z4 = false;
            Rect rect4 = new Rect();
            Rect rect5 = new Rect();
            rect5.right = this.zzwR.getDefaultDisplay().getWidth();
            rect5.bottom = this.zzwR.getDefaultDisplay().getHeight();
            int[] iArr = new int[2];
            int[] iArr2 = new int[2];
            if (view != null) {
                z3 = view.getGlobalVisibleRect(rect2);
                z4 = view.getLocalVisibleRect(rect3);
                view.getHitRect(rect4);
                try {
                    view.getLocationOnScreen(iArr);
                    view.getLocationInWindow(iArr2);
                } catch (Exception e) {
                    zzafr.zzb("Failure getting view location.", e);
                }
                rect.left = iArr[0];
                rect.top = iArr[1];
                rect.right = rect.left + view.getWidth();
                rect.bottom = rect.top + view.getHeight();
            }
            int windowVisibility = view != null ? view.getWindowVisibility() : 8;
            if (this.zzxD != -1) {
                windowVisibility = this.zzxD;
            }
            boolean z5 = !z2 && zzbs.zzbz().zza(view, this.zzwS, this.zzwT) && z3 && z4 && windowVisibility == 0;
            if (z && !this.zzwx.tryAcquire() && z5 == this.zzxa) {
                return;
            }
            if (z5 || this.zzxa || i != 1) {
                zzgh zzgh = new zzgh(zzbs.zzbF().elapsedRealtime(), this.zzwS.isScreenOn(), view != null ? zzbs.zzbB().isAttachedToWindow(view) : false, view != null ? view.getWindowVisibility() : 8, zza(rect5), zza(rect), zza(rect2), z3, zza(rect3), z4, zza(rect4), this.zzxF.density, z5);
                Iterator<zzgi> it = this.zzxE.iterator();
                while (it.hasNext()) {
                    it.next().zza(zzgh);
                }
                this.zzxa = z5;
            }
        }
    }

    private final int zzi(int i) {
        return (int) (((float) i) / this.zzxF.density);
    }

    public final void onActivityCreated(Activity activity, Bundle bundle) {
        zza(activity, 0);
        zzh(3);
        zzcA();
    }

    public final void onActivityDestroyed(Activity activity) {
        zzh(3);
        zzcA();
    }

    public final void onActivityPaused(Activity activity) {
        zza(activity, 4);
        zzh(3);
        zzcA();
    }

    public final void onActivityResumed(Activity activity) {
        zza(activity, 0);
        zzh(3);
        zzcA();
    }

    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        zzh(3);
        zzcA();
    }

    public final void onActivityStarted(Activity activity) {
        zza(activity, 0);
        zzh(3);
        zzcA();
    }

    public final void onActivityStopped(Activity activity) {
        zzh(3);
        zzcA();
    }

    public final void onGlobalLayout() {
        zzh(2);
        zzcA();
    }

    public final void onScrollChanged() {
        zzh(1);
    }

    public final void onViewAttachedToWindow(View view) {
        this.zzxD = -1;
        zzd(view);
        zzh(3);
    }

    public final void onViewDetachedFromWindow(View view) {
        this.zzxD = -1;
        zzh(3);
        zzcA();
        zze(view);
    }

    public final void zza(zzgi zzgi) {
        this.zzxE.add(zzgi);
        zzh(3);
    }

    public final void zzb(zzgi zzgi) {
        this.zzxE.remove(zzgi);
    }

    public final void zzcB() {
        zzh(4);
    }
}
