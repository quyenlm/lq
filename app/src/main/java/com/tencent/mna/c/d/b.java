package com.tencent.mna.c.d;

import com.tencent.mna.b.d.a;
import com.tencent.mna.base.a.c;
import com.tencent.mna.base.jni.d;
import com.tencent.mna.base.utils.f;

/* compiled from: McDgnSpeedTester */
public class b implements a {
    private String a = "0.0.0.0";
    private int b = 0;
    private int c = 0;

    public int a() {
        this.a = c.c();
        this.b = c.d();
        return d.a(f.l(this.a), this.b, true);
    }

    public int a(int i) {
        int i2 = this.c + 1;
        this.c = i2;
        return d.b(i, i2, 500);
    }
}
