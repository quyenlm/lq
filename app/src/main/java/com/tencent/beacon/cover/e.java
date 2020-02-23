package com.tencent.beacon.cover;

import android.content.Context;
import com.appsflyer.AppsFlyerLib;
import com.tencent.component.debug.TraceFormat;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: QualityCollect */
public final class e {
    private static e b = null;
    private static String c = "qua_info";
    private Context a;
    private JSONArray d = null;
    private String e = null;

    private e(Context context) {
        if (context == null) {
            g.a(TraceFormat.STR_WARN, "context is null!", new Object[0]);
            return;
        }
        this.a = context.getApplicationContext();
        this.e = g.f(this.a);
        this.d = new JSONArray();
    }

    public static synchronized e a(Context context) {
        e eVar;
        synchronized (e.class) {
            if (b == null) {
                b = new e(context);
            }
            eVar = b;
        }
        return eVar;
    }

    /* access modifiers changed from: protected */
    public final void a(String str) {
        String str2 = null;
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("res", false);
            jSONObject.put("msg", str.replace(10, ' ').replace(13, ' '));
            str2 = jSONObject.toString();
        } catch (JSONException e2) {
        }
        if (str2 != null) {
            try {
                String b2 = g.b(this.a, c, "");
                if (!"".equals(b2)) {
                    g.a(this.a, c, b2 + "," + str2);
                } else {
                    g.a(this.a, c, str2);
                }
            } catch (Exception e3) {
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void b(String str) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("res", false);
            jSONObject.put("msg", str.replace(10, ' ').replace(13, ' '));
            this.d.put(jSONObject);
        } catch (Exception e2) {
        }
    }

    /* access modifiers changed from: protected */
    public final void a(boolean z) {
        int i = 0;
        String b2 = b(z);
        String a2 = g.a(this.a, this.e);
        try {
            byte[] a3 = g.a(true, this.e, b2.getBytes("utf-8"));
            if (a3 != null) {
                HashMap hashMap = new HashMap();
                hashMap.put("Content-Type", "application/x-www-form-urlencoded");
                hashMap.put("Content-Length", String.valueOf(a3.length));
                hashMap.put("encr_type", "rsapost");
                hashMap.put("rsa_encr_key", a2);
                hashMap.put("qua_log", "1");
                while (true) {
                    int i2 = i + 1;
                    if (i < 3) {
                        HttpURLConnection a4 = i.a("http://oth.update.mdt.qq.com:8080/beacon/vercheck", (Map<String, String>) hashMap);
                        if (a4 == null || i.a(a4, a3) == null) {
                            g.a(10000);
                            i = i2;
                        } else {
                            g.a(this.a, c, "");
                            return;
                        }
                    } else {
                        return;
                    }
                }
            }
        } catch (Exception e2) {
            g.a(TraceFormat.STR_ERROR, "Encry post data error!", new Object[0]);
        }
    }

    private String b(boolean z) {
        String str;
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("appkey", g.b(this.a));
            jSONObject.put("appversion", g.c(this.a));
            jSONObject.put("model", g.a());
            jSONObject.put(AppsFlyerLib.ATTRIBUTION_ID_COLUMN_NAME, g.d(this.a));
            jSONObject.put("cpuabi", g.b());
            jSONObject.put("coverSDKver", "1.0.0");
            String jSONObject2 = jSONObject.toString();
            String b2 = g.b(this.a, c, "");
            if ("".equals(b2)) {
                str = jSONObject2 + "|";
            } else {
                str = jSONObject2 + "|[" + b2 + "]";
            }
            if (!z) {
                return str + "|";
            }
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("compsDownRes", false);
            jSONObject3.put("compsDownErr", this.d);
            return str + "|" + jSONObject3.toString();
        } catch (Exception e2) {
            return "";
        }
    }
}
