package com.tencent.msdk.stat;

import android.content.Context;

final class x implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ Context b;
    final /* synthetic */ StatSpecifyReportedInfo c;

    x(String str, Context context, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        this.a = str;
        this.b = context;
        this.c = statSpecifyReportedInfo;
    }

    public void run() {
        try {
            synchronized (StatServiceImpl.o) {
                if (StatServiceImpl.o.size() >= StatConfig.getMaxParallelTimmingEvents()) {
                    StatServiceImpl.q.error((Object) "The number of page events exceeds the maximum value " + Integer.toString(StatConfig.getMaxParallelTimmingEvents()));
                    return;
                }
                String unused = StatServiceImpl.m = this.a;
                if (StatServiceImpl.o.containsKey(StatServiceImpl.m)) {
                    StatServiceImpl.q.e((Object) "Duplicate PageID : " + StatServiceImpl.m + ", onResume() repeated?");
                    return;
                }
                StatServiceImpl.o.put(StatServiceImpl.m, Long.valueOf(System.currentTimeMillis()));
                StatServiceImpl.a(this.b, true, this.c);
            }
        } catch (Throwable th) {
            StatServiceImpl.q.e(th);
        }
    }
}
