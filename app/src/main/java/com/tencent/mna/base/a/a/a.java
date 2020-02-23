package com.tencent.mna.base.a.a;

import com.amazonaws.regions.ServiceAbbreviations;
import com.tencent.mna.base.utils.f;
import com.tencent.mna.base.utils.h;
import org.json.JSONObject;

/* compiled from: AccCloudConfig */
public class a extends c {
    public int A = 0;
    public int B = 0;
    public int C = 500;
    public int D = 1000;
    public String E = "0";
    public double F = 1.0d;
    public String G = "";
    public int H = 0;
    public int I = 0;
    public String J = "";
    public int K = 0;
    public int L = 0;
    public int M = 10;
    public int N = 0;
    public int O = 0;
    public int P = 0;
    public int Q = 0;
    public int R = 0;
    public int S = 0;
    public int T = 0;
    public int U = 0;
    public int V = 0;
    public int W = 0;
    public int X = 0;
    public int Y = 0;
    public int Z = 2000;
    public int a = 10;
    public String aA = "";
    public String aB = "";
    public String aC = "";
    public String aD = "";
    public String aE = "";
    public boolean aF = true;
    public boolean aG = false;
    public boolean aH = false;
    public boolean aI = true;
    public String aJ = "0.0.0.0";
    public String aK = "0.0.0.0";
    public int aL = 0;
    public String aM = "0.0.0.0";
    public int aN = 0;
    public int aO = 1;
    public boolean aP = true;
    public int aQ = 0;
    public String aR = "0.0.0.0";
    public int aS = 0;
    public boolean aT = false;
    public boolean aU = false;
    public int aV = 0;
    public boolean aW = true;
    public int aa = 5000;
    public int ab = 500;
    public String ac = "0.0.0.0";
    public String ad = "0.0.0.0";
    public String ae = "0.0.0.0";
    public int af = 0;
    public int ag = 0;
    public int ah = 0;
    public boolean ai = false;
    public boolean aj = false;
    public int ak = 200;
    public int al = 1;
    public int am = 200;
    public int an = 3;
    public int ao = 200;
    public int ap = 3;
    public int aq = 200;
    public int ar = 1;
    public int as = 3;
    public boolean at = false;
    public boolean au = false;
    public int av = 0;
    public String aw = "";
    public String ax = "";
    public String ay = "";
    public String az = "";
    public int b = 350;
    public int c = 0;
    public int d = 25;
    public int e = 3;
    public int f = 0;
    public boolean g = false;
    public int h = 350;
    public int i = 200;
    public int j = 3000;
    public int k = 3000;
    public int l = 0;
    public boolean m = false;
    public int n = 10000;
    public int o = 50;
    public int p = 0;
    public int q = 0;
    public int r = 5000;
    public int s = 0;
    public int t = 0;
    public int u = 0;
    public int v = 0;
    public int w = 0;
    public int x = 0;
    public int y = 0;
    public boolean z = true;

    public String toString() {
        return super.toString() + String.format(com.tencent.mna.a.a.a, "delayAdj:%d,forcespeed:%d,qos:%b,xmlver:%s,diag:%d,smart:%d,loadm:%d,proxy1:%s:%d,proxy2:%s:%d,proxy3:%s,token:%d,ab_acc:%b,usew2m:%b,mutiProxyDomain:%s,mutiProxyPort:%d,maxjump:%d,useterms:%b,pingip:%s:%d,tos:%d", new Object[]{Integer.valueOf(this.a), Integer.valueOf(this.f), Boolean.valueOf(this.m), this.E, Integer.valueOf(this.l), Integer.valueOf(this.av), Integer.valueOf(this.A), this.ac, Integer.valueOf(this.af), this.ad, Integer.valueOf(this.ag), this.ae, Integer.valueOf(this.ah), Boolean.valueOf(this.aG), Boolean.valueOf(this.aj), this.aJ, Integer.valueOf(this.aL), Integer.valueOf(this.aO), Boolean.valueOf(this.aP), this.aM, Integer.valueOf(this.aN), Integer.valueOf(this.aV)});
    }

