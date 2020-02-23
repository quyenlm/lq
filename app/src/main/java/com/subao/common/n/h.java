package com.subao.common.n;

import android.os.Looper;

/* compiled from: ThreadUtils */
public class h {
    private static long a = -1;

    public static long a() {
        if (a < 0) {
            a = Looper.getMainLooper().getThread().getId();
        }
        return a;
    }

    public static boolean b() {
        return Thread.currentThread().getId() == a();
    }
}
