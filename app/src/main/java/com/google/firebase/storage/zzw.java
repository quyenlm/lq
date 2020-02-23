package com.google.firebase.storage;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.zzbo;
import com.google.android.gms.internal.abr;
import com.google.android.gms.internal.aby;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.StorageTask.ProvideError;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;

final class zzw<TListenerType, TResult extends StorageTask.ProvideError> {
    private final Queue<TListenerType> zzcpA = new ConcurrentLinkedQueue();
    private final HashMap<TListenerType, aby> zzcpB = new HashMap<>();
    private StorageTask<TResult> zzcpC;
    private int zzcpD;
    /* access modifiers changed from: private */
    public zzaa<TListenerType, TResult> zzcpE;

    public zzw(@NonNull StorageTask<TResult> storageTask, int i, @NonNull zzaa<TListenerType, TResult> zzaa) {
        this.zzcpC = storageTask;
        this.zzcpD = i;
        this.zzcpE = zzaa;
    }

    public final void zzKZ() {
        if ((this.zzcpC.zzKR() & this.zzcpD) != 0) {
            TResult zzKS = this.zzcpC.zzKS();
            for (Object next : this.zzcpA) {
                aby aby = this.zzcpB.get(next);
                if (aby != null) {
                    aby.zzw(new zzz(this, next, zzKS));
                }
            }
        }
    }

    public final void zza(@Nullable Activity activity, @Nullable Executor executor, @NonNull TListenerType tlistenertype) {
        boolean z;
        aby aby;
        boolean z2 = true;
        zzbo.zzu(tlistenertype);
        synchronized (this.zzcpC.mSyncObject) {
            z = (this.zzcpC.zzKR() & this.zzcpD) != 0;
            this.zzcpA.add(tlistenertype);
            aby = new aby(executor);
            this.zzcpB.put(tlistenertype, aby);
            if (activity != null) {
                if (Build.VERSION.SDK_INT >= 17) {
                    if (activity.isDestroyed()) {
                        z2 = false;
                    }
                    zzbo.zzb(z2, (Object) "Activity is already destroyed!");
                }
                abr.zzLc().zza(activity, tlistenertype, new zzx(this, tlistenertype));
            }
        }
        if (z) {
            aby.zzw(new zzy(this, tlistenertype, this.zzcpC.zzKS()));
        }
    }

    public final void zzat(@NonNull TListenerType tlistenertype) {
        zzbo.zzu(tlistenertype);
        synchronized (this.zzcpC.mSyncObject) {
            this.zzcpB.remove(tlistenertype);
            this.zzcpA.remove(tlistenertype);
            abr.zzLc().zzau(tlistenertype);
        }
    }
}
