package com.tencent.mna.b.a;

import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: AccelerateState */
public class c {
    static volatile boolean a = false;
    static volatile int b = 0;
    static volatile boolean c = true;
    static volatile boolean d = false;
    static volatile int e = -100;
    static final AtomicInteger f = new AtomicInteger(0);
    private static volatile boolean g = false;
    private static volatile boolean h = false;

    static synchronized boolean a() {
        boolean z;
        synchronized (c.class) {
            z = g;
        }
        return z;
    }

    static synchronized void a(boolean z) {
        synchronized (c.class) {
            g = z;
        }
    }

    static synchronized boolean b() {
        boolean z;
        synchronized (c.class) {
            z = h;
        }
        return z;
    }

    static synchronized void b(boolean z) {
        synchronized (c.class) {
            h = z;
        }
    }

    public static void c() {
        a = false;
        b = 0;
        c = true;
        d = false;
        e = -100;
        f.set(0);
    }
}
