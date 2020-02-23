package com.tencent.mna.c.c;

import com.tencent.mna.b.d.a;
import com.tencent.mna.base.a.c;
import com.tencent.mna.base.utils.f;

/* compiled from: InoDgnSpeedTester */
public class b implements a {
    private String a = "0.0.0.0";
    private int b = 0;
    private int c = 0;

    public int a() {
        this.a = c.c();
        this.b = c.d();
        return com.tencent.mna.base.jni.c.a(f.l(this.a), this.b, true);
    }

    public int a(int i) {
        int i2 = this.c + 1;
        this.c = i2;
        return com.tencent.mna.base.jni.c.b(i, i2, 500);
    }
}
