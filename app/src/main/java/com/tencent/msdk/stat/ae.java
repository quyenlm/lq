package com.tencent.msdk.stat;

import android.content.Context;
import com.tencent.msdk.stat.a.b;
import com.tencent.msdk.stat.a.c;

final class ae implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ StatSpecifyReportedInfo b;
    final /* synthetic */ c c;

    ae(Context context, StatSpecifyReportedInfo statSpecifyReportedInfo, c cVar) {
        this.a = context;
        this.b = statSpecifyReportedInfo;
        this.c = cVar;
    }

    public void run() {
        try {
            b bVar = new b(this.a, StatServiceImpl.a(this.a, false, this.b), this.c.a, this.b);
            bVar.b().b = this.c.b;
            new af(bVar).a();
        } catch (Throwable th) {
            StatServiceImpl.q.e(th);
        }
    }
}
