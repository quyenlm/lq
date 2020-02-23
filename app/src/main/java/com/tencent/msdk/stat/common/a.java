package com.tencent.msdk.stat.common;

import com.appsflyer.AppsFlyerLib;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import org.json.JSONException;
import org.json.JSONObject;

public class a {
    private String a = null;
    private String b = null;
    private String c = null;
    private String d = "0";
    private int e;
    private int f = 0;
    private long g = 0;

    public a() {
    }

    public a(String str, String str2, int i) {
        this.a = str;
        this.b = str2;
        this.e = i;
    }

    /* access modifiers changed from: package-private */
    public JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        try {
            p.a(jSONObject, "ui", this.a);
            p.a(jSONObject, "mc", this.b);
            p.a(jSONObject, HttpRequestParams.PUSH_XG_ID, this.d);
            p.a(jSONObject, AppsFlyerLib.ATTRIBUTION_ID_COLUMN_NAME, this.c);
            jSONObject.put("ts", this.g);
            jSONObject.put("ver", this.f);
        } catch (JSONException e2) {
        }
        return jSONObject;
    }

    public void a(int i) {
        this.e = i;
    }

    public String b() {
        return this.a;
    }

    public String c() {
        return this.b;
    }

    public int d() {
        return this.e;
    }

    public String toString() {
        return a().toString();
    }
}
