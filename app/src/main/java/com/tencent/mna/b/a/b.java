package com.tencent.mna.b.a;

import android.content.Context;
import android.os.Build;
import com.tencent.component.net.download.multiplex.download.DownloadDBHelper;
import com.tencent.mna.MNAObserver;
import com.tencent.mna.NetworkBindingListener;
import com.tencent.mna.StartSpeedRet;
import com.tencent.mna.b.a.c.g;
import com.tencent.mna.b.a.d;
import com.tencent.mna.b.g.d;
import com.tencent.mna.base.a.a.b;
import com.tencent.mna.base.c.a;
import com.tencent.mna.base.c.c;
import com.tencent.mna.base.c.d;
import com.tencent.mna.base.c.f;
import com.tencent.mna.base.jni.e;
import com.tencent.mna.base.utils.NetworkChangeReceiver;
import com.tencent.mna.base.utils.h;
import com.tencent.mna.base.utils.i;
import com.tencent.mna.base.utils.j;
import com.tencent.mna.base.utils.k;
import com.tencent.mna.base.utils.l;
import com.tencent.mna.base.utils.m;
import com.tencent.mna.base.utils.q;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* compiled from: AccelerateManager */
public class b {
    /* access modifiers changed from: private */
    public static f a = null;
    /* access modifiers changed from: private */
    public static d b = null;
    /* access modifiers changed from: private */
    public static com.tencent.mna.b.b.b c = null;
    /* access modifiers changed from: private */
    public static int d = -1;
    private static ScheduledExecutorService e = null;
    private static ScheduledFuture<?> f = null;
    private static ScheduledExecutorService g = null;
    private static ScheduledFuture<?> h = null;
    /* access modifiers changed from: private */
    public static com.tencent.mna.base.c.a i = null;

    public static void a() {
        com.tencent.mna.a.a((Runnable) new Runnable() {
            public void run() {
                NetworkChangeReceiver.a(com.tencent.mna.b.g());
            }
        });
    }

