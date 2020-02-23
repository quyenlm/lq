package com.google.firebase.storage;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzn implements OnCompleteListener<TResult> {
    private /* synthetic */ TaskCompletionSource zzaIk;
    private /* synthetic */ StorageTask zzcoZ;
    private /* synthetic */ Continuation zzcpa;

    zzn(StorageTask storageTask, Continuation continuation, TaskCompletionSource taskCompletionSource) {
        this.zzcoZ = storageTask;
        this.zzcpa = continuation;
        this.zzaIk = taskCompletionSource;
    }

    public final void onComplete(@NonNull Task<TResult> task) {
        try {
            Object then = this.zzcpa.then(this.zzcoZ);
            if (!this.zzaIk.getTask().isComplete()) {
                this.zzaIk.setResult(then);
            }
        } catch (RuntimeExecutionException e) {
            if (e.getCause() instanceof Exception) {
                this.zzaIk.setException((Exception) e.getCause());
            } else {
                this.zzaIk.setException(e);
            }
        } catch (Exception e2) {
            this.zzaIk.setException(e2);
        }
    }
}
