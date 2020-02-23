package com.subao.common.m;

import android.os.Handler;
import android.os.Looper;

/* compiled from: MainHandler */
public class b implements a {
    private static final b a = new b();
    private final Handler b = new Handler(Looper.getMainLooper());

    private b() {
    }

    public static a a() {
        return a;
    }

    public boolean a(Runnable runnable) {
        return this.b.post(runnable);
    }

    public boolean a(Runnable runnable, long j) {
        return this.b.postDelayed(runnable, j);
    }

    public void b(Runnable runnable) {
        this.b.removeCallbacks(runnable);
    }
}
