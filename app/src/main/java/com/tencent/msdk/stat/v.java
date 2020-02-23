package com.tencent.msdk.stat;

import android.content.Context;
import com.tencent.msdk.stat.a.b;
import com.tencent.msdk.stat.a.c;

final class v implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ StatSpecifyReportedInfo b;
    final /* synthetic */ c c;
    final /* synthetic */ int d;

    v(Context context, StatSpecifyReportedInfo statSpecifyReportedInfo, c cVar, int i) {
        this.a = context;
        this.b = statSpecifyReportedInfo;
        this.c = cVar;
        this.d = i;
    }

    public void run() {
        try {
            b bVar = new b(this.a, StatServiceImpl.a(this.a, false, this.b), this.c.a, this.b);
            bVar.b().c = this.c.c;
            Long valueOf = Long.valueOf((long) this.d);
            bVar.a(Long.valueOf(valueOf.longValue() <= 0 ? 1 : valueOf.longValue()).longValue());
            new af(bVar).a();
        } catch (Throwable th) {
            StatServiceImpl.q.e(th);
        }
    }
}
