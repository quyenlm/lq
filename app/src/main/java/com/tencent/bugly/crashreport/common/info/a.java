package com.tencent.bugly.crashreport.common.info;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.Process;
import com.tencent.bugly.b;
import com.tencent.bugly.proguard.x;
import com.tencent.bugly.proguard.z;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/* compiled from: BUGLY */
public final class a {
    private static a af = null;
    public HashMap<String, String> A = new HashMap<>();
    public boolean B = true;
    public List<String> C = new ArrayList();
    public com.tencent.bugly.crashreport.a D = null;
    public SharedPreferences E;
    private final Context F;
    private String G;
    private String H;
    private String I;
    private String J = "unknown";
    private String K = "unknown";
    private String L = "";
    private String M = null;
    private String N = null;
    private String O = null;
    private String P = null;
    private long Q = -1;
    private long R = -1;
    private long S = -1;
    private String T = null;
    private String U = null;
    private Map<String, PlugInBean> V = null;
    private boolean W = true;
    private String X = null;
    private String Y = null;
    private Boolean Z = null;
    public final long a = System.currentTimeMillis();
    private String aa = null;
    private String ab = null;
    private String ac = null;
    private Map<String, PlugInBean> ad = null;
    private Map<String, PlugInBean> ae = null;
    private int ag = -1;
    private int ah = -1;
    private Map<String, String> ai = new HashMap();
    private Map<String, String> aj = new HashMap();
    private Map<String, String> ak = new HashMap();
    private boolean al = true;
    private Boolean am = null;
    private Boolean an = null;
    private String ao = null;
    private String ap = null;
    private String aq = null;
    private String ar = null;
    private String as = null;
    private final Object at = new Object();
    private final Object au = new Object();
    private final Object av = new Object();
    private final Object aw = new Object();
    private final Object ax = new Object();
    private final Object ay = new Object();
    private final Object az = new Object();
    public final byte b;
    public String c;
    public final String d;
    public boolean e = true;
    public final String f;
    public final String g;
    public final String h;
    public long i;
    public String j = null;
    public String k = null;
    public String l = null;
    public String m = null;
    public String n = null;
    public List<String> o = null;
    public String p = "unknown";
    public long q = 0;
    public long r = 0;
    public long s = 0;
    public long t = 0;
    public boolean u = false;
    public String v = null;
    public String w = null;
    public String x = null;
    public boolean y = false;
    public boolean z = false;

    private a(Context context) {
        this.F = z.a(context);
        this.b = 1;
        PackageInfo b2 = AppInfo.b(context);
        if (b2 != null) {
            try {
                this.j = b2.versionName;
                this.v = this.j;
                this.w = Integer.toString(b2.versionCode);
            } catch (Throwable th) {
                if (!x.a(th)) {
                    th.printStackTrace();
                }
            }
        }
        this.c = AppInfo.a(context);
        this.d = AppInfo.a(Process.myPid());
        this.f = b.o();
        this.g = b.a();
        this.k = AppInfo.c(context);
        this.h = "Android " + b.b() + ",level " + b.c();
        this.g + ";" + this.h;
        Map<String, String> d2 = AppInfo.d(context);
        if (d2 != null) {
            try {
                this.o = AppInfo.a(d2);
                String str = d2.get("BUGLY_APPID");
                if (str != null) {
                    this.Y = str;
                    c("APP_ID", this.Y);
                }
                String str2 = d2.get("BUGLY_APP_VERSION");
                if (str2 != null) {
                    this.j = str2;
                }
                String str3 = d2.get("BUGLY_APP_CHANNEL");
                if (str3 != null) {
                    this.l = str3;
                }
                String str4 = d2.get("BUGLY_ENABLE_DEBUG");
                if (str4 != null) {
                    this.u = str4.equalsIgnoreCase("true");
                }
                String str5 = d2.get("com.tencent.rdm.uuid");
                if (str5 != null) {
                    this.x = str5;
                }
            } catch (Throwable th2) {
                if (!x.a(th2)) {
                    th2.printStackTrace();
                }
            }
        }
        try {
            if (!context.getDatabasePath("bugly_db_").exists()) {
                this.z = true;
                x.c("App is first time to be installed on the device.", new Object[0]);
            }
        } catch (Throwable th3) {
            if (b.c) {
                th3.printStackTrace();
            }
        }
        this.E = z.a("BUGLY_COMMON_VALUES", context);
        x.c("com info create end", new Object[0]);
    }

    public final boolean a() {
        return this.al;
    }

    public final void a(boolean z2) {
        this.al = z2;
        if (this.D != null) {
            this.D.setNativeIsAppForeground(z2);
        }
    }

