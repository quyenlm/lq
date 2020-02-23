package com.tencent.mna.b.d;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.tencent.midas.oversea.api.UnityPayHelper;
import com.tencent.mna.GHObserver;
import com.tencent.mna.KartinRet;
import com.tencent.mna.MNAObserver;
import com.tencent.mna.b.d.d;
import com.tencent.mna.base.d.b;
import com.tencent.mna.base.utils.h;
import com.tencent.mna.base.utils.i;
import com.tencent.mna.base.utils.j;
import com.tencent.mna.base.utils.k;
import com.tencent.mna.base.utils.m;
import com.tencent.mna.base.utils.n;
import com.tencent.mna.base.utils.o;
import com.tencent.mna.base.utils.q;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

/* compiled from: DiagnoseManager */
public class b {
    public static void a() {
        com.tencent.mna.a.c(new Runnable() {
            public void run() {
                try {
                    k.d(com.tencent.mna.b.g());
                    int a = k.a(com.tencent.mna.b.g());
                    h.a("DiagnoseManager init, networkType:" + a);
                    if (!k.b(a)) {
                        int a2 = b.a(1, "0.0.0.0");
                        h.a("DiagnoseManager init, errno:" + a2);
                        if (a2 == 0 && com.tencent.mna.base.a.c.x()) {
                            h.a("DiagnoseManager init, TMSDKWifiHelper init");
                            if (o.a(com.tencent.mna.b.g(), true) == 0) {
                                o.a((double) com.tencent.mna.base.a.c.y(), (double) com.tencent.mna.base.a.c.z(), (double) com.tencent.mna.base.a.c.A());
                            }
                        }
                    }
                } catch (Throwable th) {
                    h.a("DiagnoseManager init failed, exception:" + th.getMessage());
                }
            }
        });
    }

