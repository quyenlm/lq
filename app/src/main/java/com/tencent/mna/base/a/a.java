package com.tencent.mna.base.a;

import com.tencent.mna.base.a.a.b;
import com.tencent.mna.base.a.b;
import com.tencent.mna.base.jni.entity.CloudRet;
import com.tencent.mna.base.utils.f;
import com.tencent.mna.base.utils.h;
import org.json.JSONObject;

/* compiled from: AccCloudProxy */
public class a {
    public static String a = "1000";
    public static boolean b = false;
    private static com.tencent.mna.base.a.a.a c = new com.tencent.mna.base.a.a.a();

    public static com.tencent.mna.base.a.a.a a() {
        return c;
    }

    public static String a(b bVar) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("appid", com.tencent.mna.a.b.d);
            jSONObject.put("zoneid", bVar.a());
            jSONObject.put("openid", bVar.c());
            jSONObject.put("version", a);
            jSONObject.put("devid", bVar.d());
            jSONObject.put("model", bVar.e());
            jSONObject.put("mac", bVar.f());
            jSONObject.put("routername", bVar.g());
            jSONObject.put("gameIP", bVar.b());
            jSONObject.put("bundleid", bVar.h());
            jSONObject.put("channel", "" + bVar.i());
            jSONObject.put("signal", bVar.j());
            jSONObject.put("isGameMatch", com.tencent.mna.a.b.i ? 1 : 0);
            jSONObject.put("nettype", bVar.l());
            jSONObject.put("signallevel", bVar.m());
            if (bVar.k() != null && bVar.k().length() > 0) {
                jSONObject.put("secret_key", bVar.k());
            }
            h.b("[N]中控版本 [" + a + "]");
        } catch (Exception e) {
        }
        return jSONObject.toString();
    }

    public static int a(String str, String str2) {
        h.b("[N]请求中控 vip = [" + str + "]");
        CloudRet a2 = b.a(b.a.Def_Acc, str2);
        if (a2 == null) {
            return 1002;
        }
        int i = a2.errno;
        h.b("[N]请求中控错误码:" + a2.errno);
        if (a2.errno != 0) {
            return i;
        }
        try {
            JSONObject jSONObject = new JSONObject(a2.json);
            int i2 = jSONObject.getInt("errno");
            if (i2 != 0) {
                return i2;
            }
            h.a("加速json: " + jSONObject.toString());
            c = com.tencent.mna.base.a.a.a.a(jSONObject);
            h.b("[N]中控配置:" + c.toString());
            h.b("[N]" + c.a());
            if (c.aY <= 0 || !f.a(c.aX)) {
                return 1005;
            }
            return 0;
        } catch (Exception e) {
            h.a("AccCloud request exception:" + e.getMessage());
            return 1003;
        }
    }

    public static com.tencent.mna.base.a.a.a a(String str, String str2, int i, int i2) {
        h.b("[N]请求中控 vip=[" + str + "], tcpFd=[" + i + "], netId=[" + i2 + "]");
        CloudRet a2 = b.a(b.a.Def_Acc, str2, i, i2);
        if (a2 == null) {
            return null;
        }
        h.b("[N]请求中控错误码:" + a2.errno);
        if (a2.errno != 0) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(a2.json);
            if (jSONObject.getInt("errno") != 0) {
                return null;
            }
            com.tencent.mna.base.a.a.a a3 = com.tencent.mna.base.a.a.a.a(jSONObject);
            if (a3.aY <= 0 || !f.a(a3.aX)) {
                return null;
            }
            return a3;
        } catch (Exception e) {
            h.a("OnceOnNetid exception:" + e.getMessage());
            return null;
        }
    }

    public static String b() {
        return b.a();
    }

    public static String c() {
        return c.aX;
    }

    public static int d() {
        return c.aY;
    }

    public static String e() {
        return c.aZ;
    }

    public static int f() {
        return c.ba;
    }

    public static int g() {
        return c.bb;
    }

    public static int h() {
        return c.bc;
    }

    public static int i() {
        return c.bd;
    }

    public static int j() {
        return c.bf;
    }

    public static String k() {
        return c.bg;
    }

    public static int l() {
        return c.bh;
    }

    public static int m() {
        return c.a;
    }

    public static int n() {
        return c.b;
    }

    public static int o() {
        return c.c;
    }

    public static int p() {
        return c.d;
    }

    public static int q() {
        return c.e;
    }

    public static int r() {
        return c.f;
    }

    public static boolean s() {
        return c.g;
    }

    public static int t() {
        return c.h;
    }

    public static int u() {
        return c.i;
    }

    public static int v() {
        return c.j;
    }

    public static int w() {
        return c.k;
    }

    public static int x() {
        return c.l;
    }

    public static boolean y() {
        return c.m;
    }

    public static int z() {
        return c.n;
    }

    public static int A() {
        return c.o;
    }

    public static int B() {
        return c.p;
    }

    public static int C() {
        return c.q;
    }

    public static int D() {
        return c.r;
    }

    public static int E() {
        return c.s;
    }

    public static int F() {
        return c.t;
    }

    public static int G() {
        return c.u;
    }

    public static int H() {
        return c.w;
    }

    public static int I() {
        return c.x;
    }

    public static boolean J() {
        return c.z;
    }

    public static int K() {
        return c.A;
    }

    public static int L() {
        return c.B;
    }

    public static int M() {
        return c.C;
    }

    public static int N() {
        return c.D;
    }

    public static String O() {
        return c.E;
    }

    public static double P() {
        return c.F;
    }

    public static String Q() {
        return c.G;
    }

    public static int R() {
        return c.H;
    }

    public static int S() {
        return c.I;
    }

    public static String T() {
        return c.J;
    }

    public static int U() {
        return c.K;
    }

    public static int V() {
        return c.L;
    }

    public static int W() {
        return c.M;
    }

    public static int X() {
        return c.N;
    }

    public static int Y() {
        return c.O;
    }

    public static int Z() {
        return c.P;
    }

    public static int aa() {
        return c.Q;
    }

    public static int ab() {
        return c.R;
    }

    public static int ac() {
        return c.S;
    }

    public static int ad() {
        return c.T;
    }

    public static int ae() {
        return c.U;
    }

    public static int af() {
        return c.V;
    }

    public static int ag() {
        return c.W;
    }

    public static int ah() {
        return c.X;
    }

    public static int ai() {
        return c.Y;
    }

    public static int aj() {
        return c.Z;
    }

    public static int ak() {
        return c.aa;
    }

    public static int al() {
        return c.ab;
    }

    public static String am() {
        return c.ac;
    }

    public static String an() {
        return c.ad;
    }

    public static String ao() {
        return c.ae;
    }

    public static int ap() {
        return c.af;
    }

    public static int aq() {
        return c.ag;
    }

    public static boolean ar() {
        return c.ai;
    }

    public static boolean as() {
        return c.aj;
    }

    public static int at() {
        return c.ak;
    }

    public static int au() {
        return c.al;
    }

    public static int av() {
        return c.am;
    }

    public static int aw() {
        return c.an;
    }

    public static int ax() {
        return c.ao;
    }

    public static int ay() {
        return c.ap;
    }

    public static int az() {
        return c.aq;
    }

    public static int aA() {
        return c.ar;
    }

    public static int aB() {
        return c.as;
    }

    public static int aC() {
        return c.av;
    }

    public static boolean aD() {
        return c.au;
    }

    public static boolean aE() {
        return c.aF;
    }

    public static boolean aF() {
        return c.aG;
    }

    public static boolean aG() {
        return c.aH;
    }

    public static boolean aH() {
        return c.at;
    }

    public static boolean aI() {
        return c.aI;
    }

    public static String aJ() {
        return c.aJ;
    }

    public static String aK() {
        return c.aK;
    }

    public static int aL() {
        return c.aL;
    }

    public static String aM() {
        return c.aM;
    }

    public static int aN() {
        return c.aN;
    }

    public static int aO() {
        return c.bi;
    }

    public static int aP() {
        return c.aO;
    }

    public static boolean aQ() {
        return c.aP;
    }

    public static int aR() {
        return c.aQ;
    }

    public static String aS() {
        return c.aR;
    }

    public static int aT() {
        return c.aS;
    }

    public static boolean aU() {
        return c.aT;
    }

    public static boolean aV() {
        return c.aU;
    }

    public static int aW() {
        return c.aV;
    }

    public static boolean aX() {
        return c.aW;
    }
}