    public static synchronized a a(Context context) {
        a aVar;
        synchronized (a.class) {
            if (af == null) {
                af = new a(context);
            }
            aVar = af;
        }
        return aVar;
    }

    public static synchronized a b() {
        a aVar;
        synchronized (a.class) {
            aVar = af;
        }
        return aVar;
    }

    public static String c() {
        return "3.0.0";
    }

    public final void d() {
        synchronized (this.at) {
            this.G = UUID.randomUUID().toString();
        }
    }

    public final String e() {
        String str;
        synchronized (this.at) {
            if (this.G == null) {
                synchronized (this.at) {
                    this.G = UUID.randomUUID().toString();
                }
            }
            str = this.G;
        }
        return str;
    }

    public final String f() {
        if (!z.a((String) null)) {
            return null;
        }
        return this.Y;
    }

    public final void a(String str) {
        this.Y = str;
        c("APP_ID", str);
    }

    public final String g() {
        String str;
        synchronized (this.ay) {
            str = this.J;
        }
        return str;
    }

    public final void b(String str) {
        synchronized (this.ay) {
            if (str == null) {
                str = "10000";
            }
            this.J = str;
        }
    }

    public final void b(boolean z2) {
        this.W = z2;
    }

    public final String h() {
        if (this.I != null) {
            return this.I;
        }
        this.I = k() + "|" + m() + "|" + n();
        return this.I;
    }

    public final void c(String str) {
        this.I = str;
        synchronized (this.az) {
            this.aj.put("E8", str);
        }
    }

    public final synchronized String i() {
        return this.K;
    }

    public final synchronized void d(String str) {
        this.K = str;
    }

    public final synchronized String j() {
        return this.L;
    }

    public final synchronized void e(String str) {
        this.L = str;
    }

    public final String k() {
        if (!this.W) {
            return "";
        }
        if (this.M == null) {
            Context context = this.F;
            this.M = b.d();
        }
        return this.M;
    }

    public final String l() {
        if (!this.W) {
            return "";
        }
        if (this.N == null || !this.N.contains(":")) {
            Context context = this.F;
            this.N = b.f();
        }
        return this.N;
    }

    public final String m() {
        if (!this.W) {
            return "";
        }
        if (this.O == null) {
            Context context = this.F;
            this.O = b.e();
        }
        return this.O;
    }

    public final String n() {
        if (!this.W) {
            return "";
        }
        if (this.P == null) {
            this.P = b.a(this.F);
        }
        return this.P;
    }

    public final long o() {
        if (this.Q <= 0) {
            this.Q = b.h();
        }
        return this.Q;
    }

    public final long p() {
        if (this.R <= 0) {
            this.R = b.j();
        }
        return this.R;
    }

    public final long q() {
        if (this.S <= 0) {
            this.S = b.l();
        }
        return this.S;
    }

    public final String r() {
        if (this.T == null) {
            this.T = b.a(this.F, true);
        }
        return this.T;
    }

    public final String s() {
        if (this.U == null) {
            this.U = b.e(this.F);
        }
        return this.U;
    }

    public final void a(String str, String str2) {
        if (str != null && str2 != null) {
            synchronized (this.au) {
                this.A.put(str, str2);
            }
        }
    }

    public final String t() {
        try {
            Map<String, ?> all = this.F.getSharedPreferences("BuglySdkInfos", 0).getAll();
            if (!all.isEmpty()) {
                synchronized (this.au) {
                    for (Map.Entry next : all.entrySet()) {
                        try {
                            this.A.put(next.getKey(), next.getValue().toString());
                        } catch (Throwable th) {
                            x.a(th);
                        }
                    }
                }
            }
        } catch (Throwable th2) {
            x.a(th2);
        }
        if (this.A.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry next2 : this.A.entrySet()) {
            sb.append("[");
            sb.append((String) next2.getKey());
            sb.append(",");
            sb.append((String) next2.getValue());
            sb.append("] ");
        }
        c("SDK_INFO", sb.toString());
        return sb.toString();
    }

    public final String u() {
        if (this.as == null) {
            this.as = AppInfo.e(this.F);
        }
        return this.as;
    }

    public final synchronized Map<String, PlugInBean> v() {
        return null;
    }

    public final String w() {
        if (this.X == null) {
            this.X = b.n();
        }
        return this.X;
    }

    public final Boolean x() {
        if (this.Z == null) {
            this.Z = Boolean.valueOf(b.p());
        }
        return this.Z;
    }

    public final String y() {
        if (this.aa == null) {
            this.aa = b.d(this.F);
            x.a("ROM ID: %s", this.aa);
        }
        return this.aa;
    }

