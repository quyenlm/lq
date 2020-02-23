package com.tencent.msdk.stat;

import android.content.Context;

final class t implements Runnable {
    final /* synthetic */ Context a;
    final /* synthetic */ int b;

    t(Context context, int i) {
        this.a = context;
        this.b = i;
    }

    public void run() {
        try {
            StatServiceImpl.flushDataToDB(this.a);
            aj.a(this.a).a(this.b);
        } catch (Throwable th) {
            StatServiceImpl.q.e(th);
        }
    }
}
