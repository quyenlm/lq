package com.tencent.mna.b.a;

import android.os.Build;
import com.tencent.mna.base.jni.e;
import com.tencent.mna.base.utils.h;
import com.tencent.mna.base.utils.m;

/* compiled from: AccelerateHook */
public class a {
    public static String a = "";
    public static int b = -1;
    public static String c = "";
    public static String d = "";

    public static boolean a() {
        return com.tencent.mna.base.a.a.aI() || !b();
    }

    public static boolean b() {
        return (com.tencent.mna.a.a.c > 23 && Build.VERSION.SDK_INT > 23) || m.d().isLP64();
    }

    public static void a(boolean z) {
        e.a(z);
    }

    public static boolean a(int i, String str, e eVar) {
        boolean z;
        if (eVar == null) {
            h.c("hookByType warning: udpPtr is null");
            return false;
        } else if (!a()) {
            return false;
        } else {
            int i2 = -1;
            b = i;
            a = str;
            switch (i) {
                case 1:
                    try {
                        i2 = e.a(str, eVar.a, eVar.b);
                        break;
                    } catch (Exception e) {
                        h.a("hookByType exception:" + e.getMessage());
                        break;
                    }
                case 2:
                    i2 = e.b(str, eVar.c, eVar.d);
                    break;
                case 3:
                    i2 = e.a(str, eVar.e, eVar.f, eVar.g);
                    break;
                case 4:
                    i2 = e.b(str, eVar.e, eVar.a, eVar.b);
                    break;
                case 5:
                    i2 = e.c(str, eVar.e, eVar.c, eVar.d);
                    break;
            }
            if (i2 == 0) {
                z = true;
            } else {
                z = false;
            }
            return z;
        }
    }

    public static boolean c() {
        boolean z;
        int i = -1;
        if (!a()) {
            return false;
        }
        if (a == null || a.length() <= 0 || b == -1) {
            return true;
        }
        try {
            switch (b) {
                case 1:
                    i = e.b(a);
                    break;
                case 2:
                    i = e.c(a);
                    break;
                case 3:
                    i = e.d(a);
                    break;
                case 4:
                    i = e.e(a);
                    break;
                case 5:
                    i = e.f(a);
                    break;
            }
        } catch (Exception e) {
            h.a("unhookByType exception:" + e.getMessage());
        }
        if (i == 0) {
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    static boolean a(String str, long j) {
        if (!a()) {
            return false;
        }
        c = str;
        int i = -1;
        try {
            i = e.a(str, j);
        } catch (Exception e) {
            h.a("hookClose exception:" + e.getMessage());
        }
        if (i == 0) {
            return true;
        }
        return false;
    }

    static boolean d() {
        int i;
        if (!a()) {
            return false;
        }
        if (c == null || c.length() <= 0) {
            return true;
        }
        try {
            i = e.g(c);
        } catch (Exception e) {
            h.a("unhookClose exception:" + e.getMessage());
            i = -1;
        }
        if (i != 0) {
            return false;
        }
        return true;
    }

    static boolean a(String str, int i) {
        if (!a()) {
            return false;
        }
        d = str;
        int i2 = -1;
        try {
            i2 = e.a(str, i);
        } catch (Exception e) {
            h.a("startFpsByHook exception:" + e.getMessage());
        }
        if (i2 == 0) {
            return true;
        }
        return false;
    }

    static boolean e() {
        int i;
        if (!a()) {
            return false;
        }
        if (d == null || d.length() <= 0) {
            return true;
        }
        try {
            i = e.j(d);
        } catch (Exception e) {
            h.a("endFpsByUnhook exception:" + e.getMessage());
            i = -1;
        }
        if (i != 0) {
            return false;
        }
        return true;
    }

    public static void f() {
        a = "";
        c = "";
        d = "";
        b = -1;
    }
}
