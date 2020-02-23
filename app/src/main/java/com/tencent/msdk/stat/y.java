package com.tencent.msdk.stat;

import android.content.Context;
import com.tencent.msdk.stat.a.g;

final class y implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ String b;
    final /* synthetic */ StatSpecifyReportedInfo c;

    y(Context context, String str, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        this.a = context;
        this.b = str;
        this.c = statSpecifyReportedInfo;
    }

    public void run() {
        Long l;
        try {
            StatServiceImpl.flushDataToDB(this.a);
            synchronized (StatServiceImpl.o) {
                l = (Long) StatServiceImpl.o.remove(this.b);
            }
            if (l != null) {
                Long valueOf = Long.valueOf((System.currentTimeMillis() - l.longValue()) / 1000);
                if (valueOf.longValue() <= 0) {
                    valueOf = 1L;
                }
                String i = StatServiceImpl.n;
                if (i != null && i.equals(this.b)) {
                    i = "-";
                }
                g gVar = new g(this.a, i, this.b, StatServiceImpl.a(this.a, false, this.c), valueOf, this.c);
                if (!this.b.equals(StatServiceImpl.m)) {
                    StatServiceImpl.q.warn("Invalid invocation since previous onResume on diff page.");
                }
                new af(gVar).a();
                String unused = StatServiceImpl.n = this.b;
                return;
            }
            StatServiceImpl.q.e((Object) "Starttime for PageID:" + this.b + " not found, lost onResume()?");
        } catch (Throwable th) {
            StatServiceImpl.q.e(th);
        }
    }
}
