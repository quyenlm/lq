package com.google.firebase.storage;

import android.support.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;

final class zzk implements zzaa<OnCompleteListener<TResult>, TResult> {
    private /* synthetic */ StorageTask zzcoZ;

    zzk(StorageTask storageTask) {
        this.zzcoZ = storageTask;
    }

    public final /* synthetic */ void zzi(@NonNull Object obj, @NonNull Object obj2) {
        zzs.zzKV().zzc(this.zzcoZ);
        ((OnCompleteListener) obj).onComplete(this.zzcoZ);
    }
}
