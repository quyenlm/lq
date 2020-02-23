package com.tencent.bugly.crashreport.crash;

import android.content.Context;
import android.os.Build;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.common.info.AppInfo;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.common.strategy.a;
import com.tencent.bugly.crashreport.crash.anr.b;
import com.tencent.bugly.crashreport.crash.jni.NativeCrashHandler;
import com.tencent.bugly.proguard.o;
import com.tencent.bugly.proguard.p;
import com.tencent.bugly.proguard.r;
import com.tencent.bugly.proguard.u;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.z;
import com.tencent.imsdk.expansion.downloader.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: BUGLY */
public final class c {
    public static int a = 0;
    public static boolean b = false;
    public static int c = 2;
    public static boolean d = true;
    public static int e = 20480;
    public static int f = 20480;
    public static long g = 604800000;
    public static String h = null;
    public static boolean i = false;
    public static String j = null;
    public static int k = 5000;
    public static boolean l = true;
    public static boolean m = false;
    public static String n = null;
    public static String o = null;
    private static c r;
    public final b p;
    /* access modifiers changed from: private */
    public final Context q;
    /* access modifiers changed from: private */
    public final e s;
    private final NativeCrashHandler t;
    private a u = a.a();
    private w v;
    private final b w;
    private Boolean x;

    private c(int i2, Context context, w wVar, boolean z, BuglyStrategy.a aVar, o oVar, String str) {
        a = i2;
        Context a2 = z.a(context);
        this.q = a2;
        this.v = wVar;
        this.p = new b(i2, a2, u.a(), p.a(), this.u, aVar, oVar);
        com.tencent.bugly.crashreport.common.info.a a3 = com.tencent.bugly.crashreport.common.info.a.a(a2);
        this.s = new e(a2, this.p, this.u, a3);
        this.t = NativeCrashHandler.getInstance(a2, a3, this.p, this.u, wVar, z, str);
        a3.D = this.t;
        this.w = new b(a2, this.u, a3, wVar, this.p);
    }

    public static synchronized c a(int i2, Context context, boolean z, BuglyStrategy.a aVar, o oVar, String str) {
        c cVar;
        synchronized (c.class) {
            if (r == null) {
                r = new c(1004, context, w.a(), z, aVar, (o) null, (String) null);
            }
            cVar = r;
        }
        return cVar;
    }

    public static synchronized c a() {
        c cVar;
        synchronized (c.class) {
            cVar = r;
        }
        return cVar;
    }

    public final void a(StrategyBean strategyBean) {
        this.s.a(strategyBean);
        this.t.onStrategyChanged(strategyBean);
        this.w.a(strategyBean);
        w.a().a(new Thread() {
            public final void run() {
                List<CrashDetailBean> list;
                if (z.a(c.this.q, "local_crash_lock", 10000)) {
                    List<CrashDetailBean> a2 = c.this.p.a();
                    if (a2 != null && a2.size() > 0) {
                        x.c("Size of crash list: %s", Integer.valueOf(a2.size()));
                        int size = a2.size();
                        if (((long) size) > 100) {
                            list = new ArrayList<>();
                            Collections.sort(a2);
                            for (int i = 0; ((long) i) < 100; i++) {
                                list.add(a2.get((size - 1) - i));
                            }
                        } else {
                            list = a2;
                        }
                        c.this.p.a(list, 0, false, false, false);
                    }
                    z.b(c.this.q, "local_crash_lock");
                }
            }
        }, 3000);
    }

    public final boolean b() {
        Boolean bool = this.x;
        if (bool != null) {
            return bool.booleanValue();
        }
        String str = com.tencent.bugly.crashreport.common.info.a.b().d;
        List<r> a2 = p.a().a(1);
        ArrayList arrayList = new ArrayList();
        if (a2 == null || a2.size() <= 0) {
            this.x = false;
            return false;
        }
        for (r next : a2) {
            if (str.equals(next.c)) {
                this.x = true;
                arrayList.add(next);
            }
        }
        if (arrayList.size() > 0) {
            p.a().a((List<r>) arrayList);
        }
        return true;
    }

