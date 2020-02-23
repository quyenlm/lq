package com.tencent.mna.c.e;

import android.annotation.SuppressLint;
import com.tencent.mna.a.b;
import com.tencent.mna.b.a.e;
import com.tencent.mna.b.a.f;
import com.tencent.mna.b.a.h;
import com.tencent.mna.base.jni.entity.TCallTunnelRet;

/* compiled from: TCallAccelerator */
public class a implements f {
    private TCallTunnelRet a = null;
    private com.tencent.mna.b.a.c.a b = new com.tencent.mna.b.a.c.a();
    private h c = new h() {
        public int a(int i, int i2, int i3, int i4, int i5) {
            return com.tencent.mna.base.jni.f.a(i, i2, i3, i4, i5);
        }

        public int a(int i, int i2, int i3, int i4, int i5, String str) {
            return com.tencent.mna.base.jni.f.a(i, i2, i3, i4, i5, str);
        }

        public int a(int i, int i2, int i3) {
            return com.tencent.mna.base.jni.f.a(b.f);
        }

        public String a() {
            return com.tencent.mna.base.jni.f.b();
        }
    };

    @SuppressLint({"DefaultLocale"})
    public int a(String str, int i) {
        int a2 = com.tencent.mna.base.jni.f.a(true);
        if (a2 != 0) {
            return a(a2);
        }
        String str2 = b.f;
        this.a = com.tencent.mna.base.jni.f.a(str, str2);
        if (this.a != null) {
            com.tencent.mna.base.utils.h.b(String.format("[N]TCall调度错误码:%d, masterIp:%s, proxyIp:%s", new Object[]{Integer.valueOf(this.a.tunnelErrno), com.tencent.mna.base.utils.f.b(this.a.masterIp), com.tencent.mna.base.utils.f.b(this.a.accessIp)}));
        }
        if (this.a == null) {
            return a(70001);
        }
        this.b.a = com.tencent.mna.base.utils.f.b(this.a.masterIp);
        this.b.c = com.tencent.mna.base.utils.f.b(this.a.accessIp);
        this.b.d = com.tencent.mna.base.utils.f.b(this.a.accessIp);
        if (this.a.tunnelErrno != 0) {
            return a(this.a.tunnelErrno);
        }
        String c2 = com.tencent.mna.base.a.a.c();
        int d = com.tencent.mna.base.a.a.d();
        String Q = com.tencent.mna.base.a.a.Q();
        int a3 = com.tencent.mna.base.jni.f.a(str, str, i, str2, Q);
        if (a3 != 0) {
            return a(a3);
        }
        int a4 = com.tencent.mna.base.jni.f.a(str, c2, d, str2, Q);
        if (a4 != 0) {
            return a(a4);
        }
        return 0;
    }

    private int a(int i) {
        this.b.f = b(i);
        return this.b.f;
    }

    public int a() {
        return 0;
    }

    public e b() {
        e eVar = new e();
        eVar.a = com.tencent.mna.base.jni.f.c();
        eVar.b = com.tencent.mna.base.jni.f.d();
        eVar.c = com.tencent.mna.base.jni.f.e();
        eVar.d = com.tencent.mna.base.jni.f.f();
        eVar.e = com.tencent.mna.base.jni.f.g();
        eVar.f = com.tencent.mna.base.jni.f.h();
        eVar.g = com.tencent.mna.base.jni.f.i();
        eVar.h = com.tencent.mna.base.jni.f.j();
        if (eVar.a == 0 || eVar.b == 0 || eVar.c == 0 || eVar.d == 0 || eVar.e == 0 || eVar.f == 0 || eVar.g == 0 || eVar.h == 0) {
            return null;
        }
        return eVar;
    }

    public h c() {
        return this.c;
    }

    public String d() {
        return this.a != null ? com.tencent.mna.base.utils.f.b(this.a.accessIp) : "0.0.0.0";
    }

    public int e() {
        return 0;
    }

    public com.tencent.mna.b.a.c.a f() {
        if (this.b.e == null || this.b.e.isSameArea > 1) {
            this.b.e = com.tencent.mna.base.jni.f.a();
        }
        return this.b;
    }

    public String g() {
        return "TCallAccelerator";
    }

    private int b(int i) {
        return i > 0 ? i : 61000 - i;
    }
}
