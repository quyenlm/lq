package com.tencent.tp;

import android.content.Context;
import android.content.pm.PackageManager;
import java.util.ArrayList;
import java.util.List;

public class h implements d {
    private static volatile h b;
    private d a;

    private h() {
        try {
            Class a2 = c.a("com.tencent.up_tp.SMI");
            if (a2 != null) {
                this.a = (d) a2.newInstance();
                return;
            }
            throw new Exception("com.tencent.up_tp.SMI NOT found");
        } catch (Throwable th) {
            this.a = null;
        }
    }

    public static h a() {
        if (b == null) {
            synchronized (h.class) {
                if (b == null) {
                    b = new h();
                }
            }
        }
        return b;
    }

    public String a(Context context) {
        return this.a != null ? this.a.a(context) : "NotImp";
    }

    public String b(Context context) {
        return this.a != null ? this.a.b(context) : "NotImp";
    }

    public String c(Context context) {
        return this.a != null ? this.a.c(context) : "NotImp";
    }

    public String d(Context context) {
        return this.a != null ? this.a.d(context) : "NotImp";
    }

    public String e(Context context) {
        return this.a != null ? this.a.e(context) : "NotImp";
    }

    public String f(Context context) {
        return this.a != null ? this.a.f(context) : "NotImp";
    }

    public String g(Context context) {
        return this.a != null ? this.a.g(context) : "NotImp";
    }

    public String h(Context context) {
        return this.a != null ? this.a.h(context) : "NotImp";
    }

    public String i(Context context) {
        return this.a != null ? this.a.i(context) : "NotImp";
    }

    public String j(Context context) {
        return this.a != null ? this.a.j(context) : "NotImp";
    }

    public String k(Context context) {
        return this.a != null ? this.a.k(context) : "NotImp";
    }

    public String l(Context context) {
        return this.a != null ? this.a.l(context) : "NotImp";
    }

    public List m(Context context) {
        if (this.a != null) {
            return this.a.m(context);
        }
        String a2 = TssSdk.a("IsEnabled_0:apk");
        if (a2 == null || a2.compareTo("1") != 0) {
            return new ArrayList();
        }
        PackageManager packageManager = context.getPackageManager();
        return packageManager == null ? new ArrayList() : packageManager.getInstalledPackages(0);
    }

    public List n(Context context) {
        return this.a != null ? this.a.n(context) : new ArrayList();
    }
}
