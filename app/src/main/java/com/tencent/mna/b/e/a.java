package com.tencent.mna.b.e;

import android.content.Context;
import com.tencent.mna.a.b;
import com.tencent.mna.b.a.d;
import com.tencent.mna.base.c.c;
import com.tencent.mna.base.c.f;
import com.tencent.mna.base.utils.h;
import com.tencent.mna.base.utils.i;
import com.tencent.mna.base.utils.j;
import com.tencent.mna.base.utils.k;
import com.tencent.mna.base.utils.q;

/* compiled from: NetworkQuery */
public class a {
    public static C0028a a(Context context, boolean z, boolean z2, boolean z3, d dVar) {
        C0028a aVar = new C0028a();
        try {
            int a = k.a(context);
            aVar.b = C0028a.a(a);
            aVar.r = i.b(context);
            if (z3 && dVar != null) {
                aVar.p = dVar.d();
                aVar.q = dVar.c();
            }
            if (z) {
                j.c c = j.c();
                if (c != null) {
                    aVar.l = (int) c.c;
                    aVar.m = (int) c.d;
                    aVar.n = (int) c.e;
                    aVar.o = (int) c.f;
                }
                j.a b = j.b();
                if (k.d(a)) {
                    aVar.f = (int) b.h;
                    aVar.g = (int) b.d;
                    aVar.h = (int) b.j;
                    aVar.i = (int) b.f;
                    aVar.j = (int) b.i;
                    aVar.k = (int) b.e;
                } else if (k.c(a)) {
                    aVar.f = (int) b.p;
                    aVar.g = (int) b.l;
                    aVar.h = (int) b.r;
                    aVar.i = (int) b.n;
                    aVar.j = (int) b.q;
                    aVar.k = (int) b.m;
                } else if (k.e(a)) {
                    aVar.f = (int) b.x;
                    aVar.g = (int) b.t;
                    aVar.h = (int) b.z;
                    aVar.i = (int) b.v;
                    aVar.j = (int) b.y;
                    aVar.k = (int) b.u;
                }
            }
            if (k.d(a)) {
                aVar.a = q.d(context);
                aVar.c = q.a(context);
                if (z2) {
                    aVar.d = q.a(context, 1);
                }
                aVar.e = q.a();
            } else if (k.c(a)) {
                aVar.a = i.c(context);
                aVar.c = i.a();
            }
            a(aVar);
        } catch (Exception e) {
            h.a("getNetworkQueryInfo exception:" + e.getMessage());
        }
        return aVar;
    }

    private static void a(final C0028a aVar) {
        com.tencent.mna.a.a((Runnable) new Runnable() {
            public void run() {
                try {
                    f.a(c.QUERY_NETWORK).a("openid", b.f).a("netssid", "" + aVar.a).a("queryNetType", String.valueOf(aVar.b)).a("signal", String.valueOf(aVar.c)).a("routerdelay", String.valueOf(aVar.d)).a("terminals", String.valueOf(aVar.e)).a("sendnum", String.valueOf(aVar.f)).a("recvnum", String.valueOf(aVar.g)).a("snd_drops", String.valueOf(aVar.h)).a("rcv_drops", String.valueOf(aVar.i)).a("snd_errs", String.valueOf(aVar.j)).a("rcv_errs", String.valueOf(aVar.k)).a("noportsnum", String.valueOf(aVar.l)).a("inerrorsnum", String.valueOf(aVar.m)).a("rcvbuferrornum", String.valueOf(aVar.n)).a("sndbuferrornum", String.valueOf(aVar.o)).a("edgedelay", String.valueOf(aVar.p)).a("edgeip", "" + aVar.q).a("mobileType", String.valueOf(aVar.r)).g();
                } catch (Throwable th) {
                    h.a("reportQueryNetworkEvent failed, exception:" + th.getMessage());
                }
            }
        });
    }

    /* renamed from: com.tencent.mna.b.e.a$a  reason: collision with other inner class name */
    /* compiled from: NetworkQuery */
    public static class C0028a {
        public String a = "";
        public int b = -1;
        public int c = -1;
        public int d = -1;
        public int e = -1;
        public int f = -1;
        public int g = -1;
        public int h = -1;
        public int i = -1;
        public int j = -1;
        public int k = -1;
        public int l = -1;
        public int m = -1;
        public int n = -1;
        public int o = -1;
        public int p = -1;
        public String q = "";
        public int r = 0;

        public static int a(int i2) {
            if (i2 == 0) {
                return 0;
            }
            if (i2 == 4) {
                return 1;
            }
            return 2;
        }
    }
}
