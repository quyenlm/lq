package com.google.android.gms.internal;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

final class yv extends ScheduledThreadPoolExecutor {
    private /* synthetic */ yu zzcjv;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    yv(yu yuVar, int i, ThreadFactory threadFactory) {
        super(1, threadFactory);
        this.zzcjv = yuVar;
    }

    /* access modifiers changed from: protected */
    public final void afterExecute(Runnable runnable, Throwable th) {
        super.afterExecute(runnable, th);
        if (th == null && (runnable instanceof Future)) {
            Future future = (Future) runnable;
            try {
                if (future.isDone()) {
                    future.get();
                }
            } catch (CancellationException e) {
            } catch (ExecutionException e2) {
                th = e2.getCause();
            } catch (InterruptedException e3) {
                Thread.currentThread().interrupt();
            }
        }
        if (th != null) {
            this.zzcjv.zzf(th);
        }
    }
}