    public final String z() {
        if (this.ab == null) {
            this.ab = b.b(this.F);
            x.a("SIM serial number: %s", this.ab);
        }
        return this.ab;
    }

    public final String A() {
        if (this.ac == null) {
            this.ac = b.g();
            x.a("Hardware serial number: %s", this.ac);
        }
        return this.ac;
    }

    public final Map<String, String> B() {
        HashMap hashMap;
        synchronized (this.av) {
            if (this.ai.size() <= 0) {
                hashMap = null;
            } else {
                hashMap = new HashMap(this.ai);
            }
        }
        return hashMap;
    }

    public final String f(String str) {
        String remove;
        if (z.a(str)) {
            x.d("key should not be empty %s", str);
            return null;
        }
        synchronized (this.av) {
            remove = this.ai.remove(str);
        }
        return remove;
    }

    public final void C() {
        synchronized (this.av) {
            this.ai.clear();
        }
    }

    public final String g(String str) {
        String str2;
        if (z.a(str)) {
            x.d("key should not be empty %s", str);
            return null;
        }
        synchronized (this.av) {
            str2 = this.ai.get(str);
        }
        return str2;
    }

    public final void b(String str, String str2) {
        if (z.a(str) || z.a(str2)) {
            x.d("key&value should not be empty %s %s", str, str2);
            return;
        }
        synchronized (this.av) {
            this.ai.put(str, str2);
        }
    }

    public final int D() {
        int size;
        synchronized (this.av) {
            size = this.ai.size();
        }
        return size;
    }

    public final Set<String> E() {
        Set<String> keySet;
        synchronized (this.av) {
            keySet = this.ai.keySet();
        }
        return keySet;
    }

    public final Map<String, String> F() {
        HashMap hashMap;
        synchronized (this.az) {
            if (this.aj.size() <= 0) {
                hashMap = null;
            } else {
                hashMap = new HashMap(this.aj);
            }
        }
        return hashMap;
    }

    public final void c(String str, String str2) {
        if (z.a(str) || z.a(str2)) {
            x.d("server key&value should not be empty %s %s", str, str2);
            return;
        }
        synchronized (this.aw) {
            this.ak.put(str, str2);
        }
    }

    public final Map<String, String> G() {
        HashMap hashMap;
        synchronized (this.aw) {
            if (this.ak.size() <= 0) {
                hashMap = null;
            } else {
                hashMap = new HashMap(this.ak);
            }
        }
        return hashMap;
    }

    public final void a(int i2) {
        synchronized (this.ax) {
            int i3 = this.ag;
            if (i3 != i2) {
                this.ag = i2;
                x.a("user scene tag %d changed to tag %d", Integer.valueOf(i3), Integer.valueOf(this.ag));
            }
        }
    }

    public final int H() {
        int i2;
        synchronized (this.ax) {
            i2 = this.ag;
        }
        return i2;
    }

    public final void b(int i2) {
        int i3 = this.ah;
        if (i3 != 24096) {
            this.ah = 24096;
            x.a("server scene tag %d changed to tag %d", Integer.valueOf(i3), Integer.valueOf(this.ah));
        }
    }

    public final int I() {
        return this.ah;
    }

    public final synchronized Map<String, PlugInBean> J() {
        return null;
    }

    public static int K() {
        return b.c();
    }

    public final String L() {
        if (this.ao == null) {
            this.ao = b.q();
        }
        return this.ao;
    }

    public final String M() {
        if (this.ap == null) {
            this.ap = b.f(this.F);
        }
        return this.ap;
    }

    public final String N() {
        if (this.aq == null) {
            this.aq = b.g(this.F);
        }
        return this.aq;
    }

    public final String O() {
        Context context = this.F;
        return b.r();
    }

    public final String P() {
        if (this.ar == null) {
            this.ar = b.h(this.F);
        }
        return this.ar;
    }

    public final long Q() {
        Context context = this.F;
        return b.s();
    }

    public final boolean R() {
        if (this.am == null) {
            this.am = Boolean.valueOf(b.i(this.F));
            x.a("Is it a virtual machine? " + this.am, new Object[0]);
        }
        return this.am.booleanValue();
    }

    public final boolean S() {
        if (this.an == null) {
            this.an = Boolean.valueOf(b.j(this.F));
            x.a("Does it has hook frame? " + this.an, new Object[0]);
        }
        return this.an.booleanValue();
    }

    public final String T() {
        if (this.H == null) {
            this.H = AppInfo.g(this.F);
            x.a("Beacon channel " + this.H, new Object[0]);
        }
        return this.H;
    }
}
