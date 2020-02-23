package com.google.android.gms.iid;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

final class zzf extends Handler {
    private /* synthetic */ zze zzbhn;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzf(zze zze, Looper looper) {
        super(looper);
        this.zzbhn = zze;
    }

    public final void handleMessage(Message message) {
        this.zzbhn.zzc(message);
    }
}
