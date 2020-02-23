package com.tencent.tp.a;

import android.os.Handler;
import android.os.Message;
import com.tencent.tp.a.ai;

class aj extends Handler {
    private int a;
    private ai.a b;

    public aj(int i, ai.a aVar) {
        this.a = i;
        this.b = aVar;
    }

    public void handleMessage(Message message) {
        this.b.a(this.a);
    }
}
