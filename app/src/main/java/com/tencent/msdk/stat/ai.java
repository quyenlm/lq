package com.tencent.msdk.stat;

class ai implements j {
    final /* synthetic */ af a;

    ai(af afVar) {
        this.a = afVar;
    }

    public void a() {
        StatServiceImpl.c();
        if (aj.b().a > 0) {
            StatServiceImpl.commitEvents(this.a.d, -1);
        }
    }

    public void b() {
        aj.b().a(this.a.a, (j) null, this.a.c, true);
        StatServiceImpl.d();
    }
}
