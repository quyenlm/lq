package com.tencent.smtt.sdk;

import android.content.Context;
import android.os.Handler;

final class k extends Thread {
    final /* synthetic */ Context a;
    final /* synthetic */ Handler b;

    k(Context context, Handler handler) {
        this.a = context;
        this.b = handler;
    }

    public void run() {
        if (am.a().a(true, this.a) == 0) {
            am.a().b(this.a, true);
        }
        o.a(true).a(this.a, false, false);
        br a2 = br.a();
        a2.a(this.a);
        boolean b2 = a2.b();
        this.b.sendEmptyMessage(3);
        if (!b2) {
            this.b.sendEmptyMessage(2);
        } else {
            this.b.sendEmptyMessage(1);
        }
    }
}
