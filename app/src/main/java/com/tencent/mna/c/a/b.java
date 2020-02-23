package com.tencent.mna.c.a;

import com.tencent.mna.b.d.a;
import com.tencent.mna.base.jni.entity.CdnMasterRet;
import com.tencent.mna.base.utils.f;
import com.tencent.mna.base.utils.h;

/* compiled from: CdnDgnSpeedTester */
public class b implements a {
    private CdnMasterRet a = null;
    private int b = 0;

    public int a() {
        String f = f.f("101.226.141.225");
        h.a("masterIp:" + f);
        this.a = com.tencent.mna.base.jni.a.a(f.l(f), 8090, "0.0.0.0", 0, com.tencent.mna.a.b.f, true);
        if (this.a == null) {
            return -1;
        }
        h.a("cdn diagnose ret:" + this.a.toString());
        if (this.a.masterErrno != 0) {
            return this.a.masterErrno;
        }
        return 0;
    }

    public int a(int i) {
        if (this.a == null || this.a.masterErrno != 0) {
            return -1;
        }
        int i2 = this.b + 1;
        this.b = i2;
        return com.tencent.mna.base.jni.a.b(i, i2, 500);
    }
}
