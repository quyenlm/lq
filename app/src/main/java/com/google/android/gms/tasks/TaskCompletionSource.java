package com.google.android.gms.tasks;

import android.support.annotation.NonNull;

public class TaskCompletionSource<TResult> {
    private final zzn<TResult> zzbMe = new zzn<>();

    @NonNull
    public Task<TResult> getTask() {
        return this.zzbMe;
    }

    public void setException(@NonNull Exception exc) {
        this.zzbMe.setException(exc);
    }

    public void setResult(TResult tresult) {
        this.zzbMe.setResult(tresult);
    }

    public boolean trySetException(@NonNull Exception exc) {
        return this.zzbMe.trySetException(exc);
    }

    public boolean trySetResult(TResult tresult) {
        return this.zzbMe.trySetResult(tresult);
    }
}
