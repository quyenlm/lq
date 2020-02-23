package com.tencent.msdk.stat;

class aq implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ aj b;

    aq(aj ajVar, int i) {
        this.b = ajVar;
        this.a = i;
    }

    public void run() {
        this.b.b(this.a, true);
        this.b.b(this.a, false);
    }
}
