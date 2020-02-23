package com.tencent.msdk.stat;

import com.tencent.msdk.stat.a.d;

class an implements Runnable {
    final /* synthetic */ d a;
    final /* synthetic */ j b;
    final /* synthetic */ boolean c;
    final /* synthetic */ boolean d;
    final /* synthetic */ aj e;

    an(aj ajVar, d dVar, j jVar, boolean z, boolean z2) {
        this.e = ajVar;
        this.a = dVar;
        this.b = jVar;
        this.c = z;
        this.d = z2;
    }

    public void run() {
        this.e.b(this.a, this.b, this.c, this.d);
    }
}
