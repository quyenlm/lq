package com.tencent.mna.base.a;

import com.tencent.mna.a.b;
import com.tencent.mna.b.d.g;
import com.tencent.mna.base.a.a.d;
import com.tencent.mna.base.a.a.e;
import com.tencent.mna.base.a.b;
import com.tencent.mna.base.a.d;
import com.tencent.mna.base.jni.entity.CloudRet;
import com.tencent.mna.base.utils.h;
import com.tencent.mna.base.utils.k;
import org.json.JSONObject;

/* compiled from: DgnCloudProxy */
public class c {
    private static d a = new d();
    private static e b = new e();
    private static long c = 0;
    private static int d = 0;
    private static String e = "";

    public static JSONObject a(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, int i2, String str8) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("appid", b.d);
            jSONObject.put("zoneid", i);
            jSONObject.put("openid", str);
            jSONObject.put("version", str2);
            jSONObject.put("gameIP", "10000");
            jSONObject.put("rproxy_flag", str7);
            jSONObject.put("model", str3);
            jSONObject.put("devid", str4);
            jSONObject.put("mac", str5);
            jSONObject.put("bundleid", str6);
            jSONObject.put("nettype", i2);
            if (str8 != null && str8.trim().length() > 0 && !str8.equalsIgnoreCase("UNKNOWN")) {
                jSONObject.put("secret_key", str8);
            }
        } catch (Exception e2) {
        }
        return jSONObject;
    }

    public static int a(JSONObject jSONObject, JSONObject jSONObject2) {
        int a2 = a(jSONObject);
        return a2 != 0 ? a2 : b(jSONObject2);
    }

    private static int a(JSONObject jSONObject) {
        boolean z;
        int a2 = k.a(com.tencent.mna.b.g());
        if (a2 != d || !b.b().contains(e)) {
            z = true;
        } else {
            z = false;
        }
        if (a != null && !a.a && System.currentTimeMillis() - c < ((long) (a.b * 60)) * 1000 && !z) {
            return 0;
        }
        CloudRet a3 = b.a(b.a.Def_Dgn, jSONObject.toString());
        if (a3 == null) {
            return 1002;
        }
        int i = a3.errno;
        h.b("DgnCloudConfig, ret errno:" + i);
        if (i != 0) {
            return i;
        }
        try {
            JSONObject jSONObject2 = new JSONObject(a3.json);
            h.a("诊断json: " + jSONObject2.toString());
            a = d.a(jSONObject2);
            h.a(a.a());
            d = a2;
            c = System.currentTimeMillis();
            e = b.a();
            return 0;
        } catch (Throwable th) {
            return 1003;
        }
    }

    private static int b(JSONObject jSONObject) {
        d.a a2 = d.a(a.y, jSONObject.toString());
        if (a2.b == null) {
            return a2.a;
        }
        b = a2.b;
        return 0;
    }

    public static String a() {
        return a.aX;
    }

    public static int b() {
        return a.aY;
    }

    public static String c() {
        return a.aZ;
    }

    public static int d() {
        return a.ba;
    }

    public static int e() {
        return a.bb;
    }

    public static int f() {
        return a.bc;
    }

    public static int g() {
        return a.bd;
    }

    public static int h() {
        return a.bf;
    }

    public static int i() {
        return a.bh;
    }

    public static int j() {
        return a.bi;
    }

    public static boolean k() {
        return a.d == 1;
    }

    public static int l() {
        return a.e;
    }

    public static int m() {
        return a.f;
    }

    public static int n() {
        return a.g;
    }

    public static String o() {
        return a.h;
    }

    public static int p() {
        return a.i;
    }

    public static int q() {
        return a.j;
    }

    public static int r() {
        return a.k;
    }

    public static int s() {
        return a.l;
    }

    public static int t() {
        return a.n;
    }

    public static int u() {
        return a.o;
    }

    public static int v() {
        return a.p;
    }

    public static int w() {
        return a.r;
    }

    public static boolean x() {
        return a.s == 1;
    }

    public static int y() {
        return a.t;
    }

    public static int z() {
        return a.u;
    }

    public static int A() {
        return a.v;
    }

    public static int B() {
        return a.w;
    }

    public static int C() {
        return a.x;
    }

    public static int D() {
        return a.z;
    }

    public static int E() {
        return a.A;
    }

    public static double F() {
        return a.B;
    }

    public static String G() {
        return a.C;
    }

    public static String H() {
        return a.toString();
    }

    public static boolean I() {
        return a.D;
    }

    public static g[] J() {
        return b.a;
    }

    public static g[] K() {
        return b.b;
    }

    public static g[] L() {
        return b.c;
    }

    public static g[] M() {
        return b.d;
    }

    public static g[] N() {
        return b.e;
    }

    public static g[] O() {
        return b.f;
    }

    public static g[] P() {
        return b.g;
    }
}
