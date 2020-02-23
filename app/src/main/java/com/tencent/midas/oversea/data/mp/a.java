package com.tencent.midas.oversea.data.mp;

import android.os.CountDownTimer;

class a extends CountDownTimer {
    final /* synthetic */ APCountDown a;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    a(APCountDown aPCountDown, long j, long j2) {
        super(j, j2);
        this.a = aPCountDown;
    }

    public void onFinish() {
        long unused = this.a.c = 0;
        if (this.a.d != null) {
            this.a.d.finished();
        }
    }

    public void onTick(long j) {
        APCountDown.b(this.a);
        if (this.a.d != null) {
            this.a.d.update();
        }
    }
}
