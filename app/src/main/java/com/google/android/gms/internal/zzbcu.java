package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

final class zzbcu extends Handler {
    private /* synthetic */ zzbcp zzaDN;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbcu(zzbcp zzbcp, Looper looper) {
        super(looper);
        this.zzaDN = zzbcp;
    }

    public final void handleMessage(Message message) {
        switch (message.what) {
            case 1:
                this.zzaDN.zzqd();
                return;
            case 2:
                this.zzaDN.resume();
                return;
            default:
                Log.w("GoogleApiClientImpl", new StringBuilder(31).append("Unknown message id: ").append(message.what).toString());
                return;
        }
    }
}
