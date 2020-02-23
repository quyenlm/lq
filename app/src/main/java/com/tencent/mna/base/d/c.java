package com.tencent.mna.base.d;

import com.tencent.mna.base.jni.e;
import com.tencent.mna.base.utils.f;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: PingUploader */
public class c {
    private final AtomicInteger a;
    private int b;
    private int c;
    private int d;

    public c(int i, String str, int i2) {
        this(i, f.i(str), i2);
    }

    public c(int i, int i2, int i3) {
        this.a = new AtomicInteger(0);
        this.d = i;
        this.b = i2;
        this.c = i3;
    }

    public int a(int i, String str) {
        return e.a(this.d, this.b, this.c, this.a.incrementAndGet(), str, i);
    }

    public void a() {
        e.d(this.d);
        this.d = 0;
    }
}
