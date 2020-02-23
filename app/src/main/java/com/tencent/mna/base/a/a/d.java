package com.tencent.mna.base.a.a;

import com.tencent.mna.base.utils.f;
import com.tencent.mna.base.utils.h;
import com.tencent.smtt.sdk.TbsListener;
import org.json.JSONObject;

/* compiled from: DgnCloudConfig */
public class d extends c {
    public int A = 50;
    public double B = 0.3d;
    public String C = "信号较差，请靠近路由器#信号不太稳定，请靠近路由器#路由器延迟不稳定，建议重启路由器";
    public boolean D = true;
    public boolean a = true;
    public int b = 10;
    public int c = 500;
    public int d = 1;
    public int e = 31;
    public int f = 3000;
    public int g = 10;
    public String h = "0.0.0.0";
    public int i = 0;
    public int j = 3000;
    public int k = 100;
    public int l = 10;
    public int m = 0;
    public int n = 10;
    public int o = 10;
    public int p = 100;
    public int q = 0;
    public int r = 25;
    public int s = 0;
    public int t = TbsListener.ErrorCode.DOWNLOAD_HAS_LOCAL_TBS_ERROR;
    public int u = 85;
    public int v = 3;
    public int w = 3;
    public int x = 0;
    public int y = 0;
    public int z = 10;

    public static d a(JSONObject jSONObject) {
        boolean z2 = true;
        d dVar = new d();
        try {
            dVar.b(jSONObject);
            dVar.b = jSONObject.optInt("ctrlRetry", dVar.b);
            dVar.c = jSONObject.optInt("kTimeout", dVar.c);
            dVar.d = jSONObject.optInt("kReport", dVar.d);
            dVar.e = jSONObject.optInt("control", dVar.e);
            dVar.f = jSONObject.optInt("extime", dVar.f);
            dVar.g = jSONObject.optInt("pcount", dVar.g);
            dVar.s = jSONObject.optInt("wuse", dVar.s);
            dVar.x = jSONObject.optInt("rproxy_flag", dVar.x);
            dVar.bj = jSONObject.optString("kplvalue", dVar.bj);
            dVar.y = jSONObject.optInt("rulever", dVar.y);
            dVar.C = jSONObject.optString("rrdesc", dVar.C);
            dVar.bk = jSONObject.optString("ver_test", dVar.bk);
            if (jSONObject.optInt("sd4id", dVar.D ? 1 : 0) != 1) {
                z2 = false;
            }
            dVar.D = z2;
            a(dVar, jSONObject.optString("vipsvr"));
            b(dVar, jSONObject.optString("wconfig"));
            c(dVar, jSONObject.optString("srconfig"));
            d(dVar, jSONObject.optString("rrinfo"));
            dVar.a = false;
        } catch (Throwable th) {
        }
        return dVar;
    }

    public String toString() {
        return "vip:" + this.h + ",port:" + this.i + ",interval:" + this.k + ",sendLen:" + this.l + ",doubleSend:" + this.m + ",recvLen:" + this.n + ",pushLen:" + this.o + ",pushInterval:" + this.p + ",doublepush:" + this.q + ",preDelayMax:" + this.bb + ",curMinDelay:" + this.bc + ",jumpDvalue:" + this.bd + ",dtime:" + this.j + ",pushTimes:" + this.r + ",wuse:" + this.s + ",wdelay:" + this.t + ",wsucc:" + this.u + ",wsignal:" + this.v + ",wstar:" + this.w + ",rrtesttimes:" + this.z + ",rrhdelayth:" + this.A + ",rrhdelayrateth:" + this.B + ",rrdesc:" + this.C;
    }

    private static void a(d dVar, String str) {
        String f2;
        if (str != null && str.contains(":")) {
            String[] split = str.split(":");
            if (split.length >= 2 && (f2 = f.f(split[0])) != null && f2.length() > 0) {
                dVar.h = f2;
                dVar.i = Integer.parseInt(split[1]);
            }
        }
    }

    private static void b(d dVar, String str) {
        if (str != null && str.contains("_")) {
            String[] split = str.split("_");
            if (split.length >= 4) {
                try {
                    dVar.t = Integer.parseInt(split[0]);
                    dVar.u = Integer.parseInt(split[1]);
                    dVar.v = Integer.parseInt(split[2]);
                    dVar.w = Integer.parseInt(split[3]);
                } catch (NumberFormatException e2) {
                    h.a("DgnCloudConfig parseWconfig:" + e2.getMessage());
                }
            }
        }
    }

    private static void c(d dVar, String str) {
        if (str != null && str.contains("_")) {
            String[] split = str.split("_");
            if (split.length >= 9) {
                try {
                    dVar.j = Integer.parseInt(split[0]);
                    dVar.k = Integer.parseInt(split[1]);
                    dVar.l = Integer.parseInt(split[2]);
                    dVar.m = Integer.parseInt(split[3]);
                    dVar.n = Integer.parseInt(split[4]);
                    dVar.o = Integer.parseInt(split[5]);
                    dVar.p = Integer.parseInt(split[6]);
                    dVar.q = Integer.parseInt(split[7]);
                    dVar.r = Integer.parseInt(split[8]);
                } catch (NumberFormatException e2) {
                    h.a("DgnCloudConfig parseSrconfig:" + e2.getMessage());
                }
            }
        }
    }

    private static void d(d dVar, String str) {
        if (str != null && str.contains("_")) {
            String[] split = str.split("_");
            if (split.length >= 3) {
                try {
                    dVar.z = Integer.parseInt(split[0]);
                    dVar.A = Integer.parseInt(split[1]);
                    dVar.B = Double.parseDouble(split[2]);
                } catch (NumberFormatException e2) {
                    h.a("DgnCloudConfig parseRrinfo:" + e2.getMessage());
                }
            }
        }
    }

    public String a() {
        return "DgnCloudConfig: " + super.a() + ", isDefault=" + this.a + ", ctrlRetry=" + this.b + ", kTimeout=" + this.c + ", kReport=" + this.d + ", control=" + this.e + ", extime=" + this.f + ", pcount=" + this.g + ", pushIp='" + this.h + '\'' + ", pushPort=" + this.i + ", dtime=" + this.j + ", sendInterval=" + this.k + ", sendLen=" + this.l + ", doubleSend=" + this.m + ", recvLen=" + this.n + ", pushLen=" + this.o + ", pushInterval=" + this.p + ", doublepush=" + this.q + ", pushTimes=" + this.r + ", wuse=" + this.s + ", wdelay=" + this.t + ", wsucc=" + this.u + ", wsignal=" + this.v + ", wstar=" + this.w + ", rproxy_flag=" + this.x + ", kplvalue='" + this.bj + '\'' + ", rulever=" + this.y + ", rrtesttimes:" + this.z + ", rrhdelayth:" + this.A + ", rrhdelayrateth:" + this.B + ", rrdesc:'" + this.C + '\'';
    }
}
