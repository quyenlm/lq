package com.tencent.mna.b.b;

import android.content.Context;
import com.tencent.mna.base.a.a.b;
import com.tencent.mna.base.utils.f;
import com.tencent.mna.base.utils.h;
import com.tencent.mna.base.utils.k;
import com.tencent.mna.base.utils.m;
import com.tencent.mna.base.utils.q;
import java.net.InetAddress;
import java.util.List;
import java.util.Vector;

/* compiled from: BindingConfig */
class a {
    String a;
    String b;
    String c;
    String d;
    String e;
    String f;
    int g;
    int h;
    int i;
    int j;
    int k;
    int l;
    List<String> m = null;

    a() {
    }

    public int a(Context context, int i2, int i3) {
        int a2 = k.a(context);
        String a3 = com.tencent.mna.base.a.a.a(new b.a().a(com.tencent.mna.a.b.h).a(com.tencent.mna.a.b.a()).b(com.tencent.mna.a.b.f).c(m.a(com.tencent.mna.b.g(), com.tencent.mna.base.a.a.aX())).d(m.a()).e("0").f("").g(m.d(context)).b(q.g(context)).h(q.h(context)).i(com.tencent.mna.a.b.e).c(a2).d(k.a(context, a2)).a());
        com.tencent.mna.base.a.a.a a4 = com.tencent.mna.base.a.a.a(com.tencent.mna.a.b.a(), a3, i2, i3);
        if (a4 == null) {
            a4 = com.tencent.mna.base.a.a.a(com.tencent.mna.a.b.a(), a3, i2, i3);
        }
        if (a4 == null) {
            return -1;
        }
        this.a = a4.aX;
        this.g = a4.aY;
        this.b = a4.aZ;
        this.h = a4.ba;
        this.c = a4.ac;
        this.i = a4.af;
        this.d = a4.ad;
        this.j = a4.ag;
        this.e = a4.ae;
        this.f = a4.aK;
        this.k = a4.aL;
        this.l = a4.ah;
        h.a("[N]mobile control config:" + toString());
        this.m = new Vector();
        InetAddress[] b2 = f.b(com.tencent.mna.a.b.d(), i3);
        if (b2 == null) {
            return -3;
        }
        for (InetAddress inetAddress : b2) {
            if (f.a(inetAddress.getHostAddress())) {
                this.m.add(inetAddress.getHostAddress());
            }
        }
        if (this.m.size() <= 0) {
            return -4;
        }
        h.a("[N]W2M domain:[" + com.tencent.mna.a.b.d() + "] to 4GVips:" + this.m.toString());
        return 0;
    }

    public String toString() {
        return "BindingConfig{mSpeedIp='" + this.a + '\'' + ", mSpeedPort=" + this.g + ", mEdgeIp='" + this.b + '\'' + ", mEdgePort=" + this.h + ", mProxyIp1='" + this.c + '\'' + ", mProxyPort1=" + this.i + ", mProxyIp2='" + this.d + '\'' + ", mProxyPort2=" + this.j + ", mMutiProxyIp='" + this.f + '\'' + ", mMutiProxyPort=" + this.k + ", mToken=" + this.l + '}';
    }
}
