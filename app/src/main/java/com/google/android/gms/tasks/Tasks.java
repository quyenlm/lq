package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.zzbo;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class Tasks {

    static final class zza implements zzb {
        private final CountDownLatch zztJ;

        private zza() {
            this.zztJ = new CountDownLatch(1);
        }

        /* synthetic */ zza(zzo zzo) {
            this();
        }

        public final void await() throws InterruptedException {
            this.zztJ.await();
        }

        public final boolean await(long j, TimeUnit timeUnit) throws InterruptedException {
            return this.zztJ.await(j, timeUnit);
        }

        public final void onFailure(@NonNull Exception exc) {
            this.zztJ.countDown();
        }

        public final void onSuccess(Object obj) {
            this.zztJ.countDown();
        }
    }

    interface zzb extends OnFailureListener, OnSuccessListener<Object> {
    }

    static final class zzc implements zzb {
        private final Object mLock = new Object();
        private final zzn<Void> zzbMe;
        private Exception zzbMj;
        private final int zzbMl;
        private int zzbMm;
        private int zzbMn;

        public zzc(int i, zzn<Void> zzn) {
            this.zzbMl = i;
            this.zzbMe = zzn;
        }

        private final void zzDJ() {
            if (this.zzbMm + this.zzbMn != this.zzbMl) {
                return;
            }
            if (this.zzbMj == null) {
                this.zzbMe.setResult(null);
                return;
            }
            zzn<Void> zzn = this.zzbMe;
            int i = this.zzbMn;
            zzn.setException(new ExecutionException(new StringBuilder(54).append(i).append(" out of ").append(this.zzbMl).append(" underlying tasks failed").toString(), this.zzbMj));
        }

        public final void onFailure(@NonNull Exception exc) {
            synchronized (this.mLock) {
                this.zzbMn++;
                this.zzbMj = exc;
                zzDJ();
            }
        }

        public final void onSuccess(Object obj) {
            synchronized (this.mLock) {
                this.zzbMm++;
                zzDJ();
            }
        }
    }

    private Tasks() {
    }

    public static <TResult> TResult await(@NonNull Task<TResult> task) throws ExecutionException, InterruptedException {
        zzbo.zzcG("Must not be called on the main application thread");
        zzbo.zzb(task, (Object) "Task must not be null");
        if (task.isComplete()) {
            return zzb(task);
        }
        zza zza2 = new zza((zzo) null);
        zza(task, zza2);
        zza2.await();
        return zzb(task);
    }

    public static <TResult> TResult await(@NonNull Task<TResult> task, long j, @NonNull TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        zzbo.zzcG("Must not be called on the main application thread");
        zzbo.zzb(task, (Object) "Task must not be null");
        zzbo.zzb(timeUnit, (Object) "TimeUnit must not be null");
        if (task.isComplete()) {
            return zzb(task);
        }
        zza zza2 = new zza((zzo) null);
        zza(task, zza2);
        if (zza2.await(j, timeUnit)) {
            return zzb(task);
        }
        throw new TimeoutException("Timed out waiting for Task");
    }

    public static <TResult> Task<TResult> call(@NonNull Callable<TResult> callable) {
        return call(TaskExecutors.MAIN_THREAD, callable);
    }

    public static <TResult> Task<TResult> call(@NonNull Executor executor, @NonNull Callable<TResult> callable) {
        zzbo.zzb(executor, (Object) "Executor must not be null");
        zzbo.zzb(callable, (Object) "Callback must not be null");
        zzn zzn = new zzn();
        executor.execute(new zzo(zzn, callable));
        return zzn;
    }

    public static <TResult> Task<TResult> forException(@NonNull Exception exc) {
        zzn zzn = new zzn();
        zzn.setException(exc);
        return zzn;
    }

    public static <TResult> Task<TResult> forResult(TResult tresult) {
        zzn zzn = new zzn();
        zzn.setResult(tresult);
        return zzn;
    }

    public static Task<Void> whenAll(Collection<? extends Task<?>> collection) {
        if (collection.isEmpty()) {
            return forResult((Object) null);
        }
        for (Task task : collection) {
            if (task == null) {
                throw new NullPointerException("null tasks are not accepted");
            }
        }
        zzn zzn = new zzn();
        zzc zzc2 = new zzc(collection.size(), zzn);
        for (Task zza2 : collection) {
            zza(zza2, zzc2);
        }
        return zzn;
    }

    public static Task<Void> whenAll(Task<?>... taskArr) {
        return taskArr.length == 0 ? forResult((Object) null) : whenAll((Collection<? extends Task<?>>) Arrays.asList(taskArr));
    }

    private static void zza(Task<?> task, zzb zzb2) {
        task.addOnSuccessListener(TaskExecutors.zzbMf, (OnSuccessListener<? super Object>) zzb2);
        task.addOnFailureListener(TaskExecutors.zzbMf, (OnFailureListener) zzb2);
    }

    private static <TResult> TResult zzb(Task<TResult> task) throws ExecutionException {
        if (task.isSuccessful()) {
            return task.getResult();
        }
        throw new ExecutionException(task.getException());
    }
}
