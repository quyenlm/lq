package com.google.android.gms.internal;

import android.view.ViewTreeObserver;
import java.lang.ref.WeakReference;

final class zzze implements ViewTreeObserver.OnScrollChangedListener {
    private /* synthetic */ zzyx zzSb;
    private /* synthetic */ WeakReference zzSd;

    zzze(zzyx zzyx, WeakReference weakReference) {
        this.zzSb = zzyx;
        this.zzSd = weakReference;
    }

    public final void onScrollChanged() {
        this.zzSb.zza((WeakReference<zzaka>) this.zzSd, true);
    }
}
