package com.tencent.msdk.stat;

import com.tencent.msdk.stat.common.j;
import java.util.TimerTask;

class e extends TimerTask {
    final /* synthetic */ d a;

    e(d dVar) {
        this.a = dVar;
    }

    public void run() {
        if (StatConfig.isDebugEnable()) {
            j.b().i("TimerTask run");
        }
        StatServiceImpl.d(this.a.c);
        cancel();
        this.a.a();
    }
}
