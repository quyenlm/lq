package com.tencent.mna.base.d;

import com.tencent.component.debug.TraceFormat;
import com.tencent.mna.b.b.b;
import com.tencent.mna.base.jni.e;
import com.tencent.mna.base.utils.f;
import com.tencent.mna.base.utils.h;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: DirectSpeedTester */
public class a {
    private final AtomicInteger a = new AtomicInteger(200000);
    private final AtomicInteger b = new AtomicInteger(0);
    private int c;
    private int d;
    private byte[] e;
    private b f = null;

    public a(int i, String str, int i2) {
        this.d = i;
        this.e = f.k(str);
        this.c = i2;
        h.a("DirectSpeedTester speedAddr:" + Arrays.toString(this.e));
    }

    public a(int i, String str, int i2, b bVar) {
        this.d = i;
        this.f = bVar;
        int a2 = this.f != null ? bVar.a(this.d) : -1;
        this.e = f.k(str);
        this.c = i2;
        h.a("DirectSpeedTester speedAddr:" + Arrays.toString(this.e) + ", bindRes:" + a2);
    }

    public int a(int i) {
        if (this.e == null) {
            return -1;
        }
        return e.a(this.d, this.e, this.c, this.a.incrementAndGet(), TraceFormat.STR_ASSERT, i);
    }

    public int a(int i, String str) {
        if (this.e == null) {
            return -1;
        }
        return e.b(this.d, this.e, this.c, this.b.incrementAndGet(), str, i);
    }

    public void b(int i) {
        h.a("Direct Speed Tester, fd:" + this.d + " set tos: 0x" + Integer.toHexString(i) + ", res:" + e.b(this.d, i));
    }

    public void a() {
        if (this.f != null) {
            this.f.b(this.d);
        }
        e.d(this.d);
        this.d = 0;
    }
}
