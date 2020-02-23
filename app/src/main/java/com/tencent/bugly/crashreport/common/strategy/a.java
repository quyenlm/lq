package com.tencent.bugly.crashreport.common.strategy;

import android.content.Context;
import android.os.Parcelable;
import com.tencent.bugly.crashreport.biz.b;
import com.tencent.bugly.proguard.as;
import com.tencent.bugly.proguard.o;
import com.tencent.bugly.proguard.p;
import com.tencent.bugly.proguard.r;
import com.tencent.bugly.proguard.w;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.z;
import java.util.List;
import java.util.Map;

/* compiled from: BUGLY */
public final class a {
    public static int a = 1000;
    private static a b = null;
    /* access modifiers changed from: private */
    public static String h = null;
    private final List<com.tencent.bugly.a> c;
    private final w d;
    private final StrategyBean e;
    /* access modifiers changed from: private */
    public StrategyBean f = null;
    /* access modifiers changed from: private */
    public Context g;

    private a(Context context, List<com.tencent.bugly.a> list) {
        this.g = context;
        this.e = new StrategyBean();
        this.c = list;
        this.d = w.a();
    }

    public static synchronized a a(Context context, List<com.tencent.bugly.a> list) {
        a aVar;
        synchronized (a.class) {
            if (b == null) {
                b = new a(context, list);
            }
            aVar = b;
        }
        return aVar;
    }

    public final void a(long j) {
        this.d.a(new Thread() {
            public final void run() {
                try {
                    Map<String, byte[]> a2 = p.a().a(a.a, (o) null, true);
                    if (a2 != null) {
                        byte[] bArr = a2.get("device");
                        byte[] bArr2 = a2.get("gateway");
                        if (bArr != null) {
                            com.tencent.bugly.crashreport.common.info.a.a(a.this.g).e(new String(bArr));
                        }
                        if (bArr2 != null) {
                            com.tencent.bugly.crashreport.common.info.a.a(a.this.g).d(new String(bArr2));
                        }
                    }
                    a aVar = a.this;
                    a aVar2 = a.this;
                    StrategyBean unused = aVar.f = a.d();
                    if (a.this.f != null && !z.a(a.h) && z.c(a.h)) {
                        a.this.f.r = a.h;
                        a.this.f.s = a.h;
                    }
                } catch (Throwable th) {
                    if (!x.a(th)) {
                        th.printStackTrace();
                    }
                }
                a.this.a(a.this.f, false);
            }
        }, j);
    }

    public static synchronized a a() {
        a aVar;
        synchronized (a.class) {
            aVar = b;
        }
        return aVar;
    }

    public final synchronized boolean b() {
        return this.f != null;
    }

    public final StrategyBean c() {
        if (this.f != null) {
            return this.f;
        }
        return this.e;
    }

    /* access modifiers changed from: protected */
    public final void a(StrategyBean strategyBean, boolean z) {
        x.c("[Strategy] Notify %s", b.class.getName());
        b.a(strategyBean, z);
        for (com.tencent.bugly.a next : this.c) {
            try {
                x.c("[Strategy] Notify %s", next.getClass().getName());
                next.onServerStrategyChanged(strategyBean);
            } catch (Throwable th) {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
            }
        }
    }

    public static void a(String str) {
        if (z.a(str) || !z.c(str)) {
            x.d("URL user set is invalid.", new Object[0]);
        } else {
            h = str;
        }
    }

    public final void a(as asVar) {
        if (asVar != null) {
            if (this.f == null || asVar.h != this.f.p) {
                StrategyBean strategyBean = new StrategyBean();
                strategyBean.g = asVar.a;
                strategyBean.i = asVar.c;
                strategyBean.h = asVar.b;
                if (z.a(h) || !z.c(h)) {
                    if (z.c(asVar.d)) {
                        x.c("[Strategy] Upload url changes to %s", asVar.d);
                        strategyBean.r = asVar.d;
                    }
                    if (z.c(asVar.e)) {
                        x.c("[Strategy] Exception upload url changes to %s", asVar.e);
                        strategyBean.s = asVar.e;
                    }
                }
                if (asVar.f != null && !z.a(asVar.f.a)) {
                    strategyBean.u = asVar.f.a;
                }
                if (asVar.h != 0) {
                    strategyBean.p = asVar.h;
                }
                if (asVar.g != null && asVar.g.size() > 0) {
                    strategyBean.v = asVar.g;
                    String str = asVar.g.get("B11");
                    if (str == null || !str.equals("1")) {
                        strategyBean.j = false;
                    } else {
                        strategyBean.j = true;
                    }
                    String str2 = asVar.g.get("B3");
                    if (str2 != null) {
                        strategyBean.y = Long.valueOf(str2).longValue();
                    }
                    strategyBean.q = (long) asVar.i;
                    strategyBean.x = (long) asVar.i;
                    String str3 = asVar.g.get("B27");
                    if (str3 != null && str3.length() > 0) {
                        try {
                            int parseInt = Integer.parseInt(str3);
                            if (parseInt > 0) {
                                strategyBean.w = parseInt;
                            }
                        } catch (Exception e2) {
                            if (!x.a(e2)) {
                                e2.printStackTrace();
                            }
                        }
                    }
                    String str4 = asVar.g.get("B25");
                    if (str4 == null || !str4.equals("1")) {
                        strategyBean.l = false;
                    } else {
                        strategyBean.l = true;
                    }
                }
                x.a("[Strategy] enableCrashReport:%b, enableQuery:%b, enableUserInfo:%b, enableAnr:%b, enableBlock:%b, enableSession:%b, enableSessionTimer:%b, sessionOverTime:%d, enableCocos:%b, strategyLastUpdateTime:%d", Boolean.valueOf(strategyBean.g), Boolean.valueOf(strategyBean.i), Boolean.valueOf(strategyBean.h), Boolean.valueOf(strategyBean.j), Boolean.valueOf(strategyBean.k), Boolean.valueOf(strategyBean.n), Boolean.valueOf(strategyBean.o), Long.valueOf(strategyBean.q), Boolean.valueOf(strategyBean.l), Long.valueOf(strategyBean.p));
                this.f = strategyBean;
                p.a().b(2);
                r rVar = new r();
                rVar.b = 2;
                rVar.a = strategyBean.e;
                rVar.e = strategyBean.f;
                rVar.g = z.a((Parcelable) strategyBean);
                p.a().a(rVar);
                a(strategyBean, true);
            }
        }
    }

    public static StrategyBean d() {
        List<r> a2 = p.a().a(2);
        if (a2 != null && a2.size() > 0) {
            r rVar = a2.get(0);
            if (rVar.g != null) {
                return (StrategyBean) z.a(rVar.g, StrategyBean.CREATOR);
            }
        }
        return null;
    }
}
