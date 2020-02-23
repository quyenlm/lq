package com.tencent.mna.c.b;

import com.tencent.mna.b.a.e;
import com.tencent.mna.b.a.f;
import com.tencent.mna.b.a.h;
import com.tencent.mna.base.jni.b;
import com.tencent.mna.base.utils.c;
import com.tencent.mna.base.utils.m;

/* compiled from: DsAccelerator */
public class a implements f {
    private String a = "0.0.0.0";
    private int b = 0;
    /* access modifiers changed from: private */
    public String c = "0.0.0.0";
    private int d = 0;
    private int e = 0;
    private int f = 0;
    private com.tencent.mna.b.a.c.a g = new com.tencent.mna.b.a.c.a();
    private int h = 0;
    private int i = 0;
    private h j = new h() {
        public int a(int i, int i2, int i3, int i4, int i5) {
            return b.a(i, i2, i3, i4, i5);
        }

        public int a(int i, int i2, int i3, int i4, int i5, String str) {
            return b.a(i, i2, i3, i4, i5, str);
        }

        public int a(int i, int i2, int i3) {
            return b.a(i, i2, i3);
        }

        public String a() {
            return a.this.c;
        }
    };

    public int a(String str, int i2) {
        String a2;
        int i3;
        int i4;
        String aS = com.tencent.mna.base.a.a.aS();
        int aT = com.tencent.mna.base.a.a.aT();
        String e2 = com.tencent.mna.base.a.a.e();
        int f2 = com.tencent.mna.base.a.a.f();
        if (com.tencent.mna.a.b.f == null || com.tencent.mna.a.b.f.length() <= 0) {
            a2 = m.a(com.tencent.mna.b.g(), com.tencent.mna.base.a.a.aX());
        } else {
            a2 = com.tencent.mna.a.b.f;
        }
        if (a2 != null) {
            i3 = a2.hashCode();
        } else {
            i3 = 0;
        }
        int hashCode = (a2 + String.valueOf(System.currentTimeMillis())).hashCode();
        String a3 = m.a(com.tencent.mna.b.g(), com.tencent.mna.base.a.a.aX());
        if (a3 != null) {
            i4 = a3.hashCode();
        } else {
            i4 = 0;
        }
        int aR = com.tencent.mna.base.a.a.aR();
        int a4 = a(aS, aT, e2, f2, i3, hashCode, i4, aR, false);
        this.g = new com.tencent.mna.b.a.c.a("0.0.0.0", "0.0.0.0", this.a, this.c, a4, "" + aR);
        this.g.h = "" + c.a(this.h) + "_" + c.a(this.e) + "_" + c.a(this.f) + "_" + this.i;
        com.tencent.mna.base.utils.h.b(String.format(com.tencent.mna.a.a.a, "[N]Ds调度协商错误码:%d, Proxy1IpMain:[%s], devkey:%d, clientkey:%d, delay_clientkey:%d, setId:%d", new Object[]{Integer.valueOf(this.g.f), this.a, Long.valueOf(c.a(this.h)), Long.valueOf(c.a(this.e)), Long.valueOf(c.a(this.f)), Integer.valueOf(this.i)}));
        return a4;
    }

    public int a(String str, int i2, String str2, int i3, int i4, int i5, int i6, int i7, boolean z) {
        boolean z2 = true;
        this.a = str;
        this.b = i2;
        this.c = str2;
        this.d = i3;
        this.e = i4;
        this.f = i5;
        this.h = i6;
        this.i = i7;
        boolean z3 = i3 > 0 && com.tencent.mna.base.utils.f.a(str2);
        if (z3) {
            b.a(str2, i3, false);
        }
        if (i2 <= 0 || !com.tencent.mna.base.utils.f.a(str)) {
            z2 = false;
        }
        if (!z2) {
            return z3 ? 81001 : 81002;
        }
        return b.a(str, i2, str2, i3, i4, i5, i6, i7, z);
    }

    public int a() {
        return b.h();
    }

    public e b() {
        e eVar = new e();
        eVar.a = b.a();
        eVar.b = b.b();
        eVar.c = b.c();
        eVar.d = b.d();
        eVar.e = b.e();
        eVar.f = b.f();
        eVar.g = b.g();
        if (eVar.a == 0 || eVar.b == 0 || eVar.c == 0 || eVar.d == 0 || eVar.e == 0 || eVar.f == 0 || eVar.g == 0) {
            return null;
        }
        return eVar;
    }

    public h c() {
        return this.j;
    }

    public String d() {
        return this.a;
    }

    public int e() {
        return this.b;
    }

    public com.tencent.mna.b.a.c.a f() {
        return this.g;
    }

    public String g() {
        return "DsAccelerator";
    }
}
