package com.tencent.mna.base.utils;

import android.util.Log;

/* compiled from: Logger */
public final class h {
    public static int a = 0;
    private static String b = "MNA";
    private static a c = null;

    /* compiled from: Logger */
    public interface a {
        void a(String str);
    }

    public static void a(int i) {
        if (i < 0 || i > 8) {
            a = 0;
        } else {
            a = i;
        }
    }

    public static int a() {
        return a;
    }

    public static void a(String str) {
        if (a > 0 && a <= 3) {
            Log.d(b, str);
        }
    }

    public static void b(String str) {
        if (a > 0 && a <= 4) {
            Log.i(b, str);
        }
    }

    public static void c(String str) {
        if (a > 0 && a <= 5) {
            Log.w(b, str);
        }
    }

    public static void d(String str) {
        if (a > 0 && a <= 6) {
            Log.e(b, str);
        }
    }

    public static void e(String str) {
        if (c != null) {
            c.a(str);
        }
    }
}
