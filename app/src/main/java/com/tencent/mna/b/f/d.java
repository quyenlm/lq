package com.tencent.mna.b.f;

import com.beetalk.sdk.plugin.impl.gglive.GGLiveConstants;
import com.tencent.midas.oversea.api.UnityPayHelper;
import org.json.JSONObject;

/* compiled from: QosProto */
public class d {

    /* compiled from: QosProto */
    public static class a {
        public String a;
        public String b;
        public int c;
        public String d;
        public int e;
        public int f;
        public int g;

        public a(String str, String str2, int i, String str3, int i2, int i3, int i4) {
            this.a = str;
            this.b = str2;
            this.c = i;
            this.d = str3;
            this.e = i2;
            this.f = i3;
            this.g = i4;
        }

        public String a() {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("appid", this.a);
                jSONObject.put("sdkver", this.b);
                jSONObject.put("type", this.c);
                jSONObject.put(UnityPayHelper.OPENID, this.d);
                jSONObject.put("cppvalue", com.tencent.mna.base.jni.e.a("" + System.currentTimeMillis()));
                jSONObject.put("delay", this.e);
                jSONObject.put("stage", this.f);
                jSONObject.put("signal", this.g);
                return jSONObject.toString();
            } catch (Exception e2) {
                return "";
            }
        }
    }

    /* compiled from: QosProto */
    public static class b {
        public String a;
        public String b;
        public int c;
        public String d;
        public String e;
        public String f;
        public String g;

        public b(String str, String str2, int i, String str3, String str4, String str5, String str6) {
            this.a = str;
            this.b = str2;
            this.c = i;
            this.d = str3;
            this.e = str4;
            this.f = str5;
            this.g = str6;
        }

        public String a() {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("appid", this.a);
                jSONObject.put("sdkver", this.b);
                jSONObject.put("type", this.c);
                jSONObject.put("privateIp", this.d);
                jSONObject.put(UnityPayHelper.OPENID, this.e);
                jSONObject.put("phoneno", this.f);
                jSONObject.put("viparr", this.g);
                jSONObject.put("cppvalue", com.tencent.mna.base.jni.e.a("" + System.currentTimeMillis()));
                return jSONObject.toString();
            } catch (Exception e2) {
                return "";
            }
        }
    }

    /* compiled from: QosProto */
    public static class c {
        public int a;
        public String b;
        public int c;
        public int d;
        public String e;
        public String f;
        public String g;
        public int h;
        public int i;
        public int j;
        public String k;
        public int l;

        public static c a(JSONObject jSONObject) {
            c cVar = new c();
            cVar.a = jSONObject.optInt("errno", -1);
            cVar.b = jSONObject.optString("errmsg", "");
            cVar.c = jSONObject.optInt("isQos", 0);
            cVar.d = jSONObject.optInt("imsi", -1);
            cVar.e = jSONObject.optString("mobileUrl", "");
            cVar.f = jSONObject.optString("mobileId", "");
            cVar.g = jSONObject.optString("mobileSecure", "");
            cVar.h = jSONObject.optInt("strategy", -1);
            cVar.k = jSONObject.optString("ensureAdj", "1000");
            if (cVar.k.contains("_")) {
                String[] split = cVar.k.split("_");
                if (split.length > 1) {
                    cVar.i = Integer.parseInt(split[0]);
                    cVar.j = Integer.parseInt(split[1]);
                }
            } else {
                cVar.i = Integer.parseInt(cVar.k);
                cVar.j = -1;
            }
            cVar.l = jSONObject.optInt("frequence", 2);
            return cVar;
        }

        public String toString() {
            return "QosRsp1{errno=" + this.a + ", errmsg='" + this.b + '\'' + ", isQos=" + this.c + ", imsi=" + this.d + ", mobileUrl='" + this.e + '\'' + ", mobileId='" + this.f + '\'' + ", mobileSecure='" + this.g + '\'' + ", strategy=" + this.h + ", ensureAdj1=" + this.i + ", ensureAdj2=" + this.j + ", ensureAdjArrStr='" + this.k + '\'' + ", maxTimes=" + this.l + '}';
        }
    }

    /* renamed from: com.tencent.mna.b.f.d$d  reason: collision with other inner class name */
    /* compiled from: QosProto */
    public static class C0029d {
        public int a;
        public String b;
        public String c;
        public String d;

        public static C0029d a(JSONObject jSONObject) {
            C0029d dVar = new C0029d();
            dVar.a = jSONObject.optInt("error", 0);
            dVar.b = jSONObject.optString("message", "");
            dVar.c = jSONObject.optString(GGLiveConstants.PARAM.RESULT, "");
            dVar.d = jSONObject.optString("privateip", "");
            return dVar;
        }

        public String toString() {
            return "QosRsp2{error=" + this.a + ", message='" + this.b + '\'' + ", result='" + this.c + '\'' + ", privateIp='" + this.d + '\'' + '}';
        }
    }

    /* compiled from: QosProto */
    public static class e {
        public int a;
        public String b;
        public int c;
        public String d;
        public int e;
        public long f;

        public static e a(JSONObject jSONObject) {
            e eVar = new e();
            eVar.a = jSONObject.optInt("errno", -1);
            eVar.b = jSONObject.optString("errmsg", "");
            eVar.c = jSONObject.optInt("qosresult", -1);
            eVar.d = jSONObject.optString(UnityPayHelper.SESSIONID, "");
            eVar.e = jSONObject.optInt("sessiontime", 0);
            eVar.f = System.currentTimeMillis();
            return eVar;
        }

        public String toString() {
            return "QosRsp3{errno=" + this.a + ", errmsg='" + this.b + '\'' + ", qosresult=" + this.c + ", sessionId='" + this.d + '\'' + ", sessiontime=" + this.e + ", rsptime=" + this.f + '}';
        }
    }
}
