package com.tencent.mna.c.c;

import com.tencent.mna.b.a.e;
import com.tencent.mna.b.a.f;
import com.tencent.mna.b.a.h;
import com.tencent.mna.base.jni.c;

/* compiled from: InoAccelerator */
public class a implements f {
    private String a = "0.0.0.0";
    private int b = 0;
    private String c = "0.0.0.0";
    private int d = 0;
    private String e = "0.0.0.0";
    /* access modifiers changed from: private */
    public String f = "0.0.0.0";
    private int g = 0;
    private com.tencent.mna.b.a.c.a h = new com.tencent.mna.b.a.c.a();
    private h i = new h() {
        public int a(int i, int i2, int i3, int i4, int i5) {
            return c.a(i, i2, i3, i4, i5);
        }

        public int a(int i, int i2, int i3, int i4, int i5, String str) {
            return c.a(i, i2, i3, i4, i5, str);
        }

        public int a(int i, int i2, int i3) {
            return c.a(i, i2, i3);
        }

        public String a() {
            return a.this.f;
        }
    };

    public int a(String str, int i2) {
        int a2 = a(com.tencent.mna.base.a.a.am(), com.tencent.mna.base.a.a.ap(), com.tencent.mna.base.a.a.an(), com.tencent.mna.base.a.a.aq(), com.tencent.mna.base.a.a.ao(), com.tencent.mna.base.a.a.e(), com.tencent.mna.base.a.a.f());
        this.h = new com.tencent.mna.b.a.c.a("0.0.0.0", "0.0.0.0", this.a + ";" + this.c + ";" + this.e, this.f, a2, "");
        com.tencent.mna.base.utils.h.b(String.format(com.tencent.mna.a.a.a, "[N]Ino调度协商错误码:%d, proxyIp1:[%s], proxyIp2:[%s], proxyIp3:[%s]", new Object[]{Integer.valueOf(this.h.f), this.a, this.c, this.e}));
        return a2;
    }

    public int a(String str, int i2, String str2, int i3, String str3, String str4, int i4) {
        int i5 = 65005;
        this.a = str;
        this.b = i2;
        this.c = str2;
        this.d = i3;
        this.e = str3;
        this.f = str4;
        this.g = i4;
        boolean z = i2 > 0 && com.tencent.mna.base.utils.f.a(str);
        boolean z2 = i4 > 0 && com.tencent.mna.base.utils.f.a(str4);
        if (z || z2) {
            i5 = c.a(this.a, this.b, this.c, this.d, this.e, this.f, this.g, false);
        }
        if (!z) {
            return z2 ? 65001 : 65002;
        }
        return i5;
    }

    public int a() {
        return c.h();
    }

    public e b() {
        e eVar = new e();
        eVar.a = c.a();
        eVar.b = c.b();
        eVar.c = c.c();
        eVar.d = c.d();
        eVar.e = c.e();
        eVar.f = c.f();
        eVar.g = c.g();
        if (eVar.a == 0 || eVar.b == 0 || eVar.c == 0 || eVar.d == 0 || eVar.e == 0 || eVar.f == 0 || eVar.g == 0) {
            return null;
        }
        return eVar;
    }

    public h c() {
        return this.i;
    }

    public String d() {
        return this.a;
    }

    public int e() {
        return this.b;
    }

    public com.tencent.mna.b.a.c.a f() {
        return this.h;
    }

    public String g() {
        return "InoAccelerator";
    }
}
