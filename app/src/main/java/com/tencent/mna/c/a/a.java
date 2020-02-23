package com.tencent.mna.c.a;

import com.tencent.mna.a.b;
import com.tencent.mna.b.a.e;
import com.tencent.mna.b.a.f;
import com.tencent.mna.b.a.h;
import com.tencent.mna.base.jni.entity.CdnMasterRet;
import com.tencent.mna.base.jni.entity.CdnNegRet;
import java.util.UUID;

/* compiled from: CdnAccelerator */
public class a implements f {
    /* access modifiers changed from: private */
    public CdnMasterRet a = null;
    private CdnNegRet b = null;
    private com.tencent.mna.b.a.c.a c = new com.tencent.mna.b.a.c.a();
    private h d = new h() {
        public int a(int i, int i2, int i3, int i4, int i5) {
            return com.tencent.mna.base.jni.a.a(i, i2, i3, i4, i5);
        }

        public int a(int i, int i2, int i3, int i4, int i5, String str) {
            return com.tencent.mna.base.jni.a.a(i, i2, i3, i4, i5, str);
        }

        public int a(int i, int i2, int i3) {
            if (a.this.a == null || a.this.a.masterErrno != 0) {
                return -1;
            }
            return com.tencent.mna.base.jni.a.a(i, i2, i3);
        }

        public String a() {
            if (a.this.a == null || a.this.a.masterErrno != 0) {
                return null;
            }
            return com.tencent.mna.base.utils.f.b(a.this.a.exportIp);
        }
    };

    public int a(String str, int i) {
        String f = com.tencent.mna.base.utils.f.f("101.226.141.225");
        com.tencent.mna.base.utils.h.a("masterIp:" + f);
        String uuid = UUID.randomUUID().toString();
        String str2 = b.f;
        this.a = com.tencent.mna.base.jni.a.a(f, 8090, str, i, str2, false);
        if (this.a == null) {
            return b(f, 51000);
        }
        com.tencent.mna.base.utils.h.b("[N]Cdn调度:" + this.a.toString());
        if (this.a.masterErrno != 0) {
            return b(f, this.a.masterErrno);
        }
        this.b = com.tencent.mna.base.jni.a.a(this.a.negIp, this.a.negPort, str, i, str2, uuid);
        if (this.b == null) {
            return c(f, 62000);
        }
        com.tencent.mna.base.utils.h.b("[N]Cdn协商:" + this.b.toString());
        if (this.b.negErrno != 0) {
            return c(f, this.b.negErrno);
        }
        return a(f);
    }

    public int a() {
        return com.tencent.mna.base.jni.a.h();
    }

    public e b() {
        e eVar = new e();
        eVar.a = com.tencent.mna.base.jni.a.a();
        eVar.b = com.tencent.mna.base.jni.a.b();
        eVar.c = com.tencent.mna.base.jni.a.c();
        eVar.d = com.tencent.mna.base.jni.a.d();
        eVar.e = com.tencent.mna.base.jni.a.e();
        eVar.f = com.tencent.mna.base.jni.a.f();
        eVar.g = com.tencent.mna.base.jni.a.g();
        if (eVar.a == 0 || eVar.b == 0 || eVar.c == 0 || eVar.d == 0 || eVar.e == 0 || eVar.f == 0 || eVar.g == 0) {
            return null;
        }
        return eVar;
    }

    public h c() {
        return this.d;
    }

    public String d() {
        if (this.b == null) {
            return null;
        }
        return com.tencent.mna.base.utils.f.b(this.b.proxyIp);
    }

    public int e() {
        if (this.b == null) {
            return 0;
        }
        return this.b.proxyPort;
    }

    public com.tencent.mna.b.a.c.a f() {
        return this.c;
    }

    public String g() {
        return "CdnAccelerator";
    }

    private int a(String str) {
        this.c.a = str;
        this.c.b = com.tencent.mna.base.utils.f.b(this.a.negIp);
        this.c.c = com.tencent.mna.base.utils.f.b(this.b.proxyIp);
        this.c.d = com.tencent.mna.base.utils.f.b(this.a.exportIp);
        this.c.f = 0;
        this.c.g = "" + this.b.token;
        return 0;
    }

    private int b(String str, int i) {
        this.c.a = str;
        this.c.f = i;
        return i;
    }

    private int c(String str, int i) {
        this.c.a = str;
        this.c.b = com.tencent.mna.base.utils.f.b(this.a.negIp);
        this.c.d = com.tencent.mna.base.utils.f.b(this.a.exportIp);
        this.c.f = i;
        return i;
    }
}
