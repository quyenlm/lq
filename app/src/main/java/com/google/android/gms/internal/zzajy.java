package com.google.android.gms.internal;

import android.view.View;
import android.view.ViewTreeObserver;
import java.lang.ref.WeakReference;

@zzzn
abstract class zzajy {
    private final WeakReference<View> zzabl;

    public zzajy(View view) {
        this.zzabl = new WeakReference<>(view);
    }

    private final ViewTreeObserver getViewTreeObserver() {
        View view = (View) this.zzabl.get();
        if (view == null) {
            return null;
        }
        ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        if (viewTreeObserver == null || !viewTreeObserver.isAlive()) {
            return null;
        }
        return viewTreeObserver;
    }

    public final void detach() {
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver != null) {
            zzb(viewTreeObserver);
        }
    }

    /* access modifiers changed from: protected */
    public abstract void zza(ViewTreeObserver viewTreeObserver);

    /* access modifiers changed from: protected */
    public abstract void zzb(ViewTreeObserver viewTreeObserver);

    public final void zzio() {
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver != null) {
            zza(viewTreeObserver);
        }
    }
}
