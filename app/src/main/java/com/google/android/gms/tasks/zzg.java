package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

final class zzg<TResult> implements zzk<TResult> {
    /* access modifiers changed from: private */
    public final Object mLock = new Object();
    private final Executor zzbEo;
    /* access modifiers changed from: private */
    public OnFailureListener zzbLY;

    public zzg(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        this.zzbEo = executor;
        this.zzbLY = onFailureListener;
    }

    public final void cancel() {
        synchronized (this.mLock) {
            this.zzbLY = null;
        }
    }

    public final void onComplete(@NonNull Task<TResult> task) {
        if (!task.isSuccessful()) {
            synchronized (this.mLock) {
                if (this.zzbLY != null) {
                    this.zzbEo.execute(new zzh(this, task));
                }
            }
        }
    }
}
