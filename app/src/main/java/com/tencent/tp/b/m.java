package com.tencent.tp.b;

import com.tencent.tp.a.o;

class m implements o.a {
    final /* synthetic */ k a;

    m(k kVar) {
        this.a = kVar;
    }

    public void a() {
        com.tencent.tp.m.a("rootkit:launch_1_0");
        this.a.c();
    }

    public void b() {
        com.tencent.tp.m.a("rootkit:launch_1_1");
        boolean unused = this.a.d = true;
        com.tencent.tp.m.a(1);
    }
}
