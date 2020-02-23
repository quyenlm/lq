package com.tencent.msdk.stat;

import android.content.Context;
import com.tencent.msdk.stat.a.b;
import com.tencent.msdk.stat.a.c;

final class s implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ c b;
    final /* synthetic */ Context c;
    final /* synthetic */ StatSpecifyReportedInfo d;

    s(String str, c cVar, Context context, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        this.a = str;
        this.b = cVar;
        this.c = context;
        this.d = statSpecifyReportedInfo;
    }

    public void run() {
        try {
            if (StatServiceImpl.a(this.a)) {
                StatServiceImpl.q.error((Object) "The event_id of StatService.trackCustomEndEvent() can not be null or empty.");
                return;
            }
            Long l = (Long) StatServiceImpl.e.remove(this.b);
            if (l != null) {
                b bVar = new b(this.c, StatServiceImpl.a(this.c, false, this.d), this.b.a, this.d);
                bVar.b().c = this.b.c;
                Long valueOf = Long.valueOf((System.currentTimeMillis() - l.longValue()) / 1000);
                bVar.a(Long.valueOf(valueOf.longValue() <= 0 ? 1 : valueOf.longValue()).longValue());
                new af(bVar).a();
                return;
            }
            StatServiceImpl.q.warn("No start time found for custom event: " + this.b.toString() + ", lost trackCustomBeginKVEvent()?");
        } catch (Throwable th) {
            StatServiceImpl.q.e(th);
        }
    }
}
