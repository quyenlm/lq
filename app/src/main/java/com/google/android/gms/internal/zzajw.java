package com.google.android.gms.internal;

import android.view.View;
import android.view.ViewTreeObserver;
import com.google.android.gms.ads.internal.zzbs;
import java.lang.ref.WeakReference;

@zzzn
final class zzajw extends zzajy implements ViewTreeObserver.OnGlobalLayoutListener {
    private final WeakReference<ViewTreeObserver.OnGlobalLayoutListener> zzabk;

    public zzajw(View view, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        super(view);
        this.zzabk = new WeakReference<>(onGlobalLayoutListener);
    }

    public final void onGlobalLayout() {
        ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = (ViewTreeObserver.OnGlobalLayoutListener) this.zzabk.get();
        if (onGlobalLayoutListener != null) {
            onGlobalLayoutListener.onGlobalLayout();
        } else {
            detach();
        }
    }

    /* access modifiers changed from: protected */
    public final void zza(ViewTreeObserver viewTreeObserver) {
        viewTreeObserver.addOnGlobalLayoutListener(this);
    }

    /* access modifiers changed from: protected */
    public final void zzb(ViewTreeObserver viewTreeObserver) {
        zzbs.zzbB().zza(viewTreeObserver, (ViewTreeObserver.OnGlobalLayoutListener) this);
    }
}
