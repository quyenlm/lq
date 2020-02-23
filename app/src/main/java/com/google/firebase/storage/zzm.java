package com.google.firebase.storage;

import android.support.annotation.NonNull;
import com.google.firebase.storage.StorageTask;

final class zzm implements zzaa<OnPausedListener<? super TResult>, TResult> {
    zzm(StorageTask storageTask) {
    }

    public final /* synthetic */ void zzi(@NonNull Object obj, @NonNull Object obj2) {
        ((OnPausedListener) obj).onPaused((StorageTask.ProvideError) obj2);
    }
}
