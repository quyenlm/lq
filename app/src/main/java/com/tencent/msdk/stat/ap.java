package com.tencent.msdk.stat;

import java.util.List;

class ap implements j {
    final /* synthetic */ List a;
    final /* synthetic */ boolean b;
    final /* synthetic */ aj c;

    ap(aj ajVar, List list, boolean z) {
        this.c = ajVar;
        this.a = list;
        this.b = z;
    }

    public void a() {
        StatServiceImpl.c();
        this.c.a((List<as>) this.a, this.b, true);
    }

    public void b() {
        StatServiceImpl.d();
        this.c.a((List<as>) this.a, 1, this.b, true);
    }
}
