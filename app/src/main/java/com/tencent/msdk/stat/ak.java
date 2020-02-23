package com.tencent.msdk.stat;

import java.util.List;

class ak implements Runnable {
    final /* synthetic */ List a;
    final /* synthetic */ int b;
    final /* synthetic */ boolean c;
    final /* synthetic */ boolean d;
    final /* synthetic */ aj e;

    ak(aj ajVar, List list, int i, boolean z, boolean z2) {
        this.e = ajVar;
        this.a = list;
        this.b = i;
        this.c = z;
        this.d = z2;
    }

    public void run() {
        this.e.a((List<as>) this.a, this.b, this.c);
        if (this.d) {
            this.a.clear();
        }
    }
}
