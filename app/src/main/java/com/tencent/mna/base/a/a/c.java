package com.tencent.mna.base.a.a;

import com.tencent.mna.a.a;
import com.tencent.mna.base.utils.f;
import com.tencent.mna.base.utils.h;
import org.json.JSONObject;

/* compiled from: CloudConfig */
public class c {
    public String aX = "0.0.0.0";
    public int aY = 0;
    public String aZ = "0.0.0.0";
    public int ba = 0;
    public int bb = 300;
    public int bc = 300;
    public int bd = 100;
    public int be = 0;
    public int bf = 0;
    public String bg = "";
    public int bh = 0;
    public int bi = 0;
    public String bj = "0.02_0.05_0.09_20_1.1_60";
    public String bk = "800.800.800";

    public String toString() {
        return String.format(a.a, "speedip:%s:%d,exportip:%s:%d,mna:%d,clientip:%s,usenext:%d,useping:%d,", new Object[]{this.aX, Integer.valueOf(this.aY), this.aZ, Integer.valueOf(this.ba), Integer.valueOf(this.bf), this.bg, Integer.valueOf(this.bh), Integer.valueOf(this.bi)});
    }

    /* access modifiers changed from: package-private */
    public void b(JSONObject jSONObject) {
        b(jSONObject.optString("speedsvr"));
        c(jSONObject.optString("edgesvr"));
        d(jSONObject.optString("kartinjudge"));
        this.be = jSONObject.optInt("debuglog", this.be);
        if (h.a() == 0) {
            h.a(this.be);
        }
        this.bf = jSONObject.optInt("mna", this.bf);
        this.bg = jSONObject.optString("clientip", this.bg);
        this.bh = jSONObject.optInt("usenext", this.bh);
        this.bi = jSONObject.optInt("useping", this.bi);
    }

    static boolean a(String str) {
        if (str == null || str.length() <= 0) {
            return false;
        }
        if (f.a(str) || str.contains(".ino.")) {
            return true;
        }
        return false;
    }

    private void b(String str) {
        if (str != null && str.contains(":")) {
            String[] split = str.split(":");
            if (split.length >= 2) {
                try {
                    if (a(split[0])) {
                        String f = f.f(split[0]);
                        if (f != null && f.length() > 0) {
                            this.aX = f;
                            this.aY = Integer.parseInt(split[1]);
                            return;
                        }
                        return;
                    }
                    this.aX = "0.0.0.0";
                    this.aY = 0;
                } catch (Exception e) {
                }
            }
        }
    }

    private void c(String str) {
        if (str != null && str.contains(":")) {
            String[] split = str.split(":");
            if (split.length >= 2) {
                try {
                    if (a(split[0])) {
                        String f = f.f(split[0]);
                        if (f != null && f.length() > 0) {
                            this.aZ = f;
                            this.ba = Integer.parseInt(split[1]);
                            return;
                        }
                        return;
                    }
                    this.aZ = "0.0.0.0";
                    this.ba = 0;
                } catch (Exception e) {
                }
            }
        }
    }

    private void d(String str) {
        if (str != null && str.contains("_")) {
            String[] split = str.split("_");
            if (split.length >= 3) {
                try {
                    this.bb = Integer.parseInt(split[0]);
                    this.bc = Integer.parseInt(split[1]);
                    this.bd = Integer.parseInt(split[2]);
                } catch (Exception e) {
                }
            }
        }
    }

    public String a() {
        return "speedIp='" + this.aX + '\'' + ", speedPort=" + this.aY + ", edgeIp='" + this.aZ + '\'' + ", edgePort=" + this.ba + ", preDelayMax=" + this.bb + ", curMinDelay=" + this.bc + ", jumpDvalue=" + this.bd + ", debuglog=" + this.be + ", mna=" + this.bf + ", clientip='" + this.bg + '\'' + ", useping=" + this.bi;
    }
}
