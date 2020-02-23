package com.tencent.mna.c.d;

import com.tencent.mna.b.a.f;
import com.tencent.mna.b.a.h;
import com.tencent.mna.b.b.b;
import com.tencent.mna.base.jni.d;
import com.tencent.mna.base.jni.e;
import com.tencent.mna.base.utils.NetworkChangeReceiver;
import com.tencent.mna.base.utils.c;
import com.tencent.mna.base.utils.k;
import com.tencent.mna.base.utils.m;

/* compiled from: McAccelerator */
public class a implements f {
    private b a;
    private String b = "0.0.0.0";
    private String c = "0.0.0.0";
    private int d = 0;
    private int e = 0;
    /* access modifiers changed from: private */
    public String f = "0.0.0.0";
    private int g = 0;
    private int h = 0;
    private int i = 0;
    private com.tencent.mna.b.a.c.a j = new com.tencent.mna.b.a.c.a();
    private int k = 0;
    private int l = 0;
    private int m = 0;
    private int n = 0;
    private h o = new h() {
        public int a(int i, int i2, int i3, int i4, int i5) {
            return d.a(i, i2, i3, i4, i5);
        }

        public int a(int i, int i2, int i3, int i4, int i5, String str) {
            return d.a(i, i2, i3, i4, i5, str);
        }

        public int a(int i, int i2, int i3) {
            return d.a(i, i2, i3);
        }

        public String a() {
            return a.this.f;
        }
    };

    public int a(String str, int i2) {
        String a2;
        int i3;
        int i4;
        String aJ = com.tencent.mna.base.a.a.aJ();
        String aK = com.tencent.mna.base.a.a.aK();
        int aL = com.tencent.mna.base.a.a.aL();
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
        int a4 = a(aJ, aK, aL, e2, f2, i3, hashCode, i4, aR, false);
        this.j = new com.tencent.mna.b.a.c.a("0.0.0.0", "0.0.0.0", this.b + ";" + this.c, this.f, a4, "" + aR);
        this.j.h = "" + c.a(this.m) + "_" + c.a(this.h) + "_" + c.a(this.i) + "_" + this.n;
        com.tencent.mna.base.utils.h.b(String.format(com.tencent.mna.a.a.a, "[N]Mc调度协商错误码:%d, Proxy1IpMain:[%s], Proxy1IpMobile:[%s], devkey:%d, clientkey:%d, delay_clientkey:%d, setId:%d", new Object[]{Integer.valueOf(this.j.f), this.b, this.c, Long.valueOf(c.a(this.m)), Long.valueOf(c.a(this.h)), Long.valueOf(c.a(this.i)), Integer.valueOf(this.n)}));
        return a4;
    }

    public int a(String str, String str2, int i2, String str3, int i3, int i4, int i5, int i6, int i7, boolean z) {
        this.b = str2;
        this.d = i2;
        this.f = str3;
        this.g = i3;
        this.h = i4;
        this.i = i5;
        this.m = i6;
        this.n = i7;
        boolean z2 = i3 > 0 && com.tencent.mna.base.utils.f.a(str3);
        if (z2) {
            d.a(str3, i3, false);
        }
        if (i2 > 0 && com.tencent.mna.base.utils.f.a(str2)) {
            this.a = new b();
            int a2 = this.a.a(com.tencent.mna.b.g());
            if (a2 != 0) {
                return 80000 - a2;
            }
            this.k = e.a(300);
            if (this.a.a(this.k) != 0) {
                e.d(this.k);
                this.k = 0;
                return 81003;
            }
            this.l = e.a(300);
            if (this.a.a(this.l) != 0) {
                e.d(this.l);
                this.l = 0;
                return 81004;
            }
            int a3 = this.a.a(com.tencent.mna.b.g(), false);
            if (a3 != 0) {
                return 82000 - a3;
            }
            String c2 = this.a.c();
            int d2 = this.a.d();
            if (d2 <= 0 || !com.tencent.mna.base.utils.f.a(c2)) {
                return 81006;
            }
            this.c = c2;
            this.e = d2;
            int a4 = d.a(this.k, this.l, str2, c2, i2, d2, str3, i3, i4, i5, i6, i7, z);
            if (a4 != 0) {
                return a4;
            }
            d.a(true);
            NetworkChangeReceiver.a((NetworkChangeReceiver.b) new NetworkChangeReceiver.b() {
                public void a(int i) {
                    com.tencent.mna.base.utils.h.a("onNetworkChange:" + k.a(i));
                    if (k.c(i)) {
                        d.a(false);
                    } else if (k.d(i) || k.e(i)) {
                        d.a(true);
                    }
                }
            });
            return a4;
        } else if (z2) {
            return 81001;
        } else {
            return 81002;
        }
    }

    public int a() {
        if (this.a != null) {
            this.a.b(com.tencent.mna.b.g());
        }
        if (this.k != 0) {
            e.d(this.k);
            this.k = 0;
        }
        if (this.l != 0) {
            e.d(this.l);
            this.l = 0;
        }
        NetworkChangeReceiver.a();
        return d.h();
    }

    public com.tencent.mna.b.a.e b() {
        com.tencent.mna.b.a.e eVar = new com.tencent.mna.b.a.e();
        eVar.a = d.a();
        eVar.b = d.b();
        eVar.c = d.c();
        eVar.d = d.d();
        eVar.e = d.e();
        eVar.f = d.f();
        eVar.g = d.g();
        if (eVar.a == 0 || eVar.b == 0 || eVar.c == 0 || eVar.d == 0 || eVar.e == 0 || eVar.f == 0 || eVar.g == 0) {
            return null;
        }
        return eVar;
    }

    public h c() {
        return this.o;
    }

    public String d() {
        return this.b;
    }

    public int e() {
        return this.d;
    }

    public com.tencent.mna.b.a.c.a f() {
        return this.j;
    }

    public String g() {
        return "McAccelerator";
    }
}
