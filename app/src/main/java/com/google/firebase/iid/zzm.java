package com.google.firebase.iid;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

final class zzm extends Handler {
    private /* synthetic */ zzl zzckJ;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzm(zzl zzl, Looper looper) {
        super(looper);
        this.zzckJ = zzl;
    }

    public final void handleMessage(Message message) {
        this.zzckJ.zzc(message);
    }
}
