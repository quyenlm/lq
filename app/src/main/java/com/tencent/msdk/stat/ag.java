package com.tencent.msdk.stat;

class ag implements j {
    final /* synthetic */ af a;

    ag(af afVar) {
        this.a = afVar;
    }

    public void a() {
        StatServiceImpl.c();
        if (aj.b().a() >= StatConfig.getMaxBatchReportCount()) {
            aj.b().a(StatConfig.getMaxBatchReportCount());
        }
    }

    public void b() {
        StatServiceImpl.d();
    }
}
