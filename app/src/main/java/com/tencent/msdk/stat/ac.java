package com.tencent.msdk.stat;

import android.content.Context;
import com.tencent.msdk.stat.common.j;

final class ac implements Runnable {
    final /* synthetic */ Context a;

    ac(Context context) {
        this.a = context;
    }

    public void run() {
        if (this.a == null) {
            StatServiceImpl.q.error((Object) "The Context of StatService.onStop() can not be null!");
            return;
        }
        StatServiceImpl.flushDataToDB(this.a);
        if (!StatServiceImpl.a()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (j.A(this.a)) {
                if (StatConfig.isDebugEnable()) {
                    StatServiceImpl.q.i("onStop isBackgroundRunning flushDataToDB");
                }
                StatServiceImpl.commitEvents(this.a, -1);
            }
        }
    }
}
