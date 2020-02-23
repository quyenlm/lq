package com.tencent.msdk.stat;

import java.util.List;

class al implements Runnable {
    final /* synthetic */ List a;
    final /* synthetic */ boolean b;
    final /* synthetic */ boolean c;
    final /* synthetic */ aj d;

    al(aj ajVar, List list, boolean z, boolean z2) {
        this.d = ajVar;
        this.a = list;
        this.b = z;
        this.c = z2;
    }

    public void run() {
        this.d.a((List<as>) this.a, this.b);
        if (this.c) {
            this.a.clear();
        }
    }
}
