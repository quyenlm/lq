package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

final class zzc<TResult, TContinuationResult> implements OnFailureListener, OnSuccessListener<TContinuationResult>, zzk<TResult> {
    private final Executor zzbEo;
    /* access modifiers changed from: private */
    public final Continuation<TResult, Task<TContinuationResult>> zzbLR;
    /* access modifiers changed from: private */
    public final zzn<TContinuationResult> zzbLS;

    public zzc(@NonNull Executor executor, @NonNull Continuation<TResult, Task<TContinuationResult>> continuation, @NonNull zzn<TContinuationResult> zzn) {
        this.zzbEo = executor;
        this.zzbLR = continuation;
        this.zzbLS = zzn;
    }

    public final void cancel() {
        throw new UnsupportedOperationException();
    }

    public final void onComplete(@NonNull Task<TResult> task) {
        this.zzbEo.execute(new zzd(this, task));
    }

    public final void onFailure(@NonNull Exception exc) {
        this.zzbLS.setException(exc);
    }

    public final void onSuccess(TContinuationResult tcontinuationresult) {
        this.zzbLS.setResult(tcontinuationresult);
    }
}
