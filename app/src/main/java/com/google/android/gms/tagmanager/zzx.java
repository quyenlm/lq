package com.google.android.gms.tagmanager;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.tagmanager.ContainerHolder;

final class zzx extends Handler {
    private final ContainerHolder.ContainerAvailableListener zzbDJ;
    private /* synthetic */ zzv zzbDK;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public zzx(zzv zzv, ContainerHolder.ContainerAvailableListener containerAvailableListener, Looper looper) {
        super(looper);
        this.zzbDK = zzv;
        this.zzbDJ = containerAvailableListener;
    }

    public final void handleMessage(Message message) {
        switch (message.what) {
            case 1:
                this.zzbDJ.onContainerAvailable(this.zzbDK, (String) message.obj);
                return;
            default:
                zzdj.e("Don't know how to handle this message.");
                return;
        }
    }
}
