package com.tencent.smtt.sdk;

class i implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ h b;

    i(h hVar, boolean z) {
        this.b = hVar;
        this.a = z;
    }

    public void run() {
        this.b.c.onReceiveValue(Boolean.valueOf(this.a));
    }
}
