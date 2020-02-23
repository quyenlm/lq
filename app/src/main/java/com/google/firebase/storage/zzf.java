package com.google.firebase.storage;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzf implements OnFailureListener {
    private /* synthetic */ TaskCompletionSource zzcoP;

    zzf(StorageReference storageReference, TaskCompletionSource taskCompletionSource) {
        this.zzcoP = taskCompletionSource;
    }

    public final void onFailure(@NonNull Exception exc) {
        this.zzcoP.setException(StorageException.fromExceptionAndHttpCode(exc, 0));
    }
}