    public static void a(final String str) {
        final String str2 = com.tencent.mna.a.b.h + "_" + (TextUtils.isEmpty(com.tencent.mna.a.b.f) ? "UNKNOWN" : com.tencent.mna.a.b.f);
        if (str == null || !str.startsWith("TESTVALUE")) {
            com.tencent.mna.a.c(new Runnable() {
                public void run() {
                    c cVar = new c(str2, str);
                    try {
                        if (!b.b(cVar, k.a(com.tencent.mna.b.g())) && !b.b(cVar, "0.0.0.0")) {
                            a[] aVarArr = new a[1];
                            if (!b.b(cVar, aVarArr)) {
                                a aVar = aVarArr[0];
                                if (!b.b(cVar, aVar)) {
                                    int a2 = k.a(com.tencent.mna.b.g());
                                    if (!b.b(cVar, a2)) {
                                        String d = q.d(com.tencent.mna.b.g());
                                        if (!b.b(cVar, aVar, a2)) {
                                            int a3 = k.a(com.tencent.mna.b.g());
                                            if (!b.b(cVar, a2, d)) {
                                                cVar.a(a3, k.a(com.tencent.mna.b.g(), a3));
                                                b.b(cVar);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (Throwable th) {
                        h.a("DiagnoseManager queryKartin failed, exception:" + th.getMessage());
                        b.b(cVar, cVar.d());
                    }
                }
            });
        } else {
            a(str2, str);
        }
    }

    private static void a(final String str, final String str2) {
        h.a("DiagnoseManager queryKartin(" + str2 + "), queryKartin4test");
        com.tencent.mna.a.c(new Runnable() {
            public void run() {
                e eVar = new e(str, str2);
                try {
                    b.b(eVar);
                } catch (Throwable th) {
                    h.a("DiagnoseManager queryKartin4test, exception:" + th.getMessage());
                    b.b((c) eVar, eVar.d());
                }
            }
        });
    }

    public static int a(int i, String str) {
        String a2 = m.a(com.tencent.mna.b.g(), com.tencent.mna.base.a.c.I());
        String d2 = q.d(com.tencent.mna.b.g());
        String d3 = m.d(com.tencent.mna.b.g());
        int a3 = k.a(com.tencent.mna.b.g());
        JSONObject a4 = com.tencent.mna.base.a.c.a(com.tencent.mna.a.b.h, com.tencent.mna.a.b.f, "2000", Build.MODEL, a2, d2, d3, str, a3, com.tencent.mna.a.b.e);
        JSONObject a5 = com.tencent.mna.base.a.c.a(com.tencent.mna.a.b.h, com.tencent.mna.a.b.f, "3000", Build.MODEL, a2, d2, d3, str, a3, com.tencent.mna.a.b.e);
        h.a(" dgn reqJson = " + a4);
        int i2 = -1;
        int i3 = i + 1;
        while (i2 != 0) {
            int i4 = i3 - 1;
            if (i3 <= 0) {
                break;
            }
            i2 = com.tencent.mna.base.a.c.a(a4, a5);
            i3 = i4;
        }
        return i2;
    }

    /* access modifiers changed from: private */
    public static void b(c cVar, final KartinRet kartinRet) {
        if (kartinRet == null) {
            h.d("DiagnoseManager queryKartin result is null");
            return;
        }
        h.a("DiagnoseManager queryKartin(" + kartinRet.tag + "), notifyQueryResult, result:" + kartinRet);
        if (kartinRet.flag == 0) {
            h.b("GSDKQueryKartin succeed");
        } else {
            h.b("GSDKQueryKartin fail");
        }
        if (com.tencent.mna.base.a.c.k()) {
            Context g = com.tencent.mna.b.g();
            cVar.a(q.d(g) + '_' + q.i(g), i.c(g));
        }
        final MNAObserver b = com.tencent.mna.b.b();
        h.a("DiagnoseManager queryKartin(" + kartinRet.tag + "), notifyQueryResult notify");
        com.tencent.mna.a.b(new Runnable() {
            public void run() {
                try {
                    if (b != null) {
                        b.OnQueryKartinNotify(kartinRet.tag, kartinRet.flag, kartinRet.desc, kartinRet.jump_network, kartinRet.jump_signal, kartinRet.jump_router, kartinRet.router_status, kartinRet.router_desc, kartinRet.jump_export, kartinRet.export_status, kartinRet.export_desc, kartinRet.jump_terminal, kartinRet.terminal_status, kartinRet.terminal_desc, kartinRet.jump_proxy, kartinRet.jump_edge, kartinRet.signal_desc, kartinRet.signal_status, kartinRet.jump_direct, kartinRet.direct_status, kartinRet.direct_desc, kartinRet.netinfo_status, kartinRet.netinfo_desc, kartinRet.wifi_num);
                    } else {
                        com.tencent.mna.base.jni.e.i("OnQueryKartinNotify:" + kartinRet.tag + "_" + kartinRet.flag + "_" + kartinRet.desc + "_" + kartinRet.jump_network + "_" + kartinRet.jump_signal + "_" + kartinRet.jump_router + "_" + kartinRet.router_status + "_" + kartinRet.router_desc + "_" + kartinRet.jump_export + "_" + kartinRet.export_status + "_" + kartinRet.export_desc + "_" + kartinRet.jump_terminal + "_" + kartinRet.terminal_status + "_" + kartinRet.terminal_desc + "_" + kartinRet.jump_proxy + "_" + kartinRet.jump_edge + "_" + kartinRet.signal_desc + "_" + kartinRet.signal_status + "_" + kartinRet.jump_direct + "_" + kartinRet.direct_status + "_" + kartinRet.direct_desc + "_" + kartinRet.netinfo_status + "_" + kartinRet.netinfo_desc + "_" + kartinRet.wifi_num);
                    }
                } catch (Throwable th) {
                }
            }
        });
        final GHObserver c2 = com.tencent.mna.b.c();
        com.tencent.mna.a.b(new Runnable() {
            public void run() {
                try {
                    if (c2 != null) {
                        c2.OnQueryKartinNotify(kartinRet);
                    } else {
                        com.tencent.mna.base.jni.e.i("OnQueryKartinNotify:" + kartinRet.tag + "_" + kartinRet.flag + "_" + kartinRet.desc + "_" + kartinRet.jump_network + "_" + kartinRet.jump_signal + "_" + kartinRet.jump_router + "_" + kartinRet.router_status + "_" + kartinRet.router_desc + "_" + kartinRet.jump_export + "_" + kartinRet.export_status + "_" + kartinRet.export_desc + "_" + kartinRet.jump_terminal + "_" + kartinRet.terminal_status + "_" + kartinRet.terminal_desc + "_" + kartinRet.jump_proxy + "_" + kartinRet.jump_edge + "_" + kartinRet.signal_desc + "_" + kartinRet.signal_status + "_" + kartinRet.jump_direct + "_" + kartinRet.direct_status + "_" + kartinRet.direct_desc + "_" + kartinRet.netinfo_status + "_" + kartinRet.netinfo_desc + "_" + kartinRet.wifi_num);
                    }
                } catch (Throwable th) {
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public static boolean b(c cVar, int i) {
        KartinRet b;
        h.a("DiagnoseManager queryKartin(" + cVar.a + "), checkNetworkType");
        if (!k.b(i)) {
            return false;
        }
        if (i == 0) {
            h.a("DiagnoseManager queryKartin(" + cVar.a + "), checkNetworkType no net");
            b = cVar.a();
        } else {
            h.a("DiagnoseManager queryKartin(" + cVar.a + "), checkNetworkType 2g");
            b = cVar.b(k.a(com.tencent.mna.b.g(), 1));
        }
        b(cVar, b);
        return true;
    }

    /* access modifiers changed from: private */
    public static boolean b(c cVar, String str) {
        h.a("DiagnoseManager queryKartin(" + cVar.a + "), requestConfig");
        int a2 = a(0, str);
        if (a2 != 0) {
            h.a("DiagnoseManager queryKartin(" + cVar.a + "), requestConfig errno:" + a2);
            b(cVar, cVar.a(false));
            return true;
        }
        cVar.a(com.tencent.mna.base.a.c.H(), com.tencent.mna.base.a.c.o(), com.tencent.mna.base.a.c.p(), com.tencent.mna.base.a.c.a(), com.tencent.mna.base.a.c.c(), com.tencent.mna.base.a.c.h(), com.tencent.mna.base.a.c.C());
        return false;
    }

    /* access modifiers changed from: private */
    public static boolean b(c cVar, a[] aVarArr) {
        h.a("DiagnoseManager queryKartin(" + cVar.a + "), createDgnSpeedTester");
        aVarArr[0] = com.tencent.mna.c.a.b(com.tencent.mna.base.a.c.h());
        if (aVarArr[0] != null) {
            return false;
        }
        h.a("DiagnoseManager queryKartin(" + cVar.a + "), dgnSpeedTester is null");
        b(cVar, cVar.b());
        return true;
    }

    /* access modifiers changed from: private */
    public static boolean b(c cVar, a aVar) {
        h.a("DiagnoseManager queryKartin(" + cVar.a + "), prepareForDiagnose");
        int a2 = aVar.a();
        cVar.a(a2);
        if (a2 == 0) {
            return false;
        }
        h.a("DiagnoseManager queryKartin(" + cVar.a + "), prepareForDiagnose errno:" + a2);
        b(cVar, cVar.a(true));
        return true;
    }

    /* access modifiers changed from: private */
    public static boolean b(c cVar, a aVar, int i) {
        ExecutorService newFixedThreadPool;
        h.a("DiagnoseManager queryKartin(" + cVar.a + "), diagnose");
        d dVar = new d(i);
        e eVar = new e();
        c cVar2 = new c();
        a aVar2 = new a();
        C0027b bVar = new C0027b(aVar, i);
        dVar.a();
        if (i == 4) {
            newFixedThreadPool = Executors.newFixedThreadPool(3);
            newFixedThreadPool.execute(eVar);
            newFixedThreadPool.execute(cVar2);
            newFixedThreadPool.execute(aVar2);
            newFixedThreadPool.execute(bVar);
        } else {
            newFixedThreadPool = Executors.newFixedThreadPool(2);
            newFixedThreadPool.execute(aVar2);
            newFixedThreadPool.execute(bVar);
        }
        newFixedThreadPool.shutdown();
        try {
            if (!newFixedThreadPool.awaitTermination(20, TimeUnit.SECONDS)) {
                try {
                    newFixedThreadPool.shutdownNow();
                } catch (Exception e2) {
                }
            }
        } catch (InterruptedException e3) {
            try {
                newFixedThreadPool.shutdownNow();
            } catch (Exception e4) {
            }
        }
        dVar.b();
        cVar.a(i, dVar.c(), eVar.a, eVar.b, cVar2.a(), aVar2.a(), aVar2.b(), aVar2.c(), bVar.e, bVar.f, bVar.a(), bVar.c, bVar.d);
        return false;
    }

    /* access modifiers changed from: private */
    public static boolean b(c cVar, int i, String str) {
        h.a("DiagnoseManager queryKartin(" + cVar.a + "), checkNetworkChange");
        int a2 = k.a(com.tencent.mna.b.g());
        String d2 = q.d(com.tencent.mna.b.g());
        if (a2 != i || !str.equals(d2)) {
            b(cVar, cVar.c());
            return true;
        }
        h.a("DiagnoseManager queryKartin(" + cVar.a + "), checkNetworkChange network changeless");
        return false;
    }

    /* access modifiers changed from: private */
    public static void b(c cVar) {
        h.a("DiagnoseManager queryKartin(" + cVar.a + "), prepareQueryResult");
        c cVar2 = cVar;
        b(cVar, cVar2.a(com.tencent.mna.base.a.c.x(), com.tencent.mna.base.a.c.B(), com.tencent.mna.base.a.c.J(), com.tencent.mna.base.a.c.L(), com.tencent.mna.base.a.c.N(), com.tencent.mna.base.a.c.K(), com.tencent.mna.base.a.c.O(), com.tencent.mna.base.a.c.M(), com.tencent.mna.base.a.c.P()));
    }

    /* compiled from: DiagnoseManager */
    private static class d {
        boolean a;
        j.b b;
        j.b c;
        j.b d;

        public d(int i) {
            boolean a2 = d.a(com.tencent.mna.base.a.c.l(), d.a.NIC);
            if (!a2) {
                h.a("diagnose, NIC switch off");
            }
            if (i == 4 && a2) {
                h.a("diagnose, enable collect net data");
                this.a = true;
            }
        }

        /* access modifiers changed from: package-private */
        public void a() {
            if (this.a) {
                h.a("diagnose, collectPreNetData");
                this.b = j.a();
            }
        }

        /* access modifiers changed from: package-private */
        public void b() {
            if (this.a) {
                h.a("diagnose, collectPostNetData");
                this.c = j.a();
            }
        }

        /* access modifiers changed from: package-private */
        public j.b c() {
            if (this.a && this.d == null && this.b != null && this.c != null) {
                h.a("diagnose, getDifNetData");
                this.d = j.a(this.b, this.c);
            }
            return this.d;
        }
    }

    /* compiled from: DiagnoseManager */
    private static class e implements Runnable {
        int a;
        int b;

        private e() {
            this.a = -2;
            this.b = -10;
        }

        public void run() {
            try {
                if (!d.a(com.tencent.mna.base.a.c.l(), d.a.Ping)) {
                    h.a("diagnose, Ping switch off");
                    return;
                }
                this.a = q.a(com.tencent.mna.b.g(), com.tencent.mna.base.a.c.n());
                if (com.tencent.mna.base.a.c.i() == 1) {
                    this.b = n.b("www.qq.com", com.tencent.mna.base.a.c.n());
                }
                h.a(String.format(com.tencent.mna.a.a.a, "diagnose ping %d, ping next %d", new Object[]{Integer.valueOf(this.a), Integer.valueOf(this.b)}));
            } catch (Exception e) {
                h.a("PingGateWayTask run exception:" + e.getMessage());
            }
        }
    }

    /* compiled from: DiagnoseManager */
    private static class c implements Runnable {
        q.a a;

        private c() {
        }

        public void run() {
            try {
                if (!d.a(com.tencent.mna.base.a.c.l(), d.a.RouterMacs)) {
                    h.a("diagnose, RouterMacs switch off");
                    return;
                }
                try {
                    this.a = q.b(com.tencent.mna.b.g());
                } catch (Exception e) {
                    h.a("diagnose, getRouterInfo exception:" + e.getMessage());
                }
            } catch (Exception e2) {
                h.a("GetRouterInfoTask run exception:" + e2.getMessage());
            }
        }

        /* access modifiers changed from: package-private */
        public q.a a() {
            return this.a != null ? this.a : new q.a(-1);
        }
    }

    /* compiled from: DiagnoseManager */
    private static class a implements Runnable {
        boolean a = d.a(com.tencent.mna.base.a.c.l(), d.a.Direct);
        b.a b;
        int c;
        int d;

        public void run() {
            try {
                if (!this.a) {
                    h.a("diagnose, Direct switch off");
                } else {
                    com.tencent.mna.base.d.b.a(com.tencent.mna.base.a.c.o(), com.tencent.mna.base.a.c.p(), com.tencent.mna.base.a.c.a(), com.tencent.mna.base.a.c.b(), com.tencent.mna.base.a.c.r(), com.tencent.mna.base.a.c.s(), com.tencent.mna.base.a.c.t(), com.tencent.mna.base.a.c.u(), (float) com.tencent.mna.base.a.c.v(), com.tencent.mna.base.a.c.w(), com.tencent.mna.base.a.c.q(), com.tencent.mna.base.a.c.e(), com.tencent.mna.base.a.c.f(), com.tencent.mna.base.a.c.g());
                }
            } catch (Exception e) {
                h.a("DirectSpeedTestTask run exception:" + e.getMessage());
            }
        }

        /* access modifiers changed from: package-private */
        public b.a a() {
            if (this.a) {
                this.b = com.tencent.mna.base.d.b.c();
            }
            return this.b;
        }

        public int b() {
            if (this.a) {
                this.c = com.tencent.mna.base.d.b.a();
            }
            return this.c;
        }

        public int c() {
            if (this.a) {
                this.d = com.tencent.mna.base.d.b.b();
            }
            return this.d;
        }
    }

    /* renamed from: com.tencent.mna.b.d.b$b  reason: collision with other inner class name */
    /* compiled from: DiagnoseManager */
    private static class C0027b implements Runnable {
        a a;
        int b;
        int c = 0;
        String d = UnityPayHelper.AP_MIDAS_RESP_RESULT_ERROR;
        int e = 0;
        int f;
        StringBuilder g;

        public C0027b(a aVar, int i) {
            this.a = aVar;
            this.b = i;
            this.g = new StringBuilder();
        }

        public void run() {
            int i;
            boolean z = true;
            try {
                if (!d.a(com.tencent.mna.base.a.c.l(), d.a.Export)) {
                    h.a("diagnose, Export switch off");
                    return;
                }
                int b2 = com.tencent.mna.base.jni.e.b(500);
                if (this.a != null) {
                    if (this.a.a(b2) == -2) {
                        z = false;
                    }
                    if (com.tencent.mna.base.a.c.j() == 0 || z) {
                        if (this.b == 4) {
                            long currentTimeMillis = System.currentTimeMillis();
                            int m = com.tencent.mna.base.a.c.m();
                            i = 0;
                            while (System.currentTimeMillis() - currentTimeMillis < ((long) m)) {
                                int a2 = this.a.a(b2);
                                i += a2;
                                this.g.append(a2).append(',');
                                this.e++;
                                try {
                                    Thread.sleep(50);
                                } catch (InterruptedException e2) {
                                }
                            }
                        } else {
                            int i2 = 0;
                            for (int i3 = 0; i3 < 3; i3++) {
                                i2 = i + this.a.a(b2);
                            }
                            this.e = 3;
                        }
                        if (this.e != 0) {
                            this.f = i / this.e;
                            if (this.b != 4) {
                                this.g.append(this.f).append(',');
                            }
                        }
                    } else {
                        this.d = n.a("www.qq.com");
                        int a3 = n.a(this.d, 10);
                        if (a3 >= 200) {
                            a3 = 200;
                        }
                        this.f = a3;
                        this.c = 1;
                        if (this.d == null || this.d.length() <= 0) {
                            this.d = "-3";
                        }
                        this.g.append(this.f).append(',');
                        this.e = 10;
                    }
                }
                com.tencent.mna.base.jni.e.d(b2);
            } catch (Exception e3) {
                h.a("ExportSpeedTestTask run exception:" + e3.getMessage());
            }
        }

        /* access modifiers changed from: package-private */
        public String a() {
            return this.g.toString();
        }
    }
}
