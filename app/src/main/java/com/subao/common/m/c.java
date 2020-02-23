package com.subao.common.m;

import android.support.annotation.NonNull;
import java.util.ArrayDeque;
import java.util.concurrent.Executor;

/* compiled from: SerialExecutor */
public class c implements Executor {
    private final ArrayDeque<Runnable> a = new ArrayDeque<>();
    private Runnable b;

    public void execute(@NonNull final Runnable runnable) {
        synchronized (this) {
            this.a.offer(new Runnable() {
                public void run() {
                    try {
                        runnable.run();
                    } finally {
                        c.this.a();
                    }
                }
            });
            if (this.b == null) {
                a();
            }
        }
    }

    /* access modifiers changed from: private */
    public void a() {
        synchronized (this) {
            Runnable poll = this.a.poll();
            this.b = poll;
            if (poll != null) {
                d.a().execute(this.b);
            }
        }
    }
}
