package com.tencent.mna.b.a.a;

import com.tencent.mna.base.utils.h;

/* compiled from: NetworkJumpChecker */
public class b implements a {
    private int a;
    private int b;
    private int c;

    public b(int i, int i2, int i3) {
        this.a = i;
        this.b = i2;
        this.c = i3;
    }

    public boolean a(int i, int i2) {
        int i3 = i - i2;
        boolean z = i > this.a && i2 > 0 && i2 < this.b && i3 > this.c;
        if (z) {
            h.b("[N]网络跳变，当前延迟[" + i + "] > CurMinDelayStd[" + this.a + "], 上一次延迟0 < [" + i2 + "] < LastMaxDelayStd[" + this.b + "], 两次延迟差值[" + i3 + "] > JumpDiffValueStd[" + this.c + "]");
        }
        return z;
    }
}
