package com.tencent.msdk.a.a;

import android.util.Log;
import com.tencent.imsdk.framework.request.HttpRequestParams;
import org.json.JSONException;
import org.json.JSONObject;

public class d {
    public String a = null;
    public String b = null;
    public String c = "0";
    public long d = 0;

    static d a(String str) {
        d dVar = new d();
        if (i.b(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (!jSONObject.isNull("ui")) {
                    dVar.a = jSONObject.getString("ui");
                }
                if (!jSONObject.isNull("mc")) {
                    dVar.b = jSONObject.getString("mc");
                }
                if (!jSONObject.isNull(HttpRequestParams.PUSH_XG_ID)) {
                    dVar.c = jSONObject.getString(HttpRequestParams.PUSH_XG_ID);
                }
                if (!jSONObject.isNull("ts")) {
                    dVar.d = jSONObject.getLong("ts");
                }
            } catch (JSONException e) {
                Log.w("MID", "", e);
            }
        }
        return dVar;
    }

    /* access modifiers changed from: package-private */
    public boolean a() {
        return i.c(this.c);
    }

    /* access modifiers changed from: package-private */
    public JSONObject b() {
        JSONObject jSONObject = new JSONObject();
        try {
            i.a(jSONObject, "ui", this.a);
            i.a(jSONObject, "mc", this.b);
            i.a(jSONObject, HttpRequestParams.PUSH_XG_ID, this.c);
            jSONObject.put("ts", this.d);
        } catch (JSONException e) {
            i.a((Throwable) e);
        }
        return jSONObject;
    }

    public String toString() {
        return b().toString();
    }
}
