package com.subao.common.m;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* compiled from: ThreadPool */
public class d {
    private static Executor a;

    private d() {
    }

    public static Executor a() {
        Executor executor = a;
        if (executor == null) {
            synchronized (d.class) {
                if (a == null) {
                    a = Executors.newCachedThreadPool();
                }
                executor = a;
            }
        }
        return executor;
    }

    public static void a(Runnable runnable) {
        a().execute(runnable);
    }
}
