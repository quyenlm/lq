package com.google.android.gms.tasks;

import android.app.Activity;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.zzbds;
import com.google.android.gms.internal.zzbdt;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

final class zzn<TResult> extends Task<TResult> {
    private final Object mLock = new Object();
    private final zzl<TResult> zzbMg = new zzl<>();
    private boolean zzbMh;
    private TResult zzbMi;
    private Exception zzbMj;

    static class zza extends zzbds {
        private final List<WeakReference<zzk<?>>> mListeners = new ArrayList();

        private zza(zzbdt zzbdt) {
            super(zzbdt);
            this.zzaEG.zza("TaskOnStopCallback", (zzbds) this);
        }

        public static zza zzr(Activity activity) {
            zzbdt zzn = zzn(activity);
            zza zza = (zza) zzn.zza("TaskOnStopCallback", zza.class);
            return zza == null ? new zza(zzn) : zza;
        }

        @MainThread
        public final void onStop() {
            synchronized (this.mListeners) {
                for (WeakReference<zzk<?>> weakReference : this.mListeners) {
                    zzk zzk = (zzk) weakReference.get();
                    if (zzk != null) {
                        zzk.cancel();
                    }
                }
                this.mListeners.clear();
            }
        }

        public final <T> void zzb(zzk<T> zzk) {
            synchronized (this.mListeners) {
                this.mListeners.add(new WeakReference(zzk));
            }
        }
    }

    zzn() {
    }

    private final void zzDG() {
        zzbo.zza(this.zzbMh, (Object) "Task is not yet complete");
    }

    private final void zzDH() {
        zzbo.zza(!this.zzbMh, (Object) "Task is already complete");
    }

    private final void zzDI() {
        synchronized (this.mLock) {
            if (this.zzbMh) {
                this.zzbMg.zza(this);
            }
        }
    }

    @NonNull
    public final Task<TResult> addOnCompleteListener(@NonNull Activity activity, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        zze zze = new zze(TaskExecutors.MAIN_THREAD, onCompleteListener);
        this.zzbMg.zza(zze);
        zza.zzr(activity).zzb(zze);
        zzDI();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnCompleteListener(@NonNull OnCompleteListener<TResult> onCompleteListener) {
        return addOnCompleteListener(TaskExecutors.MAIN_THREAD, onCompleteListener);
    }

    @NonNull
    public final Task<TResult> addOnCompleteListener(@NonNull Executor executor, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        this.zzbMg.zza(new zze(executor, onCompleteListener));
        zzDI();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnFailureListener(@NonNull Activity activity, @NonNull OnFailureListener onFailureListener) {
        zzg zzg = new zzg(TaskExecutors.MAIN_THREAD, onFailureListener);
        this.zzbMg.zza(zzg);
        zza.zzr(activity).zzb(zzg);
        zzDI();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnFailureListener(@NonNull OnFailureListener onFailureListener) {
        return addOnFailureListener(TaskExecutors.MAIN_THREAD, onFailureListener);
    }

    @NonNull
    public final Task<TResult> addOnFailureListener(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        this.zzbMg.zza(new zzg(executor, onFailureListener));
        zzDI();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnSuccessListener(@NonNull Activity activity, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        zzi zzi = new zzi(TaskExecutors.MAIN_THREAD, onSuccessListener);
        this.zzbMg.zza(zzi);
        zza.zzr(activity).zzb(zzi);
        zzDI();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnSuccessListener(@NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        return addOnSuccessListener(TaskExecutors.MAIN_THREAD, onSuccessListener);
    }

    @NonNull
    public final Task<TResult> addOnSuccessListener(@NonNull Executor executor, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        this.zzbMg.zza(new zzi(executor, onSuccessListener));
        zzDI();
        return this;
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWith(@NonNull Continuation<TResult, TContinuationResult> continuation) {
        return continueWith(TaskExecutors.MAIN_THREAD, continuation);
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWith(@NonNull Executor executor, @NonNull Continuation<TResult, TContinuationResult> continuation) {
        zzn zzn = new zzn();
        this.zzbMg.zza(new zza(executor, continuation, zzn));
        zzDI();
        return zzn;
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWithTask(@NonNull Continuation<TResult, Task<TContinuationResult>> continuation) {
        return continueWithTask(TaskExecutors.MAIN_THREAD, continuation);
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWithTask(@NonNull Executor executor, @NonNull Continuation<TResult, Task<TContinuationResult>> continuation) {
        zzn zzn = new zzn();
        this.zzbMg.zza(new zzc(executor, continuation, zzn));
        zzDI();
        return zzn;
    }

    @Nullable
    public final Exception getException() {
        Exception exc;
        synchronized (this.mLock) {
            exc = this.zzbMj;
        }
        return exc;
    }

    public final TResult getResult() {
        TResult tresult;
        synchronized (this.mLock) {
            zzDG();
            if (this.zzbMj != null) {
                throw new RuntimeExecutionException(this.zzbMj);
            }
            tresult = this.zzbMi;
        }
        return tresult;
    }

    public final <X extends Throwable> TResult getResult(@NonNull Class<X> cls) throws Throwable {
        TResult tresult;
        synchronized (this.mLock) {
            zzDG();
            if (cls.isInstance(this.zzbMj)) {
                throw ((Throwable) cls.cast(this.zzbMj));
            } else if (this.zzbMj != null) {
                throw new RuntimeExecutionException(this.zzbMj);
            } else {
                tresult = this.zzbMi;
            }
        }
        return tresult;
    }

    public final boolean isComplete() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzbMh;
        }
        return z;
    }

    public final boolean isSuccessful() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzbMh && this.zzbMj == null;
        }
        return z;
    }

    public final void setException(@NonNull Exception exc) {
        zzbo.zzb(exc, (Object) "Exception must not be null");
        synchronized (this.mLock) {
            zzDH();
            this.zzbMh = true;
            this.zzbMj = exc;
        }
        this.zzbMg.zza(this);
    }

    public final void setResult(TResult tresult) {
        synchronized (this.mLock) {
            zzDH();
            this.zzbMh = true;
            this.zzbMi = tresult;
        }
        this.zzbMg.zza(this);
    }

    public final boolean trySetException(@NonNull Exception exc) {
        boolean z = true;
        zzbo.zzb(exc, (Object) "Exception must not be null");
        synchronized (this.mLock) {
            if (this.zzbMh) {
                z = false;
            } else {
                this.zzbMh = true;
                this.zzbMj = exc;
                this.zzbMg.zza(this);
            }
        }
        return z;
    }

    public final boolean trySetResult(TResult tresult) {
        boolean z = true;
        synchronized (this.mLock) {
            if (this.zzbMh) {
                z = false;
            } else {
                this.zzbMh = true;
                this.zzbMi = tresult;
                this.zzbMg.zza(this);
            }
        }
        return z;
    }
}
