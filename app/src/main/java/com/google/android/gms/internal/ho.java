package com.google.android.gms.internal;

import android.os.Build;
import android.os.Looper;

final class ho extends ThreadLocal<hn> {
    ho() {
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Object initialValue() {
        if (Build.VERSION.SDK_INT >= 16) {
            return new ht();
        }
        Looper myLooper = Looper.myLooper();
        if (myLooper != null) {
            return new hs(myLooper);
        }
        throw new IllegalStateException("The current thread must have a looper!");
    }
}
