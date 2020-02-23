package com.google.firebase.storage;

import com.google.firebase.storage.StorageTask;

final class zzz implements Runnable {
    private /* synthetic */ zzw zzcpG;
    private /* synthetic */ StorageTask.ProvideError zzcpH;
    private /* synthetic */ Object zzcpI;

    zzz(zzw zzw, Object obj, StorageTask.ProvideError provideError) {
        this.zzcpG = zzw;
        this.zzcpI = obj;
        this.zzcpH = provideError;
    }

    public final void run() {
        this.zzcpG.zzcpE.zzi(this.zzcpI, this.zzcpH);
    }
}
