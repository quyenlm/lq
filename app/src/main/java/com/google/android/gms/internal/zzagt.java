package com.google.android.gms.internal;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@zzzn
public final class zzagt {
    private static final ThreadPoolExecutor zzZk = new ThreadPoolExecutor(20, 20, 1, TimeUnit.MINUTES, new LinkedBlockingQueue(), zzaH("Default"));
    private static final ThreadPoolExecutor zzZl = new ThreadPoolExecutor(5, 5, 1, TimeUnit.MINUTES, new LinkedBlockingQueue(), zzaH("Loader"));

    static {
        zzZk.allowCoreThreadTimeOut(true);
        zzZl.allowCoreThreadTimeOut(true);
    }

    public static zzajm<Void> zza(int i, Runnable runnable) {
        return i == 1 ? zza((ExecutorService) zzZl, new zzagu(runnable)) : zza((ExecutorService) zzZk, new zzagv(runnable));
    }

    public static zzajm<Void> zza(Runnable runnable) {
        return zza(0, runnable);
    }

    public static <T> zzajm<T> zza(Callable<T> callable) {
        return zza((ExecutorService) zzZk, callable);
    }

    public static <T> zzajm<T> zza(ExecutorService executorService, Callable<T> callable) {
        zzajg zzajg = new zzajg();
        try {
            zzajg.zzd(new zzagx(zzajg, executorService.submit(new zzagw(zzajg, callable))));
        } catch (RejectedExecutionException e) {
            zzafr.zzc("Thread execution is rejected.", e);
            zzajg.cancel(true);
        }
        return zzajg;
    }

    private static ThreadFactory zzaH(String str) {
        return new zzagy(str);
    }
}
