package com.tencent.mna;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.widget.Toast;
import com.appsflyer.share.Constants;
import com.tencent.mna.b.g.d;
import com.tencent.mna.base.b.a;
import com.tencent.mna.base.c.c;
import com.tencent.mna.base.c.f;
import com.tencent.mna.base.jni.e;
import com.tencent.mna.base.utils.b;
import com.tencent.mna.base.utils.g;
import com.tencent.mna.base.utils.h;
import com.tencent.mna.base.utils.m;

/* compiled from: MnaSystem */
public class b {
    public static long a = 0;
    private static boolean b = false;
    /* access modifiers changed from: private */
    public static MNAObserver c = null;
    private static GHObserver d = null;
    private static NetworkObserver e = null;
    private static RouterObserver f = null;
    /* access modifiers changed from: private */
    public static long g = 0;
    /* access modifiers changed from: private */
    public static long h = 0;
    private static NetworkBindingListener i = null;
    @SuppressLint({"StaticFieldLeak"})
    private static Context j = null;

    public static synchronized boolean a() {
        boolean z;
        synchronized (b.class) {
            z = b;
        }
        return z;
    }

    public static synchronized void a(Context context, String str, boolean z, int i2, boolean z2, boolean z3, String str2) {
        synchronized (b.class) {
            try {
                j = context.getApplicationContext();
                a(str, z, i2, z2, str2, m.d(context), context.getApplicationInfo());
                if (a.b(context)) {
                    a.a(z3);
                    l();
                    n();
                    a(str, "5.5.2");
                    a(str, z);
                    o();
                    com.tencent.mna.b.a.b.a();
                    com.tencent.mna.b.d.b.a();
                    a(z3);
                    m();
                    b = true;
                }
            } catch (Throwable th) {
                h.a("Init failed, error:" + th.getMessage());
                b = false;
            }
        }
        return;
    }

    private static void l() {
        SharedPreferences.Editor edit = j.getSharedPreferences("BuglySdkInfos", 0).edit();
        edit.putString("d819a2d50a", "5.5.2");
        edit.apply();
    }

    private static void a(boolean z) {
        if (z) {
            new com.tencent.mna.base.utils.b().a(j, (b.a) new b.a() {
                public void a(final int i, final int i2) {
                    a.d(new Runnable() {
                        public void run() {
                            try {
                                if (b.c != null) {
                                    b.c.OnBatteryChangedNotify(i, i2);
                                } else {
                                    e.i("OnBatteryChangedNotify:" + i + "_" + i2);
                                }
                                h.a("OnBatteryChangedNotify:" + i + "_" + i2);
                            } catch (Throwable th) {
                                h.a("onBatteryChangedNotify throwable:" + th.getMessage());
                            }
                        }
                    });
                }
            });
        }
    }

    private static void m() {
        g.a(g());
    }

    private static void a(String str, boolean z, int i2, boolean z2, String str2, String str3, ApplicationInfo applicationInfo) {
        int i3 = 1;
        com.tencent.mna.a.b.d = str;
        com.tencent.mna.a.b.b = z;
        if (!z) {
            i3 = 0;
        }
        h.a(i3);
        com.tencent.mna.a.b.h = i2;
        com.tencent.mna.a.a.d = z2;
        if (applicationInfo != null) {
            com.tencent.mna.a.a.c = applicationInfo.targetSdkVersion;
        }
        if (z2) {
            com.tencent.mna.a.a.f = "control.mna.qq.com";
            com.tencent.mna.a.a.g = 31003;
        } else {
            com.tencent.mna.a.a.f = "test.mocmna.qq.com";
            com.tencent.mna.a.a.g = 31003;
            try {
                Toast.makeText(g(), "MNA测试环境", 1).show();
            } catch (Exception e2) {
            }
        }
        com.tencent.mna.a.b.e = str2;
        if (str3 != null && str3.length() > 0) {
            com.tencent.mna.a.a.e = "com/tencent/mna/" + str3.replace('.', '_') + Constants.URL_PATH_DELIMITER;
        }
        if (str2 != null && str2.trim().length() > 0 && !str2.equalsIgnoreCase("UNKNOWN")) {
            com.tencent.mna.base.c.b.a(str, g());
        }
    }

    private static void n() {
        try {
            if (com.tencent.mna.a.b.c != null && com.tencent.mna.a.b.c.length() > 0) {
                f.a(c.BANDWIDTH).a("brandvalue", com.tencent.mna.a.b.c).g();
                com.tencent.mna.a.b.c = "";
            }
        } catch (Throwable th) {
        }
    }

    private static void a(String str, String str2) {
        com.tencent.mna.b.f.a.a(str, str2);
        e.a(m.d(g()), m.c(g()));
    }

    private static void a(String str, boolean z) {
        String str2 = "";
        if (j != null) {
            str2 = j.getApplicationInfo().nativeLibraryDir + Constants.URL_PATH_DELIMITER;
        }
        e.a(Integer.parseInt(str), z, str2);
    }

    private static void o() {
        d.a(Integer.parseInt(com.tencent.mna.a.b.d), g());
        a.a((Runnable) new Runnable() {
            public void run() {
                try {
                    if (d.b().a == 0) {
                        h.b("[N]路由Qos 获取版本成功");
                    } else {
                        h.b("[N]路由Qos 获取版本失败");
                    }
                } catch (Throwable th) {
                }
            }
        });
    }

    public static MNAObserver b() {
        return c;
    }

    public static GHObserver c() {
        return d;
    }

    public static NetworkObserver d() {
        return e;
    }

    public static RouterObserver e() {
        return f;
    }

    public static void a(MNAObserver mNAObserver) {
        c = mNAObserver;
    }

    static void a(long j2, long j3) {
        g = j2;
        h = j3;
        a((MNAObserver) new MNAObserver() {
            public void OnStartSpeedNotify(int i, int i2, String str) {
                h.a("OnStartSpeedNotify SpeedWrapper.notify");
                e.a(b.g, i, i2, str);
            }

            public void OnQueryKartinNotify(String str, int i, String str2, int i2, int i3, int i4, int i5, String str3, int i6, int i7, String str4, int i8, int i9, String str5, int i10, int i11, String str6, int i12, int i13, int i14, String str7, int i15, String str8, int i16) {
                h.a("OnQueryKartinNotify SpeedWrapper.notify");
                e.a(b.h, str, i, str2, i2, i3, i4, i5, str3, i6, i7, str4, i8, i9, str5, i10, i11, str6, i12, i13, i14, str7, i15, str8, i16);
            }

            public void OnBatteryChangedNotify(int i, int i2) {
            }
        });
    }

    static void a(GHObserver gHObserver) {
        d = gHObserver;
    }

    static void a(NetworkObserver networkObserver) {
        e = networkObserver;
    }

    static void a(RouterObserver routerObserver) {
        f = routerObserver;
    }

    public static NetworkBindingListener f() {
        return i;
    }

    public static void a(NetworkBindingListener networkBindingListener) {
        i = networkBindingListener;
    }

    public static Context g() {
        return j;
    }

    public static SharedPreferences h() {
        try {
            return g().getSharedPreferences("mna_sp", 0);
        } catch (Exception e2) {
            return null;
        }
    }
}
