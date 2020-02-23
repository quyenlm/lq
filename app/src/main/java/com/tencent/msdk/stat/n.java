package com.tencent.msdk.stat;

import android.content.Context;
import com.tencent.msdk.stat.common.j;
import java.lang.Thread;

final class n implements Runnable {
    final /* synthetic */ Context a;

    n(Context context) {
        this.a = context;
    }

    public void run() {
        a.a(StatServiceImpl.t).h();
        j.a(this.a, true);
        aj.a(this.a);
        k.b(this.a);
        Thread.UncaughtExceptionHandler unused = StatServiceImpl.r = Thread.getDefaultUncaughtExceptionHandler();
        if (StatConfig.getStatSendStrategy() == StatReportStrategy.APP_LAUNCH) {
            StatServiceImpl.commitEvents(this.a, -1);
        }
        if (StatConfig.isDebugEnable()) {
            StatServiceImpl.q.d("Init MTA StatService success, sdk version:2.1.9");
        }
    }
}
