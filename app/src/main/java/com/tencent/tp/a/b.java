package com.tencent.tp.a;

import android.os.Handler;
import android.os.Looper;
import java.util.TimerTask;

class b extends TimerTask {
    final /* synthetic */ a a;

    b(a aVar) {
        this.a = aVar;
    }

    public void run() {
        new Handler(Looper.getMainLooper()).postDelayed(new c(this), 0);
    }
}