    public static void a(String str, int i2, int i3, String str2, int i4, int i5, int i6, String str3) {
        h.b("[N]vip = [" + str + "], vport[" + i2 + "], htype[" + i3 + "], hookModules = [" + str2 + "], zoneid[" + i4 + "], stopMNA[" + i5 + "], 启动超时[" + i6 + "], pvpid[" + str3 + "]");
        if (!com.tencent.mna.b.a() || !com.tencent.mna.a.a()) {
            h.b("MNAStartSpeed fail");
            b(new StartSpeedRet(str, i2, i3, -1, StartSpeedRet.getSpeedDesc(-7)));
        } else if (c.a()) {
            h.b("MNAStartSpeed fail");
            b(new StartSpeedRet(str, i2, i3, 1, StartSpeedRet.getSpeedDesc(1)));
        } else {
            c.a(true);
            c.b(false);
            a(str, i2, "");
            final String str4 = str;
            final int i7 = i2;
            final int i8 = i3;
            final String str5 = str2;
            final int i9 = i4;
            final int i10 = i5;
            final int i11 = i6;
            final String str6 = str3;
            com.tencent.mna.a.a((Runnable) new Runnable() {
                public void run() {
                    StartSpeedRet startSpeedRet = new StartSpeedRet(str4, i7, i8);
                    d a2 = f.a(c.START);
                    try {
                        startSpeedRet.flag = b.b(a2, str4, i7, i8, str5, i9, i10, i11, str6);
                        b.x();
                        int unused = b.d = startSpeedRet.flag;
                        startSpeedRet.desc = StartSpeedRet.getSpeedDesc(startSpeedRet.flag);
                        if (startSpeedRet.flag == 0) {
                            h.b("MNAStartSpeed succeed");
                        } else {
                            h.b("MNAStartSpeed fail");
                        }
                        b.b(startSpeedRet);
                        if (com.tencent.mna.base.a.a.j() == 0) {
                            h.b("startSpeed is shut down: mna eq 0");
                            return;
                        }
                        if (com.tencent.mna.a.b.i) {
                            com.tencent.mna.b.e.b.a(com.tencent.mna.base.a.a.aM(), com.tencent.mna.base.a.a.aN());
                        }
                        int a3 = i.a(com.tencent.mna.base.a.a.aW(), StartSpeedRet.isCanHook(startSpeedRet.flag));
                        h.b("[N]tosFlag:" + a3);
                        b.b(b.i, a3);
                        if (a3 == 0 && b.b != null) {
                            b.b.a(i.a(com.tencent.mna.base.a.a.aW()));
                        }
                        b.b(startSpeedRet.flag, str5);
                        if (com.tencent.mna.base.a.a.aU() || com.tencent.mna.base.a.a.aV()) {
                            j.a(b.i);
                            if (com.tencent.mna.base.a.a.aU()) {
                                j.a(com.tencent.mna.a.b.a(), String.valueOf(i7));
                            }
                        }
                        b.c(str5);
                        b.b(a2);
                        b.d(startSpeedRet.flag);
                        b.b(b.i, g.a(startSpeedRet.flag));
                        b.b(startSpeedRet.flag, b.b, b.i);
                        b.b(a2, startSpeedRet.flag, str6, a3);
                        c.a(false);
                    } catch (Throwable th) {
                        h.a("startSpeed throwable:" + th.getMessage());
                    } finally {
                        c.a(false);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public static int b(d dVar, String str, int i2, int i3, String str2, int i4, int i5, int i6, String str3) {
        long j;
        try {
            long currentTimeMillis = System.currentTimeMillis();
            com.tencent.mna.b.a = currentTimeMillis;
            if (i6 > 0) {
                a(str, i2, i6);
            }
            Context g2 = com.tencent.mna.b.g();
            i = (com.tencent.mna.base.c.a) f.a(c.NORMAL);
            i.a(str3, m.a(com.tencent.mna.b.g(), com.tencent.mna.base.a.a.aX()), "5.5.2_" + com.tencent.mna.a.b.d, com.tencent.mna.a.b.f, currentTimeMillis);
            if (k.b(g2)) {
                return -2;
            }
            if (!c(str, i2)) {
                return -6;
            }
            if (!a(dVar, i4)) {
                return -3;
            }
            i.a(com.tencent.mna.base.a.a.aE());
            b = new d(300, com.tencent.mna.base.a.a.c(), com.tencent.mna.base.a.a.d(), com.tencent.mna.base.a.a.M());
            a = com.tencent.mna.c.a.a(com.tencent.mna.base.a.a.j());
            if (a == null) {
                h.a("startSpeed NullPointer exception: sAccelerator is null");
                return StartSpeedRet.SPEED_GETACCELERATORFAILED;
            }
            h.b("[N]选用协议：" + a.g());
            h c2 = a.c();
            e b2 = a.b();
            if (c2 == null || b2 == null) {
                h.a("startSpeed NullPointer exception: speedTester or udpPtr is null");
                return StartSpeedRet.SPEED_GETACCELERATORFAILED;
            }
            b.a(c2);
            if (!a.a()) {
                return -13;
            }
            a.a(a.b());
            if ((com.tencent.mna.base.a.a.U() > 0 || com.tencent.mna.base.a.a.as() || com.tencent.mna.base.a.a.aW() > 0) && !a.a(i3, str2, b2)) {
                return -5;
            }
            if (i5 != 0) {
                return 2;
            }
            if (a.a(com.tencent.mna.a.b.a(), i2) != 0) {
                return -12;
            }
            long w = (long) com.tencent.mna.base.a.a.w();
            if (i6 > 0 && com.tencent.mna.base.a.a.L() > 0) {
                i6 = com.tencent.mna.base.a.a.L();
            }
            if (i6 > 0) {
                long currentTimeMillis2 = ((long) i6) - (System.currentTimeMillis() - currentTimeMillis);
                if (currentTimeMillis2 <= 1000) {
                    return -9;
                }
                j = currentTimeMillis2 - 300;
                if (w <= j) {
                    j = w;
                }
            } else {
                j = w;
            }
            if (com.tencent.mna.base.a.a.r() == 2) {
                return -16;
            }
            if (com.tencent.mna.base.a.a.r() == 0 && a(b, i, j) <= 0) {
                return -4;
            }
            if (com.tencent.mna.base.a.a.U() <= 0 && !com.tencent.mna.base.a.a.as() && com.tencent.mna.base.a.a.aW() <= 0 && !a.a(i3, str2, b2)) {
                return -5;
            }
            if (c.b()) {
                if (com.tencent.mna.base.a.a.U() <= 0) {
                    a.c();
                }
                return -10;
            } else if (com.tencent.mna.base.a.a.aF() && !com.tencent.mna.base.utils.c.a()) {
                return 10;
            } else {
                a(true);
                return 0;
            }
        } catch (Exception e2) {
            h.a("startSpeed exception:" + e2.getMessage());
            return StartSpeedRet.SPEED_EXCEPTION;
        } catch (Throwable th) {
            h.a("startSpeed throwable:" + th.getMessage());
            return StartSpeedRet.SPEED_THROWABLE;
        }
    }

    public static boolean a(d dVar, int i2) {
        int i3 = com.tencent.mna.a.b.i ? 3 : 2;
        Context g2 = com.tencent.mna.b.g();
        String d2 = q.d(g2);
        int a2 = k.a(g2);
        com.tencent.mna.b.g.d.x = com.tencent.mna.b.g.d.a(d2);
        com.tencent.mna.b.g.d.F = d2;
        String a3 = com.tencent.mna.base.a.a.a(new b.a().a(i2).a(com.tencent.mna.a.b.a()).b(com.tencent.mna.a.b.f).c(m.a(com.tencent.mna.b.g(), com.tencent.mna.base.a.a.aX())).d(m.a()).e(d2).f(com.tencent.mna.b.g.d.x).g(m.d(g2)).b(q.g(g2)).h(q.h(g2)).i(com.tencent.mna.a.b.e).c(a2).d(k.a(g2, a2)).a());
        h.a("  acc reqJson = " + a3);
        int i4 = -1;
        int i5 = i3;
        while (i4 != 0) {
            int i6 = i5 - 1;
            if (i5 <= 0) {
                break;
            }
            i4 = com.tencent.mna.base.a.a.a(com.tencent.mna.a.b.a(), a3);
            i5 = i6;
        }
        a(i4, dVar);
        return i4 == 0;
    }

    public static void a(String str, int i2, final String str2) {
        com.tencent.mna.a.a((Runnable) new Runnable() {
            public void run() {
                try {
                    if (com.tencent.mna.base.a.a.j() == 0) {
                        h.b("endSpeed is shut down: mna eq 0");
                        return;
                    }
                    if (com.tencent.mna.base.a.a.aU()) {
                        j.a();
                    }
                    b.x();
                    b.z();
                    if (b.a != null) {
                        f.a(c.END).a("pvpid", String.valueOf(com.tencent.mna.a.b.a)).a("openid", String.valueOf(com.tencent.mna.a.b.f)).a("timestamp", String.valueOf(System.currentTimeMillis())).a("tasknormal", String.valueOf(b.v())).g();
                        b.b(b.i, b.a.f());
                        if (!a.c()) {
                            h.c("endSpeed unhookByType failed");
                        }
                        if (!a.d()) {
                            h.c("endSpeed unhookClose failed");
                        }
                        if (!a.e()) {
                            h.c("endSpeed endFpsByUnhook failed");
                        }
                        b.a.a();
                        b.b(b.i, a.C0030a.ROUT_END, String.valueOf(g.b()));
                        g.a();
                        f unused = b.a = null;
                        h.b("MNAEndSpeed succeed");
                    } else {
                        h.b("MNAEndSpeed fail");
                        h.a("endSpeed release accelerator failed, accelerator is null");
                    }
                    if (b.b != null) {
                        b.b.g();
                        d unused2 = b.b = null;
                    }
                    if (b.c != null) {
                        b.c.b(com.tencent.mna.b.g());
                        com.tencent.mna.b.b.b unused3 = b.c = null;
                    }
                    if (com.tencent.mna.a.b.i) {
                        com.tencent.mna.b.e.b.a();
                    }
                    if (com.tencent.mna.base.a.a.aU() || com.tencent.mna.base.a.a.aV()) {
                        j.b();
                    }
                    b.b(true, str2);
                    b.w();
                    h.b("[N]结束加速成功");
                } catch (Throwable th) {
                    h.b("MNAEndSpeed fail");
                    h.a("endSpeed throwable:" + th.getMessage());
                }
            }
        });
    }

    public static void b() {
        try {
            e.b(true);
            if (i != null) {
                i.a(a.C0030a.LOADMAPTIME, String.valueOf(System.currentTimeMillis()));
            }
            h.b("MNAEnterMapLoading succeed");
        } catch (Exception e2) {
            h.b("MNAEnterMapLoading fail");
            h.a("enterMapLoading exception:" + e2.getMessage());
        }
    }

    public static void a(String str, int i2) {
        try {
            a(false);
            c.b(true);
            if (a != null) {
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            b.a.a();
                        } catch (Throwable th) {
                        }
                    }
                }).start();
            } else {
                h.a("stopMNA no need to end accelerator, which is null");
            }
            h.b("[N]MNAStopMNA succeed");
        } catch (Exception e2) {
            h.b("[N]MNAStopMNA fail, exception:" + e2.getMessage());
        }
    }

    public static g b(String str, int i2) {
        return null;
    }

    public static int c() {
        return d;
    }

    public static boolean d() {
        return c.a;
    }

    public static void a(boolean z) {
        c.a = z;
        e.c(z);
    }

    public static void e() {
        c.c = false;
        a(i, false);
    }

    public static void f() {
        c.c = true;
        a(i, true);
    }

    public static void b(boolean z) {
        com.tencent.mna.a.b.i = z;
    }

    public static void g() {
        c.d = true;
        com.tencent.mna.a.a((Runnable) new Runnable() {
            public void run() {
                try {
                    b.b(false, "");
                } catch (Throwable th) {
                }
            }
        });
    }

    public static void h() {
        c.d = false;
    }

    public static boolean a(String str, String str2, String str3) {
        if (i != null) {
            try {
                com.tencent.mna.b.a.c.e f2 = i.f();
                if (f2 != null) {
                    f2.a(str);
                    f2.b(str2);
                    f2.c(str3);
                    return true;
                }
            } catch (Throwable th) {
                h.a("addEngineData throwable:" + th.getMessage());
            }
        }
        return false;
    }

    public static void a(final String str) {
        if (com.tencent.mna.base.a.a.ar() && com.tencent.mna.base.a.a.j() == 1 && d()) {
            com.tencent.mna.a.a((Runnable) new Runnable() {
                public void run() {
                    try {
                        b.c(str, -1);
                    } catch (Throwable th) {
                    }
                }
            });
        }
    }

    public static boolean i() {
        return c.c && !c.d;
    }

    public static int j() {
        return c.b;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0020, code lost:
        r0 = -12;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized int c(boolean r3) {
        /*
            r0 = 1
            java.lang.Class<com.tencent.mna.b.a.b> r1 = com.tencent.mna.b.a.b.class
            monitor-enter(r1)
            com.tencent.mna.b.b.b r2 = c     // Catch:{ Exception -> 0x001f, all -> 0x002c }
            if (r2 == 0) goto L_0x0029
            com.tencent.mna.b.a.d r2 = b     // Catch:{ Exception -> 0x001f, all -> 0x002c }
            if (r2 == 0) goto L_0x0018
            com.tencent.mna.b.a.d r2 = b     // Catch:{ Exception -> 0x001f, all -> 0x002c }
            boolean r2 = r2.a((boolean) r3)     // Catch:{ Exception -> 0x001f, all -> 0x002c }
            if (r2 != 0) goto L_0x0018
            r0 = -11
        L_0x0016:
            monitor-exit(r1)
            return r0
        L_0x0018:
            if (r3 == 0) goto L_0x0023
            r2 = 1
            c((int) r2)     // Catch:{ Exception -> 0x001f, all -> 0x002c }
            goto L_0x0016
        L_0x001f:
            r0 = move-exception
            r0 = -12
            goto L_0x0016
        L_0x0023:
            r0 = 2
            c((int) r0)     // Catch:{ Exception -> 0x001f, all -> 0x002c }
            r0 = 0
            goto L_0x0016
        L_0x0029:
            int r0 = com.tencent.mna.b.a.c.e     // Catch:{ Exception -> 0x001f, all -> 0x002c }
            goto L_0x0016
        L_0x002c:
            r0 = move-exception
            monitor-exit(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.mna.b.a.b.c(boolean):int");
    }

    public static void a(final int i2, final boolean z) {
        com.tencent.mna.a.a((Runnable) new Runnable() {
            public void run() {
                int i = 1;
                try {
                    long currentTimeMillis = System.currentTimeMillis();
                    int a2 = b.b(i2, z, b.c, b.b);
                    boolean z = b.j() != 1;
                    if (a2 == 1 || a2 == 0) {
                        a2 = b.c(z);
                    }
                    if (com.tencent.mna.base.a.a.aV()) {
                        j.a(a2, b.j());
                    }
                    NetworkBindingListener f = com.tencent.mna.b.f();
                    if (f != null) {
                        f.onBinding(a2, z);
                    } else {
                        e.i("onBinding:" + a2 + "_" + z);
                    }
                    h.a("onBinding:" + a2 + "_" + z);
                    StringBuilder sb = new StringBuilder();
                    StringBuilder append = sb.append(currentTimeMillis).append('_').append(i2).append('_');
                    if (!z) {
                        i = 0;
                    }
                    append.append(i).append('_').append(a2);
                    if (b.i != null) {
                        b.i.a(a.C0030a.W2M_FLAG, sb.toString());
                    }
                } catch (Throwable th) {
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public static int b(int i2, boolean z, com.tencent.mna.b.b.b bVar, d dVar) {
        int b2 = b(i2, z);
        return b2 < 0 ? b2 : a(bVar, dVar);
    }

    private static int b(int i2, boolean z) {
        if (!z) {
            return -1;
        }
        if (i2 < com.tencent.mna.base.a.a.aB()) {
            return -2;
        }
        return 0;
    }

    private static int a(com.tencent.mna.b.b.b bVar, d dVar) {
        if (bVar == null) {
            return c.e;
        }
        if (dVar == null) {
            return -3;
        }
        if (!k.c(com.tencent.mna.b.g()) || !com.tencent.mna.base.a.a.as()) {
            return -4;
        }
        if (j() == 1) {
            if (com.tencent.mna.base.a.a.aA() <= 0) {
                return -8;
            }
            if (q.a(com.tencent.mna.b.g()) <= com.tencent.mna.base.a.a.ay()) {
                return -9;
            }
            int e2 = dVar.e();
            int e3 = dVar.e();
            if (e2 <= 0 || e2 >= com.tencent.mna.base.a.a.az() || e3 <= 0 || e3 >= com.tencent.mna.base.a.a.az()) {
                return -10;
            }
            return 0;
        } else if (c.f.get() >= com.tencent.mna.base.a.a.aw()) {
            return -5;
        } else {
            if (i.a() <= com.tencent.mna.base.a.a.au()) {
                return -6;
            }
            int e4 = dVar.e();
            int e5 = dVar.e();
            if (e4 <= 0 || e4 >= com.tencent.mna.base.a.a.av() || e5 <= 0 || e5 >= com.tencent.mna.base.a.a.av()) {
                return -7;
            }
            if (com.tencent.mna.base.a.a.aG() && !com.tencent.mna.base.utils.c.a()) {
                return 2;
            }
            c.f.incrementAndGet();
            return 1;
        }
    }

    public static f k() {
        return a;
    }

    public static d l() {
        return b;
    }

    /* access modifiers changed from: private */
    public static boolean v() {
        return (e == null || f == null || !i()) ? false : true;
    }

    private static void c(int i2) {
        c.b = i2;
        switch (i2) {
            case 0:
                e.b();
                return;
            case 1:
                e.c();
                return;
            case 2:
                e.d();
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: private */
    public static void b(int i2, String str) {
        e b2;
        if (StartSpeedRet.isCanHook(i2)) {
            try {
                if (com.tencent.mna.base.a.a.as()) {
                    com.tencent.mna.b.b.b bVar = new com.tencent.mna.b.b.b();
                    c.e = bVar.a(com.tencent.mna.b.g(), com.tencent.mna.a.b.i, StartSpeedRet.isSpeedSucceed(i2), com.tencent.mna.base.a.a.aH());
                    if (c.e == 0) {
                        c = bVar;
                        if (b != null) {
                            b.a(c, com.tencent.mna.base.a.a.aH());
                        }
                        h.a("prepareW2m success");
                    } else {
                        h.a("prepareW2m fail in preparation, errno:" + c.e);
                    }
                } else {
                    c.e = -4;
                    h.a("prepareW2m failed, control not open");
                }
                if (c != null || (a instanceof com.tencent.mna.c.e.a)) {
                    long j = 0;
                    if (!(a == null || (b2 = a.b()) == null)) {
                        j = b2.h;
                    }
                    if (!a.a(str, j)) {
                        h.c("hookClose failed");
                    }
                }
            } catch (Exception e2) {
                c.e = NetworkBindingListener.NB_PREPARE_EXCEPTION;
                h.a("prepareW2m failed, exception:" + e2.getMessage());
            }
        } else {
            h.a("prepareW2m failed, for control or hook");
            c.e = NetworkBindingListener.NB_PREPARE_CONTROL_OR_HOOK_FAIL;
        }
    }

    public static boolean c(String str, int i2) {
        if (str == null || i2 == 0) {
            h.c("[N]游戏vip DNS失败，传入值为空");
            return false;
        }
        if (str.contains("#")) {
            String[] split = str.split("#");
            if (split.length >= 2) {
                str = split[0].trim();
                com.tencent.mna.a.b.b(split[1].trim());
            }
        } else {
            com.tencent.mna.a.b.b(str);
        }
        ArrayList arrayList = new ArrayList();
        if (com.tencent.mna.base.utils.f.d(str)) {
            arrayList.add(str);
        } else {
            List<String> e2 = com.tencent.mna.base.utils.f.e(str);
            if (e2 != null && e2.size() > 0) {
                arrayList.addAll(e2);
            }
        }
        if (arrayList.size() > 0) {
            com.tencent.mna.a.b.a((String) arrayList.get(0));
            com.tencent.mna.a.b.a((List<String>) arrayList);
            e.a((String[]) arrayList.toArray(new String[0]));
            if (i2 > 0) {
                com.tencent.mna.a.b.a(i2);
                e.e(i2);
            }
            b(i, a.C0030a.GAME_IP, com.tencent.mna.a.b.a() + ":" + com.tencent.mna.a.b.b());
            h.b("[N]游戏vip DNS成功, domain:[" + str + "] to ip:" + arrayList.toString());
            return true;
        }
        h.c("[N]游戏vip DNS失败");
        return false;
    }

    /* access modifiers changed from: private */
    public static void b(d dVar) {
        com.tencent.mna.b.a.c.c cVar;
        com.tencent.mna.b.a.c.c cVar2;
        d.b bVar;
        int a2;
        if (b == null) {
            h.a("startCheckAllDelayAndMobileQos NullPointer exception: facade is null");
            return;
        }
        try {
            Context g2 = com.tencent.mna.b.g();
            if (i != null) {
                com.tencent.mna.b.a.c.c d2 = i.d();
                cVar = i.e();
                cVar2 = d2;
            } else {
                cVar = null;
                cVar2 = null;
            }
            if (cVar2 == null || cVar2.c() <= 0 || cVar == null || cVar.c() <= 0) {
                bVar = d.b.STAGE_NOT_CONTINUOUS_SPEED_TEST;
                a2 = b.a(i, bVar, 0, 0, k.a(g2), -2, -1);
            } else {
                bVar = d.b.STAGE_CONTINUOUS_SPEED_TEST;
                a2 = b.a(i, bVar, cVar2.d(), cVar.d(), k.a(g2), -2, -1);
            }
            g.a(a2, i.a(), bVar);
            if (i != null) {
                i.a(a.C0030a.QOSDELAY, String.valueOf(a2));
            }
            if (dVar != null) {
                dVar.a("qosDelay", String.valueOf(a2));
            }
        } catch (Exception e2) {
            h.a("startCheckAllDelayAndMobileQos exception:" + e2.getMessage());
        }
    }

    /* access modifiers changed from: private */
    public static void d(int i2) {
        if (!StartSpeedRet.isSpeedSucceed(i2) && StartSpeedRet.isNegotiateForwardTunnelSucceed(i2)) {
            if (a != null) {
                try {
                    a.a();
                } catch (Exception e2) {
                    h.a("maybeReleaseForwardTunnel exception:" + e2.getMessage());
                }
            } else {
                h.a("maybeReleaseForwardTunnel NullPointer exception: sAccelerator is null");
            }
        }
    }

    /* access modifiers changed from: private */
    public static void w() {
        a(false);
        c(0);
        c.c();
        a.f();
        i.a();
        com.tencent.mna.a.b.e();
        e.e();
        com.tencent.mna.b.g.d.a();
        com.tencent.mna.b.f.a.a();
    }

    private static synchronized void a(final String str, final int i2, int i3) {
        synchronized (b.class) {
            if (i3 <= 0) {
                h.c("startTimerForStopMna input warning: startSpeedTimeoutMills <= 0");
            } else {
                x();
                h.b("startTimerForStopMna() called with: startSpeedTimeoutMills = [" + i3 + "]");
                g = Executors.newScheduledThreadPool(1);
                h = g.schedule(new Runnable() {
                    public void run() {
                        try {
                            Thread.currentThread().setName("mna-start-speed-timeout");
                            h.a("startSpeed timeout, then stop mna");
                            b.a(str, i2);
                        } catch (Throwable th) {
                            h.a("startTimerForStopMna throwable:" + th.getMessage());
                        }
                    }
                }, (long) i3, TimeUnit.MILLISECONDS);
            }
        }
    }

    /* access modifiers changed from: private */
    public static synchronized void x() {
        synchronized (b.class) {
            if (!(g == null || h == null)) {
                h.a("stopTimerForStopMna");
                try {
                    h.cancel(true);
                    g.shutdownNow();
                    h = null;
                    g = null;
                } catch (Exception e2) {
                    h.a("stopTimerForStopMna exception:" + e2.getMessage());
                }
            }
        }
        return;
    }

    private static int a(final d dVar, com.tencent.mna.base.c.a aVar, long j) {
        if (dVar == null || aVar == null) {
            h.a("compareDelay NullPointer exception: facade or reporter is null");
            return 0;
        }
        com.tencent.mna.b.a.c.c d2 = aVar.d();
        com.tencent.mna.b.a.c.c e2 = aVar.e();
        if (d2 == null || e2 == null) {
            h.a("compareDelay NullPointer exception: forwardDelaysInfo or directDelaysInfo is null");
            return 0;
        }
        h.b("[N]启动对比测速 总时长[" + j + "]ms");
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(2);
        newFixedThreadPool.execute(new a(d2, j) {
            /* access modifiers changed from: protected */
            public int a() {
                return dVar.b();
            }
        });
        newFixedThreadPool.execute(new a(e2, j) {
            /* access modifiers changed from: protected */
            public int a() {
                return dVar.a();
            }
        });
        newFixedThreadPool.shutdown();
        try {
            if (!newFixedThreadPool.awaitTermination(j, TimeUnit.MILLISECONDS)) {
                newFixedThreadPool.shutdownNow();
            }
        } catch (Exception e3) {
            newFixedThreadPool.shutdownNow();
        }
        a(i, d2, e2);
        return new com.tencent.mna.b.a.b.e(y()).a(d2, e2);
    }

    private static com.tencent.mna.b.a.b.b y() {
        String O = com.tencent.mna.base.a.a.O();
        int aC = com.tencent.mna.base.a.a.aC();
        h.a("createSpeedComparator xmlVer:" + O + ", smart:" + aC);
        if (!(aC == 0 || O == null || O.length() <= 0 || O.equals("0") || a == null || b == null || i == null)) {
            int a2 = k.a(com.tencent.mna.b.g());
            String d2 = a.d();
            if (a2 == 4 && (aC == 1 || aC == 3)) {
                h.b("[N]选路算法：LR");
                return new com.tencent.mna.b.a.b.d(b, i, O, d2, com.tencent.mna.a.b.a(), com.tencent.mna.base.a.a.c(), true);
            } else if (a2 == 3 && (aC == 2 || aC == 3)) {
                h.b("[N]选路算法：LR");
                return new com.tencent.mna.b.a.b.d(b, i, O, d2, com.tencent.mna.a.b.a(), com.tencent.mna.base.a.a.c(), false);
            }
        }
        h.b("[N]选路算法：Common");
        return new com.tencent.mna.b.a.b.a(com.tencent.mna.base.a.a.m(), com.tencent.mna.base.a.a.n(), com.tencent.mna.base.a.a.o(), com.tencent.mna.base.a.a.p(), com.tencent.mna.base.a.a.q());
    }

    /* access modifiers changed from: private */
    public static void b(int i2, d dVar, com.tencent.mna.base.c.a aVar) {
        z();
        Context g2 = com.tencent.mna.b.g();
        b(i, a.C0030a.FIRST_NET, String.valueOf(k.a(g2)));
        if (com.tencent.mna.base.a.a.H() != 0) {
            b(i, a.C0030a.FIRST_NIC, j.b(j.a()));
        }
        b(i, a.C0030a.BATTERY, String.valueOf(m.a(g2)));
        if (dVar == null || aVar == null) {
            h.a("startDelayTask NullPointer exception: facade or reporter is null");
        } else if (StartSpeedRet.isRequestControlSucceed(i2) && com.tencent.mna.base.a.a.x() != 0) {
            try {
                int D = com.tencent.mna.base.a.a.D();
                dVar.d(com.tencent.mna.base.a.a.N());
                e = Executors.newScheduledThreadPool(1);
                h.a("startDelayTask");
                f = e.scheduleAtFixedRate(new com.tencent.mna.b.a.a.c(dVar, aVar), 0, (long) D, TimeUnit.MILLISECONDS);
            } catch (Exception e2) {
                h.a("startDelayTask exception:" + e2.getMessage());
            }
        }
    }

    /* access modifiers changed from: private */
    public static synchronized void z() {
        synchronized (b.class) {
            if (!(e == null || f == null)) {
                h.a("stopDelayTask");
                try {
                    f.cancel(true);
                    e.shutdown();
                    e = null;
                    f = null;
                } catch (Exception e2) {
                    h.a("stopDelayTask exception:" + e2.getMessage());
                }
            }
        }
        return;
    }

    private static void a(int i2, com.tencent.mna.base.c.d dVar) {
        if (i2 == 0) {
            e.f(com.tencent.mna.base.a.a.K());
        }
        if (dVar != null) {
            dVar.a("ctl_errorno", String.valueOf(i2));
            if (i2 == 0) {
                dVar.a("control_ip", com.tencent.mna.base.a.a.b() + ":" + com.tencent.mna.a.a.g);
                dVar.a("speed_ip", com.tencent.mna.base.a.a.c() + ":" + com.tencent.mna.base.a.a.d());
                dVar.a("ex_client_ip", com.tencent.mna.base.a.a.k());
                dVar.a("plat_flag", String.valueOf(com.tencent.mna.base.a.a.j()));
                dVar.a("pvp", String.valueOf(com.tencent.mna.base.a.a.D()));
                dVar.a("useping", String.valueOf(com.tencent.mna.base.a.a.aO()));
                dVar.a("tos", String.valueOf(com.tencent.mna.base.a.a.aW()));
                if (com.tencent.mna.base.a.a.a() != null) {
                    dVar.a("control", com.tencent.mna.base.a.a.a().toString());
                }
            }
        }
        b(i, a.C0030a.CTL_ERRORNO, String.valueOf(i2));
        if (i2 == 0) {
            b(i, a.C0030a.PLAT_FLAG, String.valueOf(com.tencent.mna.base.a.a.j()));
            b(i, a.C0030a.CONTROL_IP, com.tencent.mna.base.a.a.b() + ":" + com.tencent.mna.a.a.g);
            b(i, a.C0030a.EX_CLIENT_IP, com.tencent.mna.base.a.a.k());
            b(i, a.C0030a.SPEED_IP, com.tencent.mna.base.a.a.c() + ":" + com.tencent.mna.base.a.a.d());
            b(i, a.C0030a.PVP, String.valueOf(com.tencent.mna.base.a.a.D()));
            b(i, a.C0030a.PRE_SMART, String.valueOf(com.tencent.mna.base.a.a.aC()));
            b(i, a.C0030a.USEPING, String.valueOf(com.tencent.mna.base.a.a.aO()));
            b(i, a.C0030a.TOS, String.valueOf(com.tencent.mna.base.a.a.aW()));
        }
    }

    /* access modifiers changed from: private */
    public static void b(StartSpeedRet startSpeedRet) {
        if (startSpeedRet != null) {
            h.b("[N]Startspeed完成，错误码:" + startSpeedRet.flag + ", " + startSpeedRet.desc);
            MNAObserver b2 = com.tencent.mna.b.b();
            if (b2 != null) {
                try {
                    b2.OnStartSpeedNotify(startSpeedRet.htype, startSpeedRet.flag, startSpeedRet.desc);
                } catch (Exception e2) {
                    h.d("notifyStartSpeedResult exception:" + e2.getMessage());
                }
            } else {
                e.i("OnStartSpeedNotify:" + startSpeedRet.htype + "_" + startSpeedRet.flag + "_" + startSpeedRet.desc);
            }
            h.a("OnStartSpeedNotify: " + startSpeedRet.htype + "_" + startSpeedRet.flag + "_" + startSpeedRet.desc);
            b(i, a.C0030a.MNA_FLAG, String.valueOf(startSpeedRet.flag));
            return;
        }
        h.a("notifyStartSpeedResult NullPointer exception: startSpeedRet is null");
    }

    /* access modifiers changed from: private */
    public static void c(String str) {
        try {
            int a2 = com.tencent.mna.base.utils.d.a();
            String str2 = "" + com.tencent.mna.base.a.a.z() + "|" + com.tencent.mna.base.a.a.A() + "|" + com.tencent.mna.base.a.a.B() + "|" + com.tencent.mna.base.a.a.C();
            if (a2 == 1) {
                Class.forName("com.unity3d.player.UnityPlayer").getMethod("UnitySendMessage", new Class[]{String.class, String.class, String.class}).invoke((Object) null, new Object[]{"MnaCallBackGameObejct", "MnaListenerInit", str2});
            } else if (a2 == 3) {
                h.a("ue4 startFps, fps interval:" + com.tencent.mna.base.a.a.B() + ", res:" + a.a(str, com.tencent.mna.base.a.a.B()));
            }
        } catch (Exception e2) {
        }
    }

    /* access modifiers changed from: private */
    public static void b(com.tencent.mna.base.c.a aVar, a.C0030a aVar2, String str) {
        if (aVar != null && str != null) {
            try {
                aVar.a(aVar2, str);
            } catch (Exception e2) {
                h.a("add String Report failed");
            }
        }
    }

    /* access modifiers changed from: private */
    public static void b(com.tencent.mna.base.c.a aVar, com.tencent.mna.b.a.c.a aVar2) {
        if (aVar != null && aVar2 != null) {
            try {
                aVar.a(a.C0030a.MASTERIP, aVar2.a).a(a.C0030a.NEGIP, aVar2.b).a(a.C0030a.PROXYIPS, aVar2.c).a(a.C0030a.PREPARE, String.valueOf(aVar2.f)).a(a.C0030a.TOKEN, String.valueOf(aVar2.g)).a(a.C0030a.CLIENTKEY, String.valueOf(aVar2.h));
                if (aVar2.e == null) {
                    aVar.a(a.C0030a.EXPORTIP, aVar2.d);
                } else {
                    aVar.a(a.C0030a.EXPORTIP, aVar2.d + "_" + aVar2.e.isSameArea + "_" + aVar2.e.isSameIsp + "_" + aVar2.e.status);
                }
            } catch (Exception e2) {
                h.a("add Plat Report failed");
            }
        }
    }

    /* access modifiers changed from: private */
    public static void b(com.tencent.mna.base.c.a aVar, int i2) {
        if (aVar != null) {
            try {
                aVar.a(a.C0030a.TOS_FLAG, String.valueOf(i2));
            } catch (Exception e2) {
                h.a("add Tos Report failed");
            }
        }
    }

    /* access modifiers changed from: private */
    public static void b(com.tencent.mna.base.c.a aVar, d.a aVar2) {
        if (aVar != null && aVar2 != null) {
            try {
                aVar.a(a.C0030a.ROUT_AUTH, String.valueOf(aVar2.b)).a(a.C0030a.ROUT_QOS, String.valueOf(aVar2.c)).a(a.C0030a.ROUT_MULTI, String.valueOf(aVar2.d)).a(a.C0030a.ROUT_RFLAG, String.valueOf(aVar2.e)).a(a.C0030a.ROUT_RVER, String.valueOf(aVar2.f)).a(a.C0030a.ROUT_RPORT, String.valueOf(aVar2.g));
            } catch (Exception e2) {
                h.a("add Router Report failed");
            }
        }
    }

    private static void a(com.tencent.mna.base.c.a aVar, com.tencent.mna.b.a.c.c cVar, com.tencent.mna.b.a.c.c cVar2) {
        if (aVar != null && cVar != null && cVar2 != null) {
            try {
                String a2 = cVar.a("_");
                String a3 = cVar2.a("_");
                h.b("[N]对比测速转发延迟:" + a2);
                h.b("[N]对比测速直连延迟:" + a3);
                aVar.a(a.C0030a.SFORWARD, a2).a(a.C0030a.SDIRECT, a3);
            } catch (Exception e2) {
                h.a("add Router Compare Report failed");
            }
        }
    }

    private static void a(com.tencent.mna.base.c.a aVar, boolean z) {
        if (aVar != null) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(System.currentTimeMillis()).append('_').append(z ? 1 : 0).append('_').append(k.a(com.tencent.mna.b.g()));
                aVar.a(a.C0030a.FRONTBACK, sb.toString());
            } catch (Exception e2) {
                h.a("add FrontBack Report failed");
            }
        }
    }

    /* access modifiers changed from: private */
    public static void b(com.tencent.mna.base.c.d dVar, int i2, String str, int i3) {
        com.tencent.mna.b.a.c.a f2;
        if (dVar != null) {
            Context g2 = com.tencent.mna.b.g();
            long currentTimeMillis = System.currentTimeMillis() - com.tencent.mna.b.a;
            if (!(a == null || (f2 = a.f()) == null)) {
                dVar.a("masterip", f2.a).a("negip", f2.b).a("proxyips", f2.c).a("prepare", String.valueOf(f2.f)).a("token", String.valueOf(f2.g)).a("clientkey", String.valueOf(f2.h));
                if (f2.e == null) {
                    dVar.a("exportip", f2.d);
                } else {
                    dVar.a("exportip", f2.d + "_" + f2.e.isSameArea + "_" + f2.e.isSameIsp + "_" + f2.e.status);
                }
            }
            dVar.a(DownloadDBHelper.FLAG, String.valueOf(i2)).a("devid", String.valueOf(m.a(com.tencent.mna.b.g(), com.tencent.mna.base.a.a.aX()))).a("pvpid", String.valueOf(str)).a("elapse", String.valueOf(currentTimeMillis)).a("openid", String.valueOf(com.tencent.mna.a.b.f)).a("game_ip", com.tencent.mna.a.b.a() + ":" + com.tencent.mna.a.b.b()).a("netype", String.valueOf(k.a(g2))).a("mnaver", "5.5.2_" + com.tencent.mna.a.b.d).a("qos", String.valueOf(com.tencent.mna.b.f.a.d)).a("qossid", String.valueOf(com.tencent.mna.b.f.a.e)).a("qosworked", String.valueOf(com.tencent.mna.b.f.a.a)).a("qosip", String.valueOf(com.tencent.mna.b.f.a.f)).a("mac", String.valueOf(q.d(g2))).a("pcell", String.valueOf(i.c(g2))).a("tos_flag", String.valueOf(i3)).a("cpu_abi", String.valueOf(Build.CPU_ABI));
            dVar.g();
            return;
        }
        h.a("doStartPReport failed, reporter is null");
    }

    /* access modifiers changed from: private */
    public static synchronized void b(boolean z, String str) {
        synchronized (b.class) {
            if (i != null) {
                if (str == null || str.length() <= 0) {
                    b(i, a.C0030a.GAMEEXTRA, "");
                } else {
                    b(i, a.C0030a.GAMEEXTRA, str + ";");
                }
                b(i, a.C0030a.QOS, com.tencent.mna.b.f.a.d);
                b(i, a.C0030a.QOSSID, String.valueOf(com.tencent.mna.b.f.a.e));
                b(i, a.C0030a.QOSWORKED, String.valueOf(com.tencent.mna.b.f.a.a));
                b(i, a.C0030a.QOSIP, String.valueOf(com.tencent.mna.b.f.a.f));
                Context g2 = com.tencent.mna.b.g();
                b(i, a.C0030a.END_NET, String.valueOf(k.a(g2)));
                if (com.tencent.mna.base.a.a.H() == 1) {
                    b(i, a.C0030a.END_NIC, j.b(j.a()));
                }
                int[] b2 = m.b(g2);
                if (b2 != null && b2.length >= 2) {
                    int i2 = b2[0];
                    int i3 = b2[1];
                    b(i, a.C0030a.BATTERY, String.valueOf(i2));
                    b(i, a.C0030a.BATTERY, String.valueOf(i3));
                }
                b(i, a.C0030a.ZONEID, String.valueOf(com.tencent.mna.a.b.h));
                b(i, a.C0030a.PHONE_MAC, q.l(g2));
                b(i, a.C0030a.ALL_MEMORY, String.valueOf(l.c(g2)));
                String d2 = q.d(g2);
                int k = q.k(g2);
                String i4 = q.i(g2);
                b(i, a.C0030a.WMAC, d2 + "_" + k + "_" + i4 + "_" + q.j(g2));
                b(i, a.C0030a.PCELL, i.c(g2));
                double a2 = com.tencent.mna.base.utils.g.a();
                double b3 = com.tencent.mna.base.utils.g.b();
                b(i, a.C0030a.END_GPS_LON, String.valueOf(a2));
                b(i, a.C0030a.END_GPS_LAT, String.valueOf(b3));
                b(i, a.C0030a.INNERIP, k.a(true));
                i.g();
                if (z) {
                    i = null;
                }
            } else {
                h.a("doNormalPReport failed, reporter is null");
            }
        }
    }

    public static com.tencent.mna.b.b.b m() {
        return c;
    }

    /* compiled from: AccelerateManager */
    private static abstract class a implements Runnable {
        private final com.tencent.mna.b.a.c.c a;
        private final long b;

        /* access modifiers changed from: protected */
        public abstract int a();

        a(com.tencent.mna.b.a.c.c cVar, long j) {
            this.a = cVar;
            this.b = j;
        }

        public void run() {
            try {
                Thread.currentThread().setName("mna-continuous-worker");
                long currentTimeMillis = System.currentTimeMillis();
                while (System.currentTimeMillis() - currentTimeMillis < this.b) {
                    if (this.a != null) {
                        this.a.a(a());
                    }
                }
            } catch (Throwable th) {
                h.a("ContinuousWorker throwable:" + th.getMessage());
            }
        }
    }
}
