package com.tencent.mna.b.a;

import com.tencent.mna.StartSpeedRet;
import com.tencent.mna.b;
import com.tencent.mna.b.a.d;
import com.tencent.mna.b.g.c;
import com.tencent.mna.b.g.d;
import com.tencent.mna.base.a.a;
import com.tencent.mna.base.utils.h;
import com.tencent.mna.base.utils.k;
import java.util.ArrayList;
import java.util.List;

/* compiled from: QosManager */
public class g {
    public static void a(int i, int i2, d.b bVar) {
        List<String> e;
        String d;
        if (a.y()) {
            try {
                if (k.a(b.g()) != 3) {
                    h.a("[N]4G-QOS: 未启动, 当前非4G网络");
                    return;
                }
                ArrayList arrayList = new ArrayList(com.tencent.mna.a.b.c());
                if (arrayList.size() > 0) {
                    String c = a.c();
                    if (c != null && !c.equals("0.0.0.0")) {
                        arrayList.add(c);
                    }
                    f k = b.k();
                    if (!(k == null || (d = k.d()) == null || d.equals("0.0.0.0"))) {
                        arrayList.add(d);
                    }
                    com.tencent.mna.b.b.b m = b.m();
                    if (!(m == null || (e = m.e()) == null || e.size() <= 0)) {
                        arrayList.addAll(m.e());
                    }
                } else {
                    arrayList.add("0.0.0.0");
                }
                h.b("[N]4G-QOS：启动, 时延:" + i + ", 信号强度:" + i2 + ", 是否对局中:" + bVar.isInPvp());
                com.tencent.mna.b.f.a.a(i, i2, arrayList, bVar);
            } catch (Exception e2) {
                h.d("[N]4G-QOS failed, exception:" + e2.getMessage());
            }
        } else {
            h.a("[N]4G-QOS未开启");
        }
    }

    public static void a() {
        if (a.y()) {
            try {
                com.tencent.mna.b.f.a.b();
            } catch (Exception e) {
                h.d("stopMobileQos exception:" + e.getMessage());
            }
        }
    }

    public static d.a a(int i) {
        List<String> e;
        if (!StartSpeedRet.isCanHook(i) || a.U() <= 0) {
            h.a("[N]路由器-QOS未开启");
        } else {
            try {
                if (k.a(b.g()) != 4) {
                    h.a("[N]路由器-QOS: 未启动, 当前非WiFi网络");
                    return null;
                }
                com.tencent.mna.b.g.d.a(a.U(), a.T(), a.R(), a.Y(), a.Z(), a.aa(), a.ab(), a.ac(), a.ad(), a.V(), a.W(), a.X(), a.S(), a.ae(), a.af(), a.ag(), a.ah(), a.ai(), a.aj(), a.ak(), a.al());
                ArrayList arrayList = new ArrayList();
                int b = com.tencent.mna.a.b.b();
                List<String> c = com.tencent.mna.a.b.c();
                if (c != null && c.size() > 0) {
                    int size = c.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        arrayList.add(c.get(i2) + ":" + b);
                    }
                    f k = b.k();
                    if (k != null) {
                        int e2 = k.e();
                        String d = k.d();
                        if (d != null && !d.equals("0.0.0.0")) {
                            arrayList.add(d + ":" + e2);
                        }
                    }
                    com.tencent.mna.b.b.b m = b.m();
                    if (!(m == null || (e = m.e()) == null || e.size() <= 0)) {
                        int size2 = e.size();
                        for (int i3 = 0; i3 < size2; i3++) {
                            arrayList.add(e.get(i3) + ":" + b);
                        }
                    }
                }
                return com.tencent.mna.b.g.d.a((List<String>) arrayList);
            } catch (Exception e3) {
                h.d("[N]路由器-QOS failed, exception:" + e3.getMessage());
            }
        }
        return null;
    }

    public static int b() {
        if (a.U() <= 0) {
            return -100;
        }
        try {
            c d = com.tencent.mna.b.g.d.d();
            if (d != null) {
                return d.a;
            }
            return -100;
        } catch (Exception e) {
            h.d("stopRouterQos exception:" + e.getMessage());
            return -100;
        }
    }
}
