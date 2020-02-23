package com.tencent.mna;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/* compiled from: MnaScheduler */
public class a {
    private static Handler a;
    private static Handler b;
    private static Handler c;
    private static Handler d;
    private static volatile Handler e;
    private static HandlerThread f;
    private static HandlerThread g;
    private static HandlerThread h;
    private static HandlerThread i;
    private static boolean j = false;

    public static synchronized boolean a(boolean z) {
        synchronized (a.class) {
            if (!j) {
                if (f == null) {
                    f = new HandlerThread("mna-bg");
                    f.start();
                }
                if (g == null) {
                    g = new HandlerThread("mna-kartin");
                    g.start();
                }
                a = new Handler(f.getLooper());
                c = new Handler(g.getLooper());
                b = new Handler(Looper.getMainLooper());
                if (z) {
                    if (h == null) {
                        h = new HandlerThread("mna-battery");
                        h.start();
                    }
                    d = new Handler(h.getLooper());
                }
                j = true;
            }
        }
        return true;
    }

    public static synchronized boolean a() {
        boolean z;
        synchronized (a.class) {
            z = j;
        }
        return z;
    }

    public static void a(Runnable runnable) {
        if (a != null) {
            a.post(f(runnable));
        }
    }

    public static void b(Runnable runnable) {
        if (b != null) {
            b.post(f(runnable));
        }
    }

    public static void c(Runnable runnable) {
        if (c != null) {
            c.post(f(runnable));
        }
    }

    public static void d(Runnable runnable) {
        if (d != null) {
            d.post(f(runnable));
        }
    }

    public static void e(Runnable runnable) {
        if (e == null) {
            synchronized (a.class) {
                if (e == null) {
                    if (i == null) {
                        i = new HandlerThread("mna-ping-upload");
                        i.start();
                    }
                    e = new Handler(i.getLooper());
                }
            }
        }
        e.post(f(runnable));
    }

    private static Runnable f(final Runnable runnable) {
        return new Runnable() {
            public void run() {
                try {
                    runnable.run();
                } catch (Throwable th) {
                }
            }
        };
    }
}