    public final synchronized void c() {
        this.s.a();
        this.t.setUserOpened(true);
        if (Build.VERSION.SDK_INT <= 19) {
            this.w.a(true);
        } else {
            this.w.c();
        }
    }

    public final synchronized void d() {
        this.s.b();
        this.t.setUserOpened(false);
        if (Build.VERSION.SDK_INT < 19) {
            this.w.a(false);
        } else {
            this.w.d();
        }
    }

    public final void e() {
        this.s.a();
    }

    public final void f() {
        this.t.setUserOpened(false);
    }

    public final void g() {
        this.t.setUserOpened(true);
    }

    public final void h() {
        if (Build.VERSION.SDK_INT <= 19) {
            this.w.a(true);
        } else {
            this.w.c();
        }
    }

    public final void i() {
        if (Build.VERSION.SDK_INT < 19) {
            this.w.a(false);
        } else {
            this.w.d();
        }
    }

    public final synchronized void a(boolean z, boolean z2, boolean z3) {
        this.t.testNativeCrash(z, z2, z3);
    }

    public final synchronized void j() {
        int i2 = 0;
        synchronized (this) {
            b bVar = this.w;
            while (true) {
                int i3 = i2 + 1;
                if (i2 >= 30) {
                    break;
                }
                try {
                    x.a("try main sleep for make a test anr! try:%d/30 , kill it if you don't want to wait!", Integer.valueOf(i3));
                    z.b((long) Constants.ACTIVE_THREAD_WATCHDOG);
                    i2 = i3;
                } catch (Throwable th) {
                    if (!x.a(th)) {
                        th.printStackTrace();
                    }
                }
            }
        }
    }

    public final boolean k() {
        return this.w.a();
    }

    public final void a(Thread thread, Throwable th, boolean z, String str, byte[] bArr, boolean z2) {
        final Thread thread2 = thread;
        final Throwable th2 = th;
        final boolean z3 = z2;
        this.v.a(new Runnable(false, (String) null, (byte[]) null) {
            public final void run() {
                try {
                    x.c("post a throwable %b", Boolean.valueOf(false));
                    c.this.s.a(thread2, th2, false, null, null);
                    if (z3) {
                        x.a("clear user datas", new Object[0]);
                        com.tencent.bugly.crashreport.common.info.a.a(c.this.q).C();
                    }
                } catch (Throwable th) {
                    if (!x.b(th)) {
                        th.printStackTrace();
                    }
                    x.e("java catch error: %s", th2.toString());
                }
            }
        });
    }

    public final void a(CrashDetailBean crashDetailBean) {
        this.p.d(crashDetailBean);
    }

    public final void a(long j2) {
        w.a().a(new Thread() {
            public final void run() {
                List<CrashDetailBean> list;
                if (z.a(c.this.q, "local_crash_lock", 10000)) {
                    List<CrashDetailBean> a2 = c.this.p.a();
                    if (a2 != null && a2.size() > 0) {
                        x.c("Size of crash list: %s", Integer.valueOf(a2.size()));
                        int size = a2.size();
                        if (((long) size) > 100) {
                            list = new ArrayList<>();
                            Collections.sort(a2);
                            for (int i = 0; ((long) i) < 100; i++) {
                                list.add(a2.get((size - 1) - i));
                            }
                        } else {
                            list = a2;
                        }
                        c.this.p.a(list, 0, false, false, false);
                    }
                    z.b(c.this.q, "local_crash_lock");
                }
            }
        }, j2);
    }

    public final void l() {
        this.t.checkUploadRecordCrash();
    }

    public final void m() {
        if (com.tencent.bugly.crashreport.common.info.a.b().d.equals(AppInfo.a(this.q))) {
            this.t.removeEmptyNativeRecordFiles();
        }
    }
}
