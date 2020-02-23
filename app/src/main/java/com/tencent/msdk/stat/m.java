package com.tencent.msdk.stat;

import java.util.List;

class m implements Runnable {
    final /* synthetic */ List a;
    final /* synthetic */ j b;
    final /* synthetic */ k c;

    m(k kVar, List list, j jVar) {
        this.c = kVar;
        this.a = list;
        this.b = jVar;
    }

    public void run() {
        this.c.a((List<?>) this.a, this.b);
    }
}