    public static a a(JSONObject jSONObject) {
        int i2;
        int i3;
        boolean z2;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        boolean z3 = true;
        a aVar = new a();
        try {
            aVar.b(jSONObject);
            aVar.f = jSONObject.optInt("forcespeed", aVar.f);
            aVar.g = jSONObject.optInt("autoBreak", aVar.g ? 1 : 0) == 1;
            aVar.h = jSONObject.optInt("breakDelay", aVar.h);
            aVar.k = jSONObject.optInt("testTime", aVar.k);
            aVar.l = jSONObject.optInt("diagnose", aVar.l);
            if (aVar.m) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            aVar.m = jSONObject.optInt("qos", i2) == 1;
            aVar.r = jSONObject.optInt("pvp", aVar.r);
            if (aVar.z) {
                i3 = 1;
            } else {
                i3 = 0;
            }
            if (jSONObject.optInt("cping", i3) == 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            aVar.z = z2;
            aVar.A = jSONObject.optInt("loadm", aVar.A);
            aVar.E = jSONObject.optString("xmlver", aVar.E);
            aVar.F = jSONObject.optDouble("probMin", aVar.F);
            aVar.G = jSONObject.optString("cdnkey", aVar.G);
            aVar.J = jSONObject.optString("skey", aVar.J);
            aVar.K = jSONObject.optInt("rport", aVar.K);
            aVar.L = jSONObject.optInt(ServiceAbbreviations.RDS, aVar.L);
            aVar.M = jSONObject.optInt("dupgap", aVar.M);
            aVar.N = jSONObject.optInt("slip", aVar.N);
            aVar.ah = jSONObject.optInt("inotoken", aVar.ah);
            aVar.av = jSONObject.optInt("smart", aVar.av);
            aVar.aw = jSONObject.optString("cli_country", aVar.aw);
            aVar.ax = jSONObject.optString("cli_area", aVar.ax);
            aVar.ay = jSONObject.optString("cli_isp", aVar.ay);
            aVar.az = jSONObject.optString("ser_country", aVar.az);
            aVar.aA = jSONObject.optString("ser_area", aVar.aA);
            aVar.aB = jSONObject.optString("ser_isp", aVar.aB);
            aVar.aC = jSONObject.optString("pro1_country", aVar.aC);
            aVar.aD = jSONObject.optString("pro1_area", aVar.aD);
            aVar.aE = jSONObject.optString("pro1_isp", aVar.aE);
            aVar.aF = jSONObject.optInt("usereport", aVar.aF ? 1 : 0) == 1;
            if (aVar.aG) {
                i4 = 1;
            } else {
                i4 = 0;
            }
            aVar.aG = jSONObject.optInt("ab_acc", i4) == 1;
            if (aVar.aH) {
                i5 = 1;
            } else {
                i5 = 0;
            }
            aVar.aH = jSONObject.optInt("ab_w2m", i5) == 1;
            aVar.as = jSONObject.optInt("w2mkmax", aVar.as);
            if (aVar.at) {
                i6 = 1;
            } else {
                i6 = 0;
            }
            aVar.at = jSONObject.optInt("w2mcmvip", i6) == 1;
            if (aVar.au) {
                i7 = 1;
            } else {
                i7 = 0;
            }
            aVar.au = jSONObject.optInt("autoBinding", i7) == 1;
            if (aVar.aI) {
                i8 = 1;
            } else {
                i8 = 0;
            }
            aVar.aI = jSONObject.optInt("nhook", i8) == 1;
            aVar.aO = jSONObject.optInt("maxjump", aVar.aO);
            if (aVar.aP) {
                i9 = 1;
            } else {
                i9 = 0;
            }
            aVar.aP = jSONObject.optInt("useterms", i9) == 1;
            aVar.aQ = jSONObject.optInt("mutisetID", aVar.aQ);
            aVar.bj = jSONObject.optString("kplvalue", aVar.bj);
            aVar.bk = jSONObject.optString("ver_test", aVar.bk);
            aVar.aV = jSONObject.optInt("tos", aVar.aV);
            if (aVar.aW) {
                i10 = 1;
            } else {
                i10 = 0;
            }
            if (jSONObject.optInt("sd4id", i10) != 1) {
                z3 = false;
            }
            aVar.aW = z3;
            f(aVar, jSONObject.optString("speedcontrol"));
            g(aVar, jSONObject.optString("fpsapm"));
            h(aVar, jSONObject.optString("perfctrl"));
            i(aVar, jSONObject.optString("paste"));
            j(aVar, jSONObject.optString("stimeout"));
            k(aVar, jSONObject.optString("bandconfig"));
            l(aVar, jSONObject.optString("rcontrol"));
            m(aVar, jSONObject.optString("reqtime"));
            n(aVar, jSONObject.optString("gap"));
            o(aVar, jSONObject.optString("rgap"));
            e(aVar, jSONObject.optString("repTimes"));
            b(aVar, jSONObject.optString("mutiProxyIP"));
            c(aVar, jSONObject.optString("pingsvr"));
            d(aVar, jSONObject.optString("doubleProxy"));
            b(aVar, jSONObject);
            a(aVar, jSONObject);
            a(aVar, jSONObject.optString("vivoctrl"));
        } catch (Throwable th) {
        }
        return aVar;
    }

    private static void a(a aVar, String str) {
        if (str != null && !str.isEmpty() && str.contains("_")) {
            String[] split = str.split("_");
            aVar.aT = split[0].equalsIgnoreCase("1");
            aVar.aU = split[1].equalsIgnoreCase("1");
            h.a("vivoDiagnoseSwitch = " + aVar.aT + " vivoW2mSwitch = " + aVar.aU);
        }
    }

    private static void a(a aVar, JSONObject jSONObject) {
        int i2;
        boolean z2 = true;
        aVar.ai = jSONObject.optInt("isBGP", aVar.ai ? 1 : 0) == 1;
        if (aVar.aj) {
            i2 = 1;
        } else {
            i2 = 0;
        }
        if (jSONObject.optInt("usew2m", i2) != 1) {
            z2 = false;
        }
        aVar.aj = z2;
        String optString = jSONObject.optString("w2mcfg");
        if (optString != null) {
            String[] split = optString.split("_");
            if (split.length >= 4) {
                try {
                    aVar.ak = Integer.parseInt(split[0]);
                    aVar.al = Integer.parseInt(split[1]);
                    aVar.am = Integer.parseInt(split[2]);
                    aVar.an = Integer.parseInt(split[3]);
                } catch (Exception e2) {
                    h.a("AccCloudConfig parseW2mcfg exception:" + e2.getMessage());
                }
            }
        }
        String optString2 = jSONObject.optString("m2wcfg");
        if (optString2 != null) {
            String[] split2 = optString2.split("_");
            if (split2.length >= 4) {
                try {
                    aVar.ao = Integer.parseInt(split2[0]);
                    aVar.ap = Integer.parseInt(split2[1]);
                    aVar.aq = Integer.parseInt(split2[2]);
                    aVar.ar = Integer.parseInt(split2[3]);
                } catch (Exception e3) {
                    h.a("AccCloudConfig parseM2wcfg exception:" + e3.getMessage());
                }
            }
        }
    }

    private static void b(a aVar, JSONObject jSONObject) {
        String optString = jSONObject.optString("proxy1svr");
        if (optString != null) {
            String[] split = optString.split(":");
            if (split.length >= 2) {
                try {
                    if (a(split[0])) {
                        String f2 = f.f(split[0]);
                        if (f2 != null && f2.length() > 0) {
                            aVar.ac = f2;
                            aVar.af = Integer.parseInt(split[1]);
                        }
                    } else {
                        aVar.ac = "0.0.0.0";
                        aVar.af = 0;
                        h.c("AccCloudConfig proxyip1 not valid.");
                    }
                } catch (Exception e2) {
                    h.a("AccCloudConfig parseProxy1Svr exception:" + e2.getMessage());
                }
            }
        }
        String optString2 = jSONObject.optString("proxy2svr");
        if (optString2 != null) {
            String[] split2 = optString2.split(":");
            if (split2.length >= 2) {
                try {
                    if (f.a(split2[0])) {
                        aVar.ad = split2[0];
                        aVar.ag = Integer.parseInt(split2[1]);
                    } else {
                        aVar.ad = "0.0.0.0";
                        aVar.ag = 0;
                        h.c("AccCloudConfig proxyip2 not valid.");
                    }
                } catch (Exception e3) {
                    h.a("AccCloudConfig parseProxy2Svr exception:" + e3.getMessage());
                }
            }
        }
        String optString3 = jSONObject.optString("proxy3svr");
        if (optString3 == null) {
            return;
        }
        if (f.a(optString3)) {
            aVar.ae = optString3;
            return;
        }
        aVar.ae = "0.0.0.0";
        h.c("AccCloudConfig proxyip3 not valid.");
    }

    private static void b(a aVar, String str) {
        if (str != null && str.contains(":")) {
            String[] split = str.split(":");
            if (split.length >= 2) {
                try {
                    if (a(split[0])) {
                        aVar.aJ = split[0];
                        String f2 = f.f(split[0]);
                        if (f2 != null && f2.length() > 0) {
                            aVar.aK = f2;
                            aVar.aL = Integer.parseInt(split[1]);
                            return;
                        }
                        return;
                    }
                    aVar.aJ = "0.0.0.0";
                    aVar.aK = "0.0.0.0";
                    aVar.aL = 0;
                } catch (Exception e2) {
                }
            }
        }
    }

    private static void c(a aVar, String str) {
        if (str != null && str.contains(":")) {
            String[] split = str.split(":");
            if (split.length >= 2) {
                try {
                    if (a(split[0])) {
                        String f2 = f.f(split[0]);
                        if (f2 != null && f2.length() > 0) {
                            aVar.aM = f2;
                            aVar.aN = Integer.parseInt(split[1]);
                            return;
                        }
                        return;
                    }
                    aVar.aM = "0.0.0.0";
                    aVar.aN = 0;
                } catch (Exception e2) {
                }
            }
        }
    }

    private static void d(a aVar, String str) {
        if (str != null && str.contains(":")) {
            String[] split = str.split(":");
            if (split.length >= 2) {
                try {
                    if (a(split[0])) {
                        String f2 = f.f(split[0]);
                        if (f2 != null && f2.length() > 0) {
                            aVar.aR = f2;
                            aVar.aS = Integer.parseInt(split[1]);
                            return;
                        }
                        return;
                    }
                    aVar.aR = "0.0.0.0";
                    aVar.aS = 0;
                } catch (Exception e2) {
                }
            }
        }
    }

    private static void e(a aVar, String str) {
        if (str != null && str.contains("_")) {
            String[] split = str.split("_");
            if (split.length >= 2) {
                try {
                    aVar.i = Integer.parseInt(split[0]);
                    aVar.j = Integer.parseInt(split[1]);
                } catch (Exception e2) {
                    h.a("AccCloudConfig parseRepTimes exception:" + e2.getMessage());
                }
            }
        }
    }

    private static void f(a aVar, String str) {
        if (str != null && str.contains("_")) {
            String[] split = str.split("_");
            if (split.length >= 5) {
                try {
                    aVar.a = Integer.parseInt(split[0]);
                    aVar.b = Integer.parseInt(split[1]);
                    aVar.c = Integer.parseInt(split[2]);
                    aVar.d = Integer.parseInt(split[3]);
                    aVar.e = Integer.parseInt(split[4]);
                } catch (Exception e2) {
                    h.a("AccCloudConfig parseSpeedControl exception:" + e2.getMessage());
                }
            }
        }
    }

    private static void g(a aVar, String str) {
        if (str != null && str.contains("_")) {
            String[] split = str.split("_");
            if (split.length >= 4) {
                try {
                    aVar.n = Integer.parseInt(split[0]);
                    aVar.o = Integer.parseInt(split[1]);
                    aVar.p = Integer.parseInt(split[2]);
                    aVar.q = Integer.parseInt(split[3]);
                } catch (Exception e2) {
                    h.a("AccCloudConfig parseFpsapm exception:" + e2.getMessage());
                }
            }
        }
    }

    private static void h(a aVar, String str) {
        if (str != null && str.contains("_")) {
            String[] split = str.split("_");
            if (split.length >= 5) {
                try {
                    aVar.s = Integer.parseInt(split[0]);
                    aVar.t = Integer.parseInt(split[1]);
                    aVar.u = Integer.parseInt(split[2]);
                    aVar.v = Integer.parseInt(split[3]);
                    aVar.w = Integer.parseInt(split[4]);
                } catch (Exception e2) {
                    h.a("AccCloudConfig parsePerfctrl exception:" + e2.getMessage());
                }
            }
        }
    }

    private static void i(a aVar, String str) {
        if (str != null && str.contains("_")) {
            String[] split = str.split("_");
            if (split.length >= 2) {
                try {
                    aVar.x = Integer.parseInt(split[0]);
                    aVar.y = Integer.parseInt(split[1]);
                } catch (Exception e2) {
                    h.a("AccCloudConfig parsePaste exception:" + e2.getMessage());
                }
            }
        }
    }

    private static void j(a aVar, String str) {
        if (str != null && str.contains("_")) {
            String[] split = str.split("_");
            if (split.length >= 3) {
                try {
                    aVar.B = Integer.parseInt(split[0]);
                    aVar.C = Integer.parseInt(split[1]);
                    aVar.D = Integer.parseInt(split[2]);
                } catch (Exception e2) {
                    h.a("AccCloudConfig parseStimeout exception:" + e2.getMessage());
                }
            }
        }
    }

    private static void k(a aVar, String str) {
        if (str != null && str.contains("_")) {
            String[] split = str.split("_");
            if (split.length >= 2) {
                try {
                    aVar.H = Integer.parseInt(split[0]);
                    aVar.I = Integer.parseInt(split[1]);
                } catch (Exception e2) {
                    h.a("AccCloudConfig parseBandconfig exception:" + e2.getMessage());
                }
            }
        }
    }

    private static void l(a aVar, String str) {
        if (str != null && str.contains("_")) {
            String[] split = str.split("_");
            if (split.length >= 5) {
                try {
                    aVar.U = Integer.parseInt(split[0]);
                    aVar.V = Integer.parseInt(split[1]);
                    aVar.W = Integer.parseInt(split[2]);
                    aVar.X = Integer.parseInt(split[3]);
                    aVar.Y = Integer.parseInt(split[4]);
                } catch (Exception e2) {
                    h.a("AccCloudConfig parseRcontrol exception:" + e2.getMessage());
                }
            }
        }
    }

    private static void m(a aVar, String str) {
        if (str != null && str.contains("_")) {
            String[] split = str.split("_");
            if (split.length >= 3) {
                try {
                    aVar.Z = Integer.parseInt(split[0]);
                    aVar.aa = Integer.parseInt(split[1]);
                    aVar.ab = Integer.parseInt(split[2]);
                } catch (Exception e2) {
                    h.a("AccCloudConfig parseReqtime exception:" + e2.getMessage());
                }
            }
        }
    }

    private static void n(a aVar, String str) {
        if (str != null && str.contains("_")) {
            String[] split = str.split("_");
            if (split.length >= 3) {
                try {
                    aVar.O = Integer.parseInt(split[0]);
                    aVar.P = Integer.parseInt(split[1]);
                    aVar.Q = Integer.parseInt(split[2]);
                } catch (Exception e2) {
                    h.a("AccCloudConfig parseGap exception:" + e2.getMessage());
                }
            }
        }
    }

    private static void o(a aVar, String str) {
        if (str != null && str.contains("_")) {
            String[] split = str.split("_");
            if (split.length >= 3) {
                try {
                    aVar.R = Integer.parseInt(split[0]);
                    aVar.S = Integer.parseInt(split[1]);
                    aVar.T = Integer.parseInt(split[2]);
                } catch (Exception e2) {
                    h.a("AccCloudConfig parseRgap exception:" + e2.getMessage());
                }
            }
        }
    }

    public String a() {
        return "AccCloudConfig{" + super.a() + ", delayAdj=" + this.a + ", delayTop=" + this.b + ", delayMin=" + this.c + ", sdMax=" + this.d + ", toMax=" + this.e + ", forcespeed=" + this.f + ", autoBreak=" + this.g + ", breakDelay=" + this.h + ", repTimes=" + this.i + ", maxTimes=" + this.j + ", testTime=" + this.k + ", diagnose=" + this.l + ", qos=" + this.m + ", si=" + this.n + ", mm=" + this.o + ", fps=" + this.p + ", apm=" + this.q + ", pvp=" + this.r + ", cpu=" + this.s + ", mem=" + this.t + ", gpu=" + this.u + ", battery=" + this.v + ", nic=" + this.w + ", paste=" + this.x + ", clen=" + this.y + ", cping=" + this.z + ", loadm=" + this.A + ", spTimeout=" + this.B + ", startTimeout=" + this.C + ", checkTimeout=" + this.D + ", xmlver='" + this.E + '\'' + ", probMin=" + this.F + ", cdnkey='" + this.G + '\'' + ", rbrand=" + this.H + ", brandLimit=" + this.I + ", skey='" + this.J + '\'' + ", rport=" + this.K + ", rds=" + this.L + ", dupgap=" + this.M + ", slip=" + this.N + ", doublegap=" + this.O + ", thirdgap=" + this.P + ", fourgap=" + this.Q + ", rdoublegap=" + this.R + ", rthirdgap=" + this.S + ", rfourgap=" + this.T + ", glmode=" + this.U + ", lowloadmode=" + this.V + ", signalmode=" + this.W + ", signallimit=" + this.X + ", promode=" + this.Y + ", AUTH_GETVER_TIMEOUT=" + this.Z + ", DSEND_RQOS_TIMEOUT=" + this.aa + ", ENDQOS_TIMEOUT=" + this.ab + ", proxyip1='" + this.ac + '\'' + ", proxyip2='" + this.ad + '\'' + ", proxyip3='" + this.ae + '\'' + ", proxyport1=" + this.af + ", proxyport2=" + this.ag + ", inotoken=" + this.ah + ", isBGP=" + this.ai + ", usew2m=" + this.aj + ", w2mWdelay=" + this.ak + ", w2mMsignal=" + this.al + ", w2mMdelay=" + this.am + ", w2mTimes=" + this.an + ", m2wMdelay=" + this.ao + ", m2wWsignal=" + this.ap + ", m2wWdelay=" + this.aq + ", smart=" + this.av + ", cliCountry='" + this.aw + '\'' + ", cliArea='" + this.ax + '\'' + ", cliIsp='" + this.ay + '\'' + ", serCountry='" + this.az + '\'' + ", serArea='" + this.aA + '\'' + ", serIsp='" + this.aB + '\'' + ", pro1Country='" + this.aC + '\'' + ", pro1Area='" + this.aD + '\'' + ", pro1Isp='" + this.aE + '\'' + ", usereport='" + this.aF + ", ab_acc='" + this.aG + ", ab_w2m=" + this.aH + ", nhook=" + this.aI + ", mutiProxyDomain='" + this.aJ + '\'' + ", mutiProxyIp='" + this.aK + '\'' + ", mutiProxyPort=" + this.aL + ", pingIp='" + this.aM + '\'' + ", pingPort='" + this.aN + ", maxjump='" + this.aO + ", useterms='" + this.aP + ", mutisetID='" + this.aQ + ", doubleProxyIp='" + this.aR + '\'' + ", doubleProxyPort=" + this.aS + ", tos=" + this.aV + '}';
    }
}
