package com.tencent.smtt.sdk;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

class ah extends Handler {
    final /* synthetic */ ag a;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ah(ag agVar, Looper looper) {
        super(looper);
        this.a = agVar;
    }

    public void handleMessage(Message message) {
        if (message.what == 150) {
            boolean unused = this.a.n();
        }
    }
}
