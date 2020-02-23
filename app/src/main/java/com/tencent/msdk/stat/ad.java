package com.tencent.msdk.stat;

import android.content.Context;

final class ad implements Runnable {
    final /* synthetic */ Context a;

    ad(Context context) {
        this.a = context;
    }

    public void run() {
        StatServiceImpl.flushDataToDB(this.a);
    }
}
