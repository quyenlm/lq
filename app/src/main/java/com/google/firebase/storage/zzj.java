package com.google.firebase.storage;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.storage.StorageTask;

final class zzj implements zzaa<OnFailureListener, TResult> {
    private /* synthetic */ StorageTask zzcoZ;

    zzj(StorageTask storageTask) {
        this.zzcoZ = storageTask;
    }

    public final /* synthetic */ void zzi(@NonNull Object obj, @NonNull Object obj2) {
        zzs.zzKV().zzc(this.zzcoZ);
        ((OnFailureListener) obj).onFailure(((StorageTask.ProvideError) obj2).getError());
    }
}
